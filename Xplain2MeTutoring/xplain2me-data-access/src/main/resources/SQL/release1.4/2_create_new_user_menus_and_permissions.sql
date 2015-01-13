BEGIN;

USE xplain2me;

-- CREATE A NEW USER MANAGEMENT CATEGORY
insert into menu_category
values(6, 'User Management', 'glyphicon glyphicon-user', true);

-- CREATE NEW MENU
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (15, 'Browse Users', 'Browse existing users authorised to manage',
 '/portal/users/browse', true, 6);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (16, 'Add User', 'Add a new user', '/portal/users/add', true, 6);

insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (17, 'Edit User', 'Edit existing user profile', '/portal/users/edit-profile', false, 6);

-- GRANT PERMISSIONS TO THE APPLICATION MANAGER (101)
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able view users he is authorised to manage.', 101, 15);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able add new users below his own profile privilege.', 101, 16);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able edit a user profile below his own profile privilege.', 101, 17);

COMMIT;
