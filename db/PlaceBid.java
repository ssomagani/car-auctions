import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltTable.ColumnInfo;
import org.voltdb.VoltType;

/**
 * 
 */

/**
 * @author seetasomagani
 *
 */
public class PlaceBid extends VoltProcedure {

	private final SQLStmt INSERT_BID = new SQLStmt("INSERT INTO bids VALUES (?, ?, ?, NOW())");
	private final SQLStmt UPDATE_AUCTION = new SQLStmt("update auctions set win_bid = ?, win_buyer = ?, last_updated = NOW() where auction_id = ? ");
	private final SQLStmt GET_AUCTION = new SQLStmt("select bid_floor, win_bid from auctions where auction_id = ?");

	public VoltTable[] run(int auctionId, int buyerId, int bidPrice) {
		voltQueueSQL(GET_AUCTION, auctionId);
		VoltTable result = voltExecuteSQL()[0];
		if(result.advanceRow()) {
			
			int bidFloor = (int) result.getLong(0);
			if(bidFloor > bidPrice)
				return buildError("Bid does not match or exceed asking price " + bidFloor);

			int winBid = (int) result.getLong(1);
			if(winBid > bidPrice) 
				return buildError("Bid lower than current highest bid");

			voltQueueSQL(UPDATE_AUCTION, bidPrice, buyerId, auctionId);
			voltQueueSQL(INSERT_BID, auctionId, buyerId, bidPrice);
			return voltExecuteSQL(true);

		} else {
			return buildError("AuctionId " + auctionId + " not found");
		}
	}

	private VoltTable[] buildError(String errorString) {
		ColumnInfo colInfo = new ColumnInfo("ERROR", VoltType.STRING);
		VoltTable table = new VoltTable(colInfo);
		table.addRow(errorString);
		return new VoltTable[] {table};
	}
}
