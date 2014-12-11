BEGIN;

USE xplain2me;

-- REVOKE ALL PERMISSIONS EVERYONE
delete from profile_type_url_permissions;

-- GRANT PERMISSIONS TO THE APPLICATION MANAGER (101)
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able to view their own dashboard', 101, 1);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able edit their own profile', 101, 7);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able to edit their login credentials', 101, 8);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager is able to logout', 101, 9);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can view unread tutor requests', 101, 2);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can mark tutor requests as read', 101, 3);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can search for tutor requests', 101, 4);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can view all details of a tutor request', 101, 5);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can remove a tutor request', 101, 6);

COMMIT;
