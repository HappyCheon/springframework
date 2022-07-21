show tables;

create table schedule2 (
  idx    int         not null  auto_increment primary key,
  mid    varchar(20) not null,
  sDate datetime    not null,     /* 일정등록날짜 */
  part varchar(10) not null,			/* 1.모임, 2.업무, 3.학습, 4.여행, 0:기타 */
  content text not null						/* 일정 상세 내역 */
);

desc schedule2;

insert into schedule2 value (default, 'hkd1234', '2022-07-29', '모임', '가족회의, 장소:집, 시간:19시');
insert into schedule2 value (default, 'hkd1234', '2022-08-30', '기타', '아침굽기, 장소:청주의료원, 시간:10시');
insert into schedule2 value (default, 'hkd1234', '2022-07-29', '모임', '친구생일, 장소:치킨호프, 시간:21시');
insert into schedule2 value (default, 'hkd1234', '2022-07-25', '학습', '영어회와, 장소:영어학원, 시간:17시');
insert into schedule2 value (default, 'hkd1234', '2022-07-29', '학습', '객체학습, 장소:도서관, 시간:12시');
insert into schedule2 value (default, 'hkd1234', '2022-06-25', '모임', '6.25모임, 장소:통일관, 시간:14시');
insert into schedule2 value (default, 'hkd1234', '2022-08-04', '업무', '주제발표, 장소:회사, 시간:10시');
insert into schedule2 value (default, 'hkd1234', '2022-07-20', '모임', '가족행사, 장소:집, 시간:19시');
insert into schedule2 value (default, 'hkd1234', '2022-07-04', '모임', '여행친구, 장소:월드컵호프, 시간:20시');
insert into schedule2 value (default, 'hkd1234', '2022-07-15', '여행', '정동진 해돋이, 장소:강릉, 시간:05시');
insert into schedule2 value (default, 'kms1234', '2022-08-15', '모임', '소영이생일파티, 장소:홈파티홀, 시간:15시');
insert into schedule2 value (default, 'kms1234', '2022-08-29', '여행', '유달산산행, 장소:유달산, 시간:09시');

select * from schedule2 order by sDate;
select * from schedule2 where mid='hkd1234' order by sDate;
select * from schedule2 where mid='hkd1234' and sDate='2022-01' order by sDate;  /* 검색 결과 없음 */
select * from schedule2 where mid='hkd1234' and substring(sDate,1,7)='2022-01' order by sDate;
select * from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2022-01' order by sDate;
select * from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2022-01' group by sDate  order by sDate;
/* select * from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2022-01' group by substring(sDate,1,7)  order by substring(sDate,1,7); */
select * from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2022-01' group by sDate  order by sDate, memory;
select sDate,count(*) from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2022-01' group by sDate  order by sDate, memory;
select sDate,substring(title,1,4) from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m-%d')='2022-01-29' order by sDate, memory;
select sDate,memory from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m-%d')='2022-01-29' order by sDate;
select sDate,memory,count(*) as memoryCount from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m-%d')='2022-01-29' group by memory order by sDate;
select sDate,memory,count(*) as memoryCount from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2022-01' group by memory order by sDate;

select * from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2022-01' order by sDate;

select * from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m-%d')='2022-01-30';

