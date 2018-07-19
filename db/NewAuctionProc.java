import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

/**
 * 
 */

/**
 * @author seetasomagani
 *
 */
public class NewAuctionProc extends VoltProcedure {

	private final SQLStmt INSERT_AUCTION = new SQLStmt("insert into Auctions values (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW() )");
	
	public VoltTable[] run(int auctionId, int sellerId,
			String vehicleId,
			String regState, String make, String model,
			int bidFloor, int winBid, int winBuyer
			) {
		voltQueueSQL(INSERT_AUCTION, auctionId, sellerId, vehicleId, regState, make, model, bidFloor, winBid, winBuyer);
		return voltExecuteSQL();
	}
}
