begin;

create table if not exists province (
    province_id int(11) not null unique,
    province_desc varchar(64) not null unique,

    primary key (province_id)
);

insert into province values(1, 'Gauteng');
insert into province values(2, 'Mpumalanga');
insert into province values(3, 'Limpopo');
insert into province values(4, 'North West');
insert into province values(5, 'Free State');
insert into province values(6, 'KwaZulu Natal');
insert into province values(7, 'Eastern Cape');
insert into province values(8, 'Northern Cape');
insert into province values(9, 'Western Cape');

commit;
