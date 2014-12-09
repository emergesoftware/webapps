begin; 

create table if not exists become_tutor_request (

    become_tutor_request_id int(11) not null auto_increment,
    become_tutor_request_last_name varchar(256) not null,
    become_tutor_request_first_names varchar(256) not null,
    become_tutor_request_contact_number varchar(13) not null unique,
    become_tutor_request_email_address varchar(256) not null unique,
    become_tutor_request_street_address text not null,
    become_tutor_request_suburb varchar(128) not null,
    become_tutor_request_city varchar(128) not null,
    become_tutor_request_area_code varchar(6) not null,
    become_tutor_request_id_number varchar(13) not null unique,
    become_tutor_request_date_of_birth timestamp not null,
    citizenship_id int(11) not null,
    gender_id varchar(1) not null,
    agreed_to_terms_of_service boolean not null default 0,
    become_tutor_request_submitted timestamp not null,
    
    primary key (become_tutor_request_id),
    foreign key (citizenship_id) references citizenship(citizenship_id),
    foreign key (gender_id) references gender(gender_id)
);

commit;