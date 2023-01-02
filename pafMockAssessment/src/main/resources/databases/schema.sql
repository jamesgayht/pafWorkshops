create database base_paf_db; 

use base_paf_db; 

create table user (
    user_id varchar(8) PRIMARY KEY, 
    username varchar(30) unique not null,
    name varchar(30)
);

create table task (
    task_id int auto_increment PRIMARY KEY,
    description varchar(255), 
    priority enum('1','2','3') not null, 
    due_date date,
    user_id varchar(8) not null,
    foreign key (user_id) references user(user_id)
);
