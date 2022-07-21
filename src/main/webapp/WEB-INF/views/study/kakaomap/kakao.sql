/* kakaoAddress : 지점 등록시켜놓고 검색하기 위한 테이블 */
create table kakaoAddress(
  address  varchar(50) not null,	/* 지점명 */
  latitude  double not null,			/* 위도 */
  longitude double not null				/* 경도 */
);
desc kakaoAddress;

select * from kakaoAddress;

/* kakaoArea : 지역별로 테마구분별로 검색하기위해 사용한 테이블 */

create table kakaoArea(
  address1 varchar(30) not null, 	/* 도(경기도/강원도) */
  address2 varchar(30) not null, 	/* 시(강릉시/동해시) */
  latitude  double not null,			/* 위도 */
  longitude double not null				/* 경도 */
);

desc kakaoArea;

select * from kakaoArea;

select distinct address1 from kakaoArea;
select distinct address2 from kakaoArea;

select distinct address2 from kakaoArea where address1 = '강원도' order by address2;
select distinct address2 from kakaoArea where address1 = '서울특별시' order by address2;

select address1, address2, latitude, longitude from kakaoArea where address1 = '강원도' and address2 = '강릉시';
select distinct address1, address2, latitude, longitude from kakaoArea where address1 = '강원도' and address2 = '강릉시';
select address1, address2, latitude, longitude from kakaoArea where address1 = '강원도' and address2 = '강릉시' limit 1;