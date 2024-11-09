create table if not exists coin_buy_history (
    id  serial primary key,
    coin_buy_id     serial references coin (id) on delete cascade,
    coin_buy_amount  bigint not null,
    coin_buy_price      bigint not null,
    tax         bigint not null,
    date date not null,
    customer_id     serial references customer (id) on delete cascade
);
