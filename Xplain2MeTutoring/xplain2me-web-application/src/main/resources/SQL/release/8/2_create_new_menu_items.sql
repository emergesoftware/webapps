use xplain2me;

begin;

-- create a new category: Tutor Management
insert into menu_category values(8, 'Tutor Management', 'glyphicon glyphicon-education', true);

-- add menu item (id=25)
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, 
                      menu_item_opens_on_new_window, menu_item_load_onto_panel, menu_category_id)
values(25, 'Create Tutor', 'Assign an existing user as a tutor', '/portal/tutors/create',
       false, true, 8);

-- add menu item (id=26)
insert into menu_item(menu_item_id, menu_item_desc, menu_item_title, menu_item_relative_url, 
                      menu_item_opens_on_new_window, menu_item_load_onto_panel, menu_category_id)
values(26, 'Assign Subjects to Tutor', 'Assign Subjects to Tutor', '/portal/tutors/assign-subjects',
       false, false, 8);

commit;
