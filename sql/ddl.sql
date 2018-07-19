--file -inlinebatch DROPS

DROP TABLE auctions IF EXISTS;
DROP TABLE bids IF EXISTS;

--DROPS

--file -inlinebatch CREATES

create table auctions (
    auction_id integer NOT NULL,
    seller_id integer NOT NULL,
    vehicle_id varchar(10) NOT NULL,
    reg_state varchar(2) NOT NULL,
    make varchar(16),
    model varchar(16),
    bid_floor integer,
    win_bid integer,
    win_buyer integer,
    last_updated timestamp NOT NULL,
    primary key(auction_id)
);
partition table auctions on column auction_id;

create table bids (
	auction_id integer NOT NULL,
    buyer_id integer,
    bid_price integer,
    bid_time timestamp NOT NULL
);
partition table bids on column auction_id;

create table buyers (
	buyer_id integer NOT NULL,
	buyer_name varchar(32) NOT NULL,
	buyer_state varchar(2),
	max_bid_limit integer,
	max_bid_amount integer,
	primary key (buyer_id)
);

create table sellers (
	seller_id integer NOT NULL,
	seller_name varchar(32) NOT NULL,
	seller_state varchar(2),
	primary key (seller_id)
);

create view TOP_BIDDERS as 
	select 
		win_buyer as buyer_id, 
		buyer_name, 
		sum(win_bid) as total, 
		count(*) as BID_COUNT 
	from auctions, buyers 
	where 
	auctions.win_buyer = buyers.buyer_id 
	group by win_buyer, buyer_name;

--CREATES

load classes classes.jar;

create procedure partition on table auctions column auction_id from class NewAuctionProc;
create procedure partition on table auctions column auction_id from class PlaceBid;
