create table if not exists user_table (
    id bigint not null primary key,
    user_name varchar(45) not null,
    first_name varchar(50) not null,
    last_name varchar(50),
    created timestamp not null
);

create table if not exists swear_accounting(
    id bigserial not null primary key,
    swear_word varchar(100) not null,
    created timestamp not null,
    chat_id bigint not null,
    user_table_id bigint references user_table (id)
);