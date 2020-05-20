create table admin
(
    id int auto_increment,
    name varchar(100),
    password varchar(100),
    nick_name varchar(100),
    locked int(4),
    constraint admin_pk
        primary key (id)
);

