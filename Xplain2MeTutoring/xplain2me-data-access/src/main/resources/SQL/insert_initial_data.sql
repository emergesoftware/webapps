begin;

insert into app_client
values (
        2014001,
        'Xplain2Me Tutoring Services',
        'Xplain2Me Tutoring',
        null,
        '0784755987',
        'Washington Chigwaza',
        'Bramley, Johannesburg, Gauteng, South Africa',
        null,
        'wchigwaza@yahoo.com',
        now(),
        true,
        null
);

insert into academic_level values(1, 'Foundation Phase - Grades 1 - 3');
insert into academic_level values(2, 'Intermediate Phase - Grades 4 - 6');
insert into academic_level values(3, 'Senior Phase - Grades 7 - 9');
insert into academic_level values(4, 'Further Education and Training Phase - Grades 10 - 12');
insert into academic_level values(5, 'Post Matric Level - (College or University)');

insert into subject values(100,'Mathematics');
insert into subject values(101,'Chemistry');
insert into subject values(102,'Physics');
insert into subject values(103,'Physical Sciences');
insert into subject values(104,'Business Studies');
insert into subject values(105,'Statistics');
insert into subject values(106,'Economics');
insert into subject values(107,'Finance');
insert into subject values(108,'Management');
insert into subject values(109,'Taxation');
insert into subject values(110,'Criminal Law');
insert into subject values(111,'Mercantile Law');
insert into subject values(112,'Engineering');
insert into subject values(113,'Electronics');
insert into subject values(114,'Life Sciences');
insert into subject values(115,'Agricultural Studies');
insert into subject values(116,'Technical Drawing');
insert into subject values(117,'Mathematical Literacy');
insert into subject values(118,'Additional Mathematics');
insert into subject values(119,'Arts and Culture');
insert into subject values(120,'Natural Sciences');
insert into subject values(121,'Geography');
insert into subject values(122,'Office Administration');
insert into subject values(123,'Bible Studies');

insert into user_role(user_role_id, user_role_desc, user_role_privilege_level)
values(100, 'ADMINISTRATOR', 0);

insert into user_role(user_role_id, user_role_desc, user_role_privilege_level)
values(101, 'APPLICATION MANAGER', 1);

insert into user_role(user_role_id, user_role_desc, user_role_privilege_level)
values(102, 'TUTOR', 2);

insert into user_role(user_role_id, user_role_desc, user_role_privilege_level)
values(103, 'STUDENT', 3);

insert into users(user_name, user_password, user_active, user_role_id)
values('washington', '716867a5cf04f066656259c4bb8ff5cedf73b7b690755e24c8acc455cd1604fa',
true, 101);

insert into user_salt(user_salt_value, user_name)
values('89732621769187643987', 'washington');

insert into profile_type(profile_type_id, profile_type_desc)
values(100, 'Default Profile');

insert into profile_type(profile_type_id, profile_type_desc)
values(101, 'App Manager Profile');

insert into profile_type(profile_type_id, profile_type_desc)
values(102, 'Tutor Profile');

insert into profile_type(profile_type_id, profile_type_desc)
values(103, 'Student Profile');

insert into gender values ('M', 'MALE');
insert into gender values ('F', 'FEMALE');

insert into citizenship values(100, 'SOUTH AFRICAN');
insert into citizenship values(200, 'NON-SOUTH AFRICAN');

insert into profile(profile_type_id, user_name)
values(101, 'washington');

insert into contact_detail(contact_detail_cell_number, contact_detail_email_address)
values('0784755987', 'wchingwa@yahoo.com');

insert into physical_address(physical_address_line_1, physical_address_line_2, physical_address_suburb, 
physical_address_city, physical_address_area_code) 
values('Bramley View', '', 'bramley', 'Johannesburg', '2086');

insert into person(person_last_name, person_first_names, person_date_of_birth, person_id_number, 
gender_id, citizenship_id, contact_detail_id, physical_address_id, user_name)
values('Chigwaza', 'Washington', '1980-01-01 00:00.00', '8001010000000', 'M', 
100, (select max(contact_detail_id) from contact_detail), (select max(physical_address_id) from physical_address), 
'washington');

update profile set profile_created = CURRENT_TIMESTAMP, 
profile_verified = TRUE, profile_verification_code = '000000'
where user_name = 'washington';

insert into event(event_type, event_short_desc, event_desc)
values(1000, 'Login', 'Authentication - Login');

commit;