create table kakaoAddress(
  address  varchar(50) not null,	/* 지점명 */
  latitude  double not null,			/* 위도 */
  longitude double not null				/* 경도 */
);
desc kakaoAddress;

select * from kakaoAddress;

create table kakaoArea(
  address1 varchar(30) not null, 	/* 도(경기도/강원도) */
  address2 varchar(30) not null, 	/* 시(강릉시/동해시) */
  latitude  double not null,			/* 위도 */
  longitude double not null				/* 경도 */
);

desc kakaoArea;

select * from kakaoArea;

