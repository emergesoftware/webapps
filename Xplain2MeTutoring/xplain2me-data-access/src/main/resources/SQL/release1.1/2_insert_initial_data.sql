begin;

USE xplain2me;

-- INSERT THE APPLICATION CLIENT INFORMATION
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

-- INSERT THE ACADEMIC LEVELS
insert into academic_level values(1, 'Foundation Phase - Grades 1 - 3');
insert into academic_level values(2, 'Intermediate Phase - Grades 4 - 6');
insert into academic_level values(3, 'Senior Phase - Grades 7 - 9');
insert into academic_level values(4, 'Further Education and Training Phase - Grades 10 - 12');
insert into academic_level values(5, 'Post Matric Level - (College or University)');

-- INSERT THE SUBJECTS
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

-- INSERT THE DIFFERENT USER PROFILES
insert into profile_type(profile_type_id, profile_type_desc)
values(100, 'Default Profile');

insert into profile_type(profile_type_id, profile_type_desc)
values(101, 'Manager Profile');

insert into profile_type(profile_type_id, profile_type_desc)
values(102, 'Tutor Profile');

insert into profile_type(profile_type_id, profile_type_desc)
values(103, 'Student Profile');

-- INSERT THE GENDER ENTRIES
insert into gender values ('M', 'MALE');
insert into gender values ('F', 'FEMALE');

-- INSERT THE CITIZENSHIPS
insert into citizenship values(100, 'SOUTH AFRICAN');
insert into citizenship values(200, 'NON-SOUTH AFRICAN');

-- INSERT THE PROVINCES
insert into province values(1, 'Gauteng');
insert into province values(2, 'Mpumalanga');
insert into province values(3, 'Limpopo');
insert into province values(4, 'North West');
insert into province values(5, 'Free State');
insert into province values(6, 'KwaZulu Natal');
insert into province values(7, 'Eastern Cape');
insert into province values(8, 'Northern Cape');
insert into province values(9, 'Western Cape');

-- CREATE A NEW USER (APPLICATION MANAGER)
insert into users(user_name, user_password, user_active)
values('washington', '716867a5cf04f066656259c4bb8ff5cedf73b7b690755e24c8acc455cd1604fa', true);

-- CREATE A PASSWORD SALT ENTRY FOR THE NEW USER
insert into user_salt(user_salt_value, user_id)
values('89732621769187643987', (select max(user_id) from users));

-- CREATE CONTACT DETAILS FOR THE NEW USER
insert into contact_detail(contact_detail_cell_number, contact_detail_email_address)
values('0784755987', 'wchingwa@yahoo.com');

-- CREATE THE PHYSICAL ADDRESS DETAILS FOR THE NEW USER
insert into physical_address(physical_address_line_1, physical_address_line_2, physical_address_suburb, 
physical_address_city, physical_address_area_code) 
values('0C Corlett Drive', 'Bramley View', 'Bramley', 'Johannesburg', '2086');

-- CREATE A NEW PERSON FOR THE NEW USER
insert into person(person_last_name, person_first_names, person_date_of_birth, person_id_number, 
gender_id, citizenship_id, contact_detail_id, physical_address_id, user_id)
values('Chigwaza', 'Washington', '1980-01-01 00:00.00', '8001010000000', 'M', 
100, (select max(contact_detail_id) from contact_detail), (select max(physical_address_id) from physical_address), 
(select max(user_id) from users));

-- INSERT THE NEW USER INTO A PROFILE
insert into profile(profile_type_id, profile_created, profile_verified, 
profile_verification_code, person_id)
values(101, CURRENT_TIMESTAMP, TRUE, '000000', (select max(person_id) from person));

commit;