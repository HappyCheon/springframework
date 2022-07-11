show tables;

create table person (
  idx int not null auto_increment primary key,
  mid varchar(20) not null,
  pwd varchar(20) not null
);

create table personDetail (
  mid varchar(5) not null,
  name varchar(20) not null,
  age  int default 20,
  address varchar(50) not null
);

desc person;
desc personDetail;

/*
drop table person;
drop table personDetail;
*/
delete from person;
delete from persondetail;


select * from person;
select * from persondetail;
select p1.*, p2.* from person p1, personDetail p2 where p1.mid=p2.mid order by idx desc;
