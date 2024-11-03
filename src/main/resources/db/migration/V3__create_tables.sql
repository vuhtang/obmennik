alter table bank_account add bank_id serial references bank (id) on delete cascade;
alter table dollar_exchange_history add amount integer not null default 0;