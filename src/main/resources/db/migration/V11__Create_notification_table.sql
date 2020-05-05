create table notification
(
   id bigint auto_increment,
   receiver varchar(100) not null,
   outer_id bigint not null,
   type int not null,
   gmt_create bigint not null,
   status int default 0 not null,
   constraint notification_pk
      primary key (id)
);