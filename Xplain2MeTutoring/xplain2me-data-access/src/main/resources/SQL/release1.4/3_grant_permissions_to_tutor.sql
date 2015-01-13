BEGIN;

USE xplain2me;

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able to view their own dashboard', 102, 1);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able edit their own profile', 102, 7);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able to edit their login credentials', 102, 8);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able to logout', 102, 9);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able view users he is authorised to manage.', 102, 15);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able add new users below his own profile privilege.', 102, 16);

insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A tutor is able edit a user profile below his own profile privilege.', 102, 17);

COMMIT;
