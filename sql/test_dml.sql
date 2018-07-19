insert into auctions values (1, 1, '12345A', 'UP', 'Audi', 'A3', 1000000, -1, -1, NOW());
insert into auctions values (2, 1, '8S345A', 'DL', 'Audi', 'A6', 3000000, -1, -1, NOW());
insert into auctions values (3, 2, 'DC845A', 'KA', 'Suzuki', 'Ignite', 1500000, -1, -1, NOW());
insert into auctions values (4, 2, 'AD899C', 'HP', 'BMW', '328i', 100000000, -1, -1, NOW());

exec PlaceBid 4 1 110000000;
exec PlaceBid 2 1 31000000;
exec PlaceBid 3 2 1600000;
exec PlaceBid 1 2 2000000;

insert into buyers values (1, 'ABC MOTORS', 'UP', 20, 100000000);
insert into buyers values (2, 'DEF MOTORS', 'HP', 20, 100000000);
insert into buyers values (3, 'GHI MOTORS', 'DL', 20, 100000000);

select * from top_bidders order by total desc;
