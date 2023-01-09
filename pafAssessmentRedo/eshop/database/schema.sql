create database eshop_redo; 

use eshop; 

create table customers (
    order_id int auto_increment primary key, 
    name varchar(32) not null, 
    address varchar(128) not null, 
    email varchar(128) not null 
);

/* LOAD DATA LOCAL INFILE "/Users/jumo/Downloads/NUS_VTTP/pafWorkshops/pafAssessmentRedo/eshop/database/data.csv" into table customers 
fields terminated by ":" lines 
terminated by "\n" 
ignore 1 lines; */


