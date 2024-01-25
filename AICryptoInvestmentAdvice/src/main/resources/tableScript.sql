drop schema if exists AI_Investment_Advice;
create schema AI_Investment_Advice;

use AI_Investment_Advice;

drop table if exists user_information;
drop table if exists advice_record;
create table advice_record(
    id integer primary key auto_increment,
    name varchar(50) not null,
    exchange varchar(50) not null,
    purchase_quantity double not null,
    purchase_price double not null,
    purchase_year int not null,
    current_price double not null,
    current_price_date date not null,
    target_price double not null,
    target_year int not null,
    news_titles TEXT not null,
    ai_advice TEXT not null,
    user_name varchar(50) references user_information(user_name)
);

create table user_information(
    user_name varchar(50) primary key not null,
    password varchar(50) not null
);