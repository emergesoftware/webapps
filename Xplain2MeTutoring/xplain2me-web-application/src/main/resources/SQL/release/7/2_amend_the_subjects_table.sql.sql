BEGIN;

USE xplain2me;

-- truncate table
delete from subject where subject_id >= 100 and subject_id <= 123;

-- alter the subject table
alter table subject add grade int not null default 0;

-- add a unique constraint
alter table subject add constraint subject_name_grade_ukey unique(subject_name, grade);

-- re-insert the subjects: foundation phase
insert into subject (subject_id, subject_name, grade)
values (1001, 'Mathematics - CAPS - Grade 1', 1);
insert into subject (subject_id, subject_name, grade)
values (1002, 'Mathematics - CAPS - Grade 2', 2);
insert into subject (subject_id, subject_name, grade)
values (1003, 'Mathematics - CAPS - Grade 3', 3);

insert into subject (subject_id, subject_name, grade)
values (1004, 'English (Home Language) - CAPS - Grade 1', 1);
insert into subject (subject_id, subject_name, grade)
values (1005, 'English (Home Language) - CAPS - Grade 2', 2);
insert into subject (subject_id, subject_name, grade)
values (1006, 'English (Home Language) - CAPS - Grade 3', 3);

insert into subject (subject_id, subject_name, grade)
values (1007, 'English (First Add. Language) - CAPS - Grade 1', 1);
insert into subject (subject_id, subject_name, grade)
values (1008, 'English (First Add. Language) - CAPS - Grade 2', 2);
insert into subject (subject_id, subject_name, grade)
values (1009, 'English (First Add. Language) - CAPS - Grade 3', 3);

-- re-insert the subjects: intermediate phase
insert into subject (subject_id, subject_name, grade)
values (1010, 'Mathematics - CAPS - Grade 4', 4);
insert into subject (subject_id, subject_name, grade)
values (1011, 'Mathematics - CAPS - Grade 5', 5);
insert into subject (subject_id, subject_name, grade)
values (1012, 'Mathematics - CAPS - Grade 6', 6);

insert into subject (subject_id, subject_name, grade)
values (1013, 'English (Home Language) - CAPS - Grade 4', 4);
insert into subject (subject_id, subject_name, grade)
values (1014, 'English (Home Language) - CAPS - Grade 5', 5);
insert into subject (subject_id, subject_name, grade)
values (1015, 'English (Home Language) - CAPS - Grade 6', 6);

insert into subject (subject_id, subject_name, grade)
values (1016, 'English (First Add. Language) - CAPS - Grade 4', 4);
insert into subject (subject_id, subject_name, grade)
values (1017, 'English (First Add. Language) - CAPS - Grade 5', 5);
insert into subject (subject_id, subject_name, grade)
values (1018, 'English (First Add. Language) - CAPS - Grade 6', 6);

-- re-insert subjects: senior phase
insert into subject (subject_id, subject_name, grade)
values (1019, 'Mathematics - CAPS - Grade 7', 7);
insert into subject (subject_id, subject_name, grade)
values (1020, 'Mathematics - CAPS - Grade 8', 8);
insert into subject (subject_id, subject_name, grade)
values (1021, 'Mathematics - CAPS - Grade 9', 9);

insert into subject (subject_id, subject_name, grade)
values (1022, 'English (Home Language) - CAPS - Grade 7', 7);
insert into subject (subject_id, subject_name, grade)
values (1023, 'English (Home Language) - CAPS - Grade 8', 8);
insert into subject (subject_id, subject_name, grade)
values (1024, 'English (Home Language) - CAPS - Grade 9', 9);

insert into subject (subject_id, subject_name, grade)
values (1025, 'English (First Add. Language) - CAPS - Grade 7', 7);
insert into subject (subject_id, subject_name, grade)
values (1026, 'English (First Add. Language) - CAPS - Grade 8', 8);
insert into subject (subject_id, subject_name, grade)
values (1027, 'English (First Add. Language) - CAPS - Grade 9', 9);

insert into subject (subject_id, subject_name, grade)
values (1028, 'Natural Science - CAPS - Grade 7', 7);
insert into subject (subject_id, subject_name, grade)
values (1029, 'Natural Science - CAPS - Grade 8', 8);
insert into subject (subject_id, subject_name, grade)
values (1030, 'Natural Science - CAPS - Grade 9', 9);

insert into subject (subject_id, subject_name, grade)
values (1031, 'Social Science - CAPS - Grade 7', 7);
insert into subject (subject_id, subject_name, grade)
values (1032, 'Social Science - CAPS - Grade 8', 8);
insert into subject (subject_id, subject_name, grade)
values (1033, 'Social Science - CAPS - Grade 9', 9);

insert into subject (subject_id, subject_name, grade)
values (1034, 'Technology - CAPS - Grade 7', 7);
insert into subject (subject_id, subject_name, grade)
values (1035, 'Technology - CAPS - Grade 8', 8);
insert into subject (subject_id, subject_name, grade)
values (1036, 'Technology - CAPS - Grade 9', 9);

-- re-insert subjects: further education and training
insert into subject (subject_id, subject_name, grade)
values (1037, 'Mathematics - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1038, 'Mathematics - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1039, 'Mathematics - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1040, 'Mathematical Literacy - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1041, 'Mathematical Literacy - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1042, 'Mathematical Literacy - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1043, 'Afrikaans (First Add. Language) - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1044, 'Afrikaans (First Add. Language) - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1045, 'Afrikaans (First Add. Language) - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1046, 'Physical Sciences - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1047, 'Physical Sciences - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1048, 'Physical Sciences - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1049, 'Life Sciences - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1050, 'Life Sciences - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1051, 'Life Sciences - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1052, 'Accounting - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1053, 'Accounting - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1054, 'Accounting - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1055, 'Information Technology - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1056, 'Information Technology - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1057, 'Information Technology - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1058, 'English (Home Language) - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1059, 'English (Home Language) - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1060, 'English (Home Language) - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1061, 'English (First Add. Language) - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1062, 'English (First Add. Language) - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1063, 'English (First Add. Language) - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1064, 'Business Studies - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1065, 'Business Studies - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1066, 'Business Studies - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1067, 'Economics - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1068, 'Economics - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1069, 'Economics - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1070, 'Agricultural Sciences - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1071, 'Agricultural Sciences - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1072, 'Agricultural Sciences - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1073, 'Civil Technology - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1074, 'Civil Technology - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1075, 'Civil Technology - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1076, 'Electrical Technology - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1077, 'Electrical Technology - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1078, 'Electrical Technology - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1079, 'Mechanical Technology - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1080, 'Mechanical Technology - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1081, 'Mechanical Technology - CAPS - Grade 12', 12);

insert into subject (subject_id, subject_name, grade)
values (1082, 'Geography - CAPS - Grade 10', 10);
insert into subject (subject_id, subject_name, grade)
values (1083, 'Geography - CAPS - Grade 11', 11);
insert into subject (subject_id, subject_name, grade)
values (1084, 'Geography - CAPS - Grade 12', 12);

-- re-insert subjects: undergraduate level
insert into subject (subject_id, subject_name, grade)
values (1085, 'English (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1086, 'Calculus (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1087, 'Linear (Matrix) Algebra (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1088, 'Abstract Algebra (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1089, 'Physics (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1090, 'Chemistry (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1091, 'Statistics (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1092, 'Applied Mathematics (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1093, 'Accounting (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1094, 'Business Management (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1095, 'Electronics & Electrotechnics (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1096, 'Finance (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1097, 'Taxation (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1098, 'Mercantile Law (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1099, 'Criminal Law (Undergraduate)', 13);

insert into subject (subject_id, subject_name, grade)
values (1100, 'Software Engineering & Development (Undergraduate)', 13);

-- alter the table: subject
alter table subject add academic_level_id int not null default 0;

-- update the subjects accordingly
update subject set academic_level_id = 1 where grade >= 1 and grade <= 3;
update subject set academic_level_id = 2 where grade >= 4 and grade <= 6;
update subject set academic_level_id = 3 where grade >= 7 and grade <= 9;
update subject set academic_level_id = 4 where grade >= 10 and grade <= 12;
update subject set academic_level_id = 5 where grade = 13;

-- add a foreign key constraint to the table: subject
alter table subject add foreign key (academic_level_id) references academic_level(academic_level_id);

-- rename the column grade to subject_grade in table: subject
alter table subject change column grade subject_grade int not null default 0;

COMMIT;
