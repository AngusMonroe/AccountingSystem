/*清空数据库*/
set sql_safe_updates = 0;
delete from user;
delete from item;
delete from transaction;
delete form balance;

/*查询数据库*/
select * from user;

select * from item;

select * from transaction;

select * from balance;

/*预设内容*/
insert into user values(1, 'admin', 'admin', 'Administrator');
insert into user values(2, 'zhao', '111111', 'Seller');
insert into user values(3, 'qian', '222222', 'Buyer');
insert into user values(4, 'sun', '333333', 'Accountant');
insert into item values(1, 'apple', 2.25, 5000);
insert into item values(2, 'banana', 1.8, 3000);
insert into item values(3, 'orange', 3.5, 2000);
insert into item values(4, 'grape', 4.5, 1000);
insert into item values(5, 'melon', 12.0, 200);
insert into item values(6, 'coconut', 25.0, 100);
