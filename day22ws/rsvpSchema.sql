create table rsvp (
    id int auto_increment primary key,
    name varchar(30) not null,
    email varchar(128) not null,
    phone char(11) not null,
    confirmation_date Date not null,
    comments text
)