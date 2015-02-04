BEGIN;

USE xplain2me;

-- drop the table: academic_levels_tutored_before
drop table academic_levels_tutored_before cascade;

-- create a new table to hold subjects tutored before instead
create table subjects_tutored_before (

    subjects_tutored_before_id int auto_increment not null unique,
    become_tutor_request_id int not null,
    subject_id int not null,
    
    primary key (subjects_tutored_before_id),
    foreign key (become_tutor_request_id) references become_tutor_request(become_tutor_request_id),
    foreign key (subject_id) references subject(subject_id)

);

-- ensure a unique selection between one tutor job request against a subject
alter table subjects_tutored_before add constraint become_tutor_request_id_subject_id_ukey
unique(become_tutor_request_id, subject_id);


COMMIT;
