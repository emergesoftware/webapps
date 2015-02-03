BEGIN;

USE xplain2me;

-- create a table for school
create table school (
    
    school_id int auto_increment not null unique,
    school_name varchar(256) not null unique,
    contact_detail_id int not null,
    school_area_name varchar(256) not null,
    school_is_public_school boolean not null default true,

    primary key (school_id),
    foreign key (contact_detail_id) references contact_detail(contact_detail_id)

);

-- create a student table
create table student (
    
    student_id int auto_increment not null unique,
    profile_id int not null,
    student_grade int not null default 1,
    school_id int,
    student_date_added timestamp default current_timestamp not null,
    tutor_request_id int,

    primary key (student_id),
    foreign key (profile_id) references profile(profile_id),
    foreign key (school_id) references school(school_id),
    foreign key (tutor_request_id) references tutor_request(tutor_request_id)

);

-- alter student table and make profile_id unique
alter table student add constraint school_profile_id_ukey unique(profile_id);

-- alter the lesson table tutor_profile_id + student_profile_id columns
alter table lesson drop column tutor_profile_id;
alter table lesson drop column student_profile_id;

alter table lesson add tutor_id int not null;
alter table lesson add student_id int not null;

alter table lesson add foreign key (tutor_id) references tutor(tutor_id);
alter table lesson add foreign key (student_id) references student(student_id);

-- alter the tutor_claim_document: change the tutor_profile_id table
alter table tutor_claim_document drop column tutor_profile_id;
alter table tutor_claim_document add tutor_id int not null;
alter table tutor_claim_document add foreign key (tutor_id) references tutor(tutor_id);


COMMIT;
