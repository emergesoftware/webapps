BEGIN;

USE xplain2me;

-- ADD THE TUTOR JOB APPLICATIONS MENU ITEMS
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (10, 'Browse Applications', 'Browse the tutor job application',
 '/portal/tutor-job-applications/browse', true, 3);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (11, 'View Tutor Job Application Details', 'View the details of the tutor job application',
 '/portal/tutor-job-applications/details', false, 3);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (12, 'Download supporting documentation', 'Download supporting documentation',
 '/portal/tutor-job-applications/documents/download', false, 3);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (13, 'Approve Tutor Job Application', 'Approve Tutor Job Application',
 '/portal/tutor-job-applications/approve', false, 3);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (14, 'Search Applications', 'Search for existing tutor job application',
 '/portal/tutor-job-applications/search', true, 3);

-- GRANT PERMISSIONS TO THE APP MANAGER
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can browse tutor job applications', 101, 10);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can view all details of a tutor job applications', 101, 11);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can download the supporting documents of a tutor job application', 101, 12);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can approve a tutor job application', 101, 13);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can search tutor job applications', 101, 14);

COMMIT;
