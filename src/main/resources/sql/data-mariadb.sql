INSERT INTO `MEMBER` (`MEMBER_ID`, `USERNAME`, `PASSWORD`, `ZIPCODE`, `CITY`, `GU`, `STREET`, `REST`) VALUES 
	(1, 'test', '$2a$10$XOzzm0y.T5QU6Reb6TUyUusBodpFNzcHJEYUZ0YikF3bF9h7ZMsdO', '21392', 'Incheon', 'Bupyeong', 'Chungseon-ro', '999-999, 999ho');
	
INSERT INTO `CAFE_MENU` (`CAFE_MENU_ID`, `NAME`, `PRICE`, `ICE`, `ON_SALE`) VALUES
	(1, 'Americano', 1500, false, true),
	(2, 'Americano', 2000, true, true),
	(3, 'Latte', 2000, false, true),
	(4, 'Latte', 2500, true, true),
	(5, 'Mocha', 2500, false, true),
	(6, 'Mocha', 3000, true, true);