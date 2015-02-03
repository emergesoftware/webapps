BEGIN;

USE xplain2me;

-- create a table to store extra tutor information
create table tutor (

    tutor_id int auto_increment not null unique,
    profile_id int not null,
    become_tutor_request_id int,

    primary key (tutor_id),
    foreign key (profile_id) references profile(profile_id),
    foreign key (become_tutor_request_id) references become_tutor_request(become_tutor_request_id)
);

-- make the profile_id + become_tutor_request_id columns to be unique
alter table tutor add constraint tutor_profile_id_ukey unique(profile_id);
alter table tutor add constraint become_tutor_request_id_ukey unique(become_tutor_request_id);

-- migrate all tutors into this table
insert into tutor(profile_id) 
select profile_id from profile where profile_type_id = 102;

-- add table to store the subjects the tutor can tutor
create table tutor_subject (

    tutor_subject_id int auto_increment not null unique,
    tutor_id int not null,
    subject_id int not null,

    primary key (tutor_subject_id),
    foreign key (tutor_id) references tutor(tutor_id),
    foreign key (subject_id) references subject(subject_id)
    
);

-- add a unique constraint to tutor_subject
alter table tutor_subject add constraint tutor_subject_ukey unique(tutor_id, subject_id);

COMMIT;
