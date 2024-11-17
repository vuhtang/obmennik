insert into customer (id, name, surname, email, dob)
values (1, 'Vladimir', 'Karasev', 'volodya35@mail.ru', date '1983-08-26'),
       (2, 'Stepan', 'Kuznetcov', 'stepka334@yandex.ru', date '1999-12-12'),
       (3, 'Maria', 'Databasova', 'lublypostgres@gmail.com', date '1998-03-05'),
       (4, 'Danil', 'Kologriviy', 'esenin@mail.ru', date '2003-11-12'); -- работник поддержки

insert into account (id, user_id)
values (1, 1),
       (2, 3),
       (3, 4);

insert into wallet (id, account_id)
values (1, 1),
       (2, 2),
       (3, 3);

insert into fiat_wallet (id, account_id, balance)
values (1, 1, 1000),
       (2, 2, 99),
       (3, 3, 1598);

insert into coin (id, name)
values (1, 'bibacoin'),
       (2, 'bobacoin'),
       (3, 'notcoin'),
       (4, 'doge'),
       (5, 'etherium');

insert into stock_account (id, coin_id, amount)
values (1, 1, 1000),
       (2, 2, 1000),
       (3, 3, 1000),
       (4, 4, 1000),
       (5, 5, 1000);

insert into coin_to_wallet (id, coin_id, wallet_id, amount)
values (1, 1, 1, 2),
       (2, 1, 2, 55),
       (3, 2, 2, 24),
       (4, 4, 3, 1023042);

insert into coin_price (id, coin_id, price_dollars, dttm)
values (1, 1, 123, date '2024-11-17'),
       (2, 2, 22, date '2024-11-17'),
       (3, 3, 1504, date '2024-11-17'),
       (4, 4, 3, date '2024-11-17'),
       (5, 5, 134902, date '2024-11-17');

insert into coin_buy_history(id, coin_buy_id, coin_buy_amount, coin_buy_price, tax, date, wallet_id)
values (1, 1, 2, 123, 2, date '2024-11-13', 1),
       (2, 2, 40, 20, 5, date '2024-11-12', 2),
       (3, 5, 134902, 1, 0, date '2018-01-01', 3);

insert into user_role (id, name)
values (1, 'admin'),
       (2, 'user'),
       (3, 'support');

insert into control_access (id, name)
values (1, 'create tickets'),
       (2, 'reply to users'),
       (3, 'exchange coins'),
       (4, 'close tickets'),
       (5, 'add supporters');

insert into role_to_access (role_id, access_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 3),
       (2, 1),
       (3, 1),
       (3, 2),
       (3, 4);

insert into customer_to_role (customer_id, role_id)
values (1, 1),
       (2, 2),
       (3, 2),
       (4, 3);




