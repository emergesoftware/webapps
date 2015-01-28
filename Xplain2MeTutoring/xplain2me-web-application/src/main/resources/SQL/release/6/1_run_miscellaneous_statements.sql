BEGIN;

-- use the xplain2me database
USE xplain2me;

-- create new menu category for Lesson management
insert into menu_category
values(7, 'Lessons', 'glyphicon glyphicon-book', true);

-- create a new menu for adding new lessons
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (23, 'Add Lessons', 'Add new lessons', '/portal/lessons/create', true, 7);

-- grant the permissions for menu (id=23) to the manager
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can add new lessons.', 101, 23);

-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

-- create a new menu for finding student async
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (24, 'Search for student by name async', 'Search for student by name async', 
'/portal/lessons/find-student-async', false, 7);

-- grant the permissions for menu (id=24) to the manager
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can search for students to be added to a lesson.', 101, 24);

-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

-- create a lesson status table
create table lesson_status (
    lesson_status_id int not null unique,
    lesson_status_desc varchar(255) not null unique,

    primary key (lesson_status_id)
);

-- insert the different lesson types
insert into lesson_status values(1, 'PENDING');
insert into lesson_status values(2, 'COMPLETED');
insert into lesson_status values(3, 'POSTPONED');
insert into lesson_status values(4, 'CANCELLED');

-- create a tutor claim document table
create table tutor_claim_document (

    tutor_claim_document_id int auto_increment not null unique,
    tutor_profile_id int not null,
    tutor_claim_document_amount double default 0.0 not null,
    tutor_claim_document_disputed boolean default false not null,
    tutor_claim_document_dispute_message varchar(255),
    tutor_claim_document_approved boolean default false not null,
    tutor_claim_document_paid_out boolean default false not null,
    tutor_claim_document_date_completed timestamp default current_timestamp not null,
    tutor_claim_document_date_paid_out timestamp,
    tutor_claim_document_cancelled boolean default false not null,
    tutor_claim_document_date_cancelled timestamp,
    tutor_claim_document_cancellation_reason varchar(255),
    tutor_claim_document_no_of_lessons int default 0 not null,

    primary key (tutor_claim_document_id),
    foreign key (tutor_profile_id) references profile(profile_id)

);

-- create a lesson table
create table lesson (

    lesson_id int auto_increment not null unique,
    tutor_profile_id int not null,
    student_profile_id int not null,
    lesson_scheduled_date_and_time timestamp default current_timestamp not null,
    lesson_actual_date_and_time timestamp,
    lesson_scheduled_length_of_lesson double default 0.0 not null,
    lesson_actual_length_of_lesson double default 0.0 not null,
    subject_id int not null,
    academic_level_id int not null,
    lesson_status_id int not null,
    lesson_reasons_for_postponement_or_cancellation text,
    lesson_content_covered text,
    lesson_learner_performance_and_problem_areas text,
    lesson_actions_taken_to_solve_problem_areas text,
    lesson_last_updated timestamp,
    lesson_claimed boolean default false not null,
    lesson_date_claimed timestamp,
    tutor_claim_document_id int,

    primary key (lesson_id),
    foreign key (tutor_profile_id) references profile(profile_id),
    foreign key (student_profile_id) references profile(profile_id),
    foreign key (subject_id) references subject(subject_id),
    foreign key (academic_level_id) references academic_level(academic_level_id),
    foreign key (lesson_status_id) references lesson_status(lesson_status_id),
    foreign key (tutor_claim_document_id) references tutor_claim_document(tutor_claim_document_id)
    

);

COMMIT;
