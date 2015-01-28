BEGIN;

USE xplain2me;

-- DELETE ALL ITEMS IN THE MENU ITEMS
DELETE FROM menu_item;
DELETE FROM menu_category;

-- INSERT CURRENT MENU CATEGORIES IN USE
insert into menu_category
values(1, 'Dashboard', 'glyphicon glyphicon-th-large', true);

insert into menu_category
values(2, 'Tutor Requests', 'glyphicon glyphicon-flag', true);

insert into menu_category
values(3, 'Job Applications', 'glyphicon glyphicon-inbox', true);

insert into menu_category
values(4, 'Audit Trail', 'glyphicon glyphicon-align-justify', true);

insert into menu_category
values(5, 'My Profile', 'glyphicon glyphicon-user', true);

-- INSERT MENU ITEMS IN USE AT THE MOMENT
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id)
values (1, 'User Dashboard', 'An overview of the user dashboard', 
'/portal/dashboard/overview', true, 1);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (2, 'Unread Tutor Requests', 'Shows a list of unread tutor requests', 
'/portal/tutor-requests/unread', true, 2);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (3, 'Mark Tutor Request As Read', 'Mark tutor request as read', 
'/portal/tutor-requests/mark-as-read', false, 2);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (4, 'Search Tutor Requests', 'Search for an existing tutor request', 
'/portal/tutor-requests/search', true, 2);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (5, 'Tutor Request Details', 'Shows all the details of a tutor request', 
'/portal/tutor-requests/details', false, 2);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (6, 'Remove Tutor Request', 'Shows a list of unread tutor requests', 
'/portal/tutor-requests/remove', false, 2);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (7, 'Edit Profile', 'Edit your own profile', 
'/portal/my-profile/edit', true, 5);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (8, 'Change Login', 'Edit your user login profile', 
'/portal/account/edit-credentials', false, 5);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (9, 'Log Out', 'Logs a user out', '/account/logout', true, 5);

COMMIT;
