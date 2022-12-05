drop database if exists paf_day24ws;

create database if not exists paf_day24ws; 

use paf_day24ws; 

create table orders (
    order_id int primary key auto_increment, 
    order_date date not null,
    customer_name varchar(128) not null,
    ship_address varchar(128) not null,
    notes text, 
    tax decimal(2,2) default 0.05 not null 
);

create table order_details (
    id int primary key auto_increment,
    product varchar(64) not null, 
    unit_price decimal(3,2) not null, 
    discount decimal (2,1) default 1.0 not null,
    quantity int not null
); 