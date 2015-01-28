BEGIN;

USE xplain2me;

-- create the view user profile menu item
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (18, 'View User Profile', 'View existing user profile', '/portal/users/view-profile', false, 6);

-- grant the manager permissions to the previous menu
-- item (id=18).
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able to view user profile below his own profile privilege.', 101, 18);

-- grant the tutor permissions to the previous menu
-- item (id=18).
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able to view user profile below his own profile privilege.', 102, 18);

-- create the edit user account profile type menu item
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (19, 'Change User Profile Type', 'Change User Profile Type', 
'/portal/users/change-profile-type', false, 6);

-- grant the manager the permission to access
-- menu item (id=19)
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able to change user profile type below his own profile privilege.', 101, 19);

-- create the activate or block user profile menu item
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (20, 'Activate / Block User', 'Activate / Block User', 
'/portal/users/activate-or-block-user', false, 6);

-- grant the manager the permission to access
-- menu item (id=20)
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able to activate or block users below his own profile privilege.', 101, 20);

-- grant the tutor the permission to access
-- menu item (id=20)
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able to activate or block users below his own profile privilege.', 102, 20);

-- create new event to activate user
INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2009, 'Activate User', 'User Management - Active User', false);

-- create the delete user profile menu item
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (21, 'Delete User', 'Delete existing user', 
'/portal/users/delete-user-profile', false, 6);

-- grant the manager the permission to access
-- menu item (id=21)
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able to delete users below his own profile privilege.', 101, 21);

-- grant the tutor the permission to access
-- menu item (id=21)
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able to delete users below his own profile privilege.', 102, 21);

-- create the search user profile menu item
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, menu_item_load_onto_panel, menu_category_id) 
values (22, 'Search Users', 'Search for an existing user', 
'/portal/users/search', true, 6);

-- grant the manager the permission to access
-- menu item (id=22)
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able to search for users below his own profile privilege.', 101, 22);

-- grant the tutor the permission to access
-- menu item (id=22)
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able to search for users below his own profile privilege.', 102, 22);

COMMIT;
