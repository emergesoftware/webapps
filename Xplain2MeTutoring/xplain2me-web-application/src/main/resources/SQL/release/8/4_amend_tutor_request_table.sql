use xplain2me;

begin;

-- create a table to hold messages for a tutor request
create table tutor_request_thread (
    
    tutor_request_thread_id int auto_increment not null unique,
    tutor_request_id int not null,
    tutor_request_thread_message text not null,
    tutor_request_thread_timestamp timestamp default current_timestamp not null,
    user_id int not null,
    tutor_request_thread_is_edited boolean default false not null,
    tutor_request_thread_date_edited timestamp,
    tutor_request_thread_is_deleted boolean default false not null,
    tutor_request_thread_date_deleted timestamp,

    primary key (tutor_request_thread_id),
    foreign key (tutor_request_id) references tutor_request(tutor_request_id),
    foreign key (user_id) references users(user_id)

);

-- create a system user
insert into users(user_name, user_password, user_active, user_added, user_deactivated)
values('system', '0000000000000000000000000000000000000', true, current_timestamp, null);

commit;
