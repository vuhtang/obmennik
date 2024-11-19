
-- USER

create table if not exists user_role (
    id      serial primary key,
    name    varchar(40) not null
);

create table if not exists control_access (
    id      serial primary key,
    name    varchar(40) not null
);

create table if not exists customer (
    id          serial primary key,
    name        varchar(40) not null,
    surname     varchar(40) not null,
    email       varchar(50) not null,
    dob         Date not null
);

create table if not exists account (
    id          serial primary key,
    user_id     serial references customer (id) on delete cascade
);

create table if not exists customer_to_role (
    id              serial primary key,
    customer_id     int references customer (id) on delete cascade,
    role_id         int references user_role (id) on delete cascade
);

create table if not exists role_to_access (
    id          serial primary key,
    role_id     serial references customer (id) on delete cascade,
    access_id   serial references control_access (id) on delete cascade
);

-- TICKETS

create type theme as enum ('registration', 'transactions', 'etc');
create type status as enum ('created', 'in_work', 'done');

create table if not exists ticket (
    id              serial primary key,
    theme_id        theme not null,
    title           varchar(50) not null,
    description     text not null,
    status_id       status not null,
    complainer_id   serial references customer (id) on delete set null,
    responsible_id  serial references customer (id) on delete set null
);

create table if not exists ticket_chat_message (
    id          serial primary key,
    ticket_id   serial references ticket (id) on delete cascade,
    create_dttm date not null,
    message_text    text not null,
    sender_id   serial references customer (id) on delete cascade
);

-- BANK

-- create table if not exists bank_account (
--     id          serial primary key,
--     name        varchar(100) not null,
--     balance     bigint not null
-- );

-- create table if not exists bank (
--     id          serial primary key,
--     name        varchar(50) not null,
--     bank_account_id     serial references bank_account (id) on delete set null
-- );

create table if not exists fiat_wallet (
    id          serial primary key,
    account_id  serial references account (id) on delete cascade,
    balance     bigint not null
);

-- create table if not exists dollar_exchange_history (
--     id          serial primary key,
--     wallet_id   serial references fiat_wallet (id) on delete cascade,
--     bank_account_id   serial references bank_account (id) on delete cascade
-- );

-- STOCK

create table if not exists wallet (
    id          serial primary key,
    account_id  serial references account (id) on delete cascade,
    private_key varchar(100) not null
);

create table if not exists coin (
    id          serial primary key,
    name        varchar(40)
);

create table if not exists coin_to_wallet (
    id          serial primary key,
    coin_id     serial references coin (id) on delete cascade,
    wallet_id   serial references wallet (id) on delete cascade,
    amount      bigint not null
);

create table if not exists stock_account (
    id          serial primary key,
    coin_id     serial references coin (id) on delete cascade,
    amount      bigint not null
);

create table if not exists coin_price (
    id              serial primary key,
    coin_id         serial references coin (id) on delete cascade,
    price_dollars   bigint not null,
    dttm            date not null
);

create table if not exists coin_exchange_history (
    id                  serial primary key,
    coin_sell_id        serial references coin (id) on delete cascade,
    coin_sell_amount    bigint not null,
    coin_sell_price     bigint not null,

    coin_buy_id         serial references coin (id) on delete cascade,
    coin_buy_amount     bigint not null,
    coin_buy_price      bigint not null,

    tax                 int not null,
    date                date not null,
    wallet_id         serial references wallet (id) on delete cascade
);

