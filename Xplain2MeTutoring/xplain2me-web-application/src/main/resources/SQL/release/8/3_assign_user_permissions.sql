use xplain2me;

begin;

-- grant the permissions for menu (id=25) to the manager
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager create a tutor from an existing user profile.', 101, 25);

-- grant the permissions for menu (id=26) to the manager
insert into profile_type_url_permissions(profile_type_url_permissions_desc, 
profile_type_id, menu_item_id)
values('A manager can assign subjects to an existing tutor.', 101, 26);

commit;
