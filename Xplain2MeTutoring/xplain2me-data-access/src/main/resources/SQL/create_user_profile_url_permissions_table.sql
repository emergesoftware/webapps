BEGIN;

-- MENU CATEOGORY TABLE
create table menu_category (
    menu_category_id int auto_increment not null unique,
    menu_category_desc varchar(255) not null unique,
    menu_category_glyphicon_css varchar(255) not null,
    menu_category_load_onto_panel boolean default true not null,

    primary key (menu_category_id)
);

-- MENU ITEM TABLE
create table menu_item (

    menu_item_id int auto_increment not null unique,
    menu_item_desc varchar(255) not null,
    menu_item_title varchar(255) not null,
    menu_item_relative_url varchar(255) not null unique,
    menu_item_opens_on_new_window boolean default false not null,
    menu_item_load_onto_panel boolean default true not null,
    menu_category_id int not null,

    primary key (menu_item_id),
    foreign key (menu_category_id) references menu_category(menu_category_id)
);

-- USER PROFILE ULR PERMISSIONS TABLE
create table profile_type_url_permissions (

    profile_type_url_permissions_id int auto_increment not null unique,
    profile_type_url_permissions_desc text not null,
    profile_type_id int not null,
    menu_item_id int not null,
    profile_type_url_permissions_date_modified timestamp default current_timestamp not null,

    primary key (profile_type_url_permissions_id),
    foreign key (profile_type_id) references profile_type(profile_type_id),
    foreign key (menu_item_id) references menu_item(menu_item_id)
);

-- INSERT CURRENT MENU CATEGORIES IN USE
insert into menu_category(menu_category_desc, menu_category_glyphicon_css, menu_category_load_onto_panel)
values('Dashboard', 'glyphicon glyphicon-th-large', true);

insert into menu_category(menu_category_desc, menu_category_glyphicon_css, menu_category_load_onto_panel)
values('Tutor Requests', 'glyphicon glyphicon-flag', true);

insert into menu_category(menu_category_desc, menu_category_glyphicon_css, menu_category_load_onto_panel)
values('Job Applications', 'glyphicon glyphicon-inbox', true);

insert into menu_category(menu_category_desc, menu_category_glyphicon_css, menu_category_load_onto_panel)
values('Audit Trail', 'glyphicon glyphicon-align-justify', true);

insert into menu_category(menu_category_desc, menu_category_glyphicon_css, menu_category_load_onto_panel)
values('My Profile', 'glyphicon glyphicon-user', true);

-- INSERT MENU ITEMS IN USE AT THE MOMENT
insert into menu_item(menu_item_desc, menu_item_title, menu_item_relative_url, 
menu_item_load_onto_panel, menu_category_id)
values ('User Dashboard', 'An overview of the user dashboard', '/portal/dashboard/overview', true, 1);

insert into menu_item(menu_item_desc, menu_item_title, menu_item_relative_url, 
menu_item_load_onto_panel, menu_category_id)
values ('Unread Tutor Requests', 'Shows a list of unread tutor requests', '/portal/tutor-requests/unread', true, 2);

insert into menu_item(menu_item_desc, menu_item_title, menu_item_relative_url, 
menu_item_load_onto_panel, menu_category_id)
values ('Mark Tutor Request As Read', 'Mark tutor request as read', '/portal/tutor-requests/mark-as-read', false, 2);

insert into menu_item(menu_item_desc, menu_item_title, menu_item_relative_url, 
menu_item_load_onto_panel, menu_category_id)
values ('Search Tutor Requests', 'Search for an existing tutor request', '/portal/tutor-requests/search', true, 2);

insert into menu_item(menu_item_desc, menu_item_title, menu_item_relative_url, 
menu_item_load_onto_panel, menu_category_id)
values ('Tutor Request Details', 'Shows all the details of a tutor request', '/portal/tutor-requests/details', false, 2);

insert into menu_item(menu_item_desc, menu_item_title, menu_item_relative_url, 
menu_item_load_onto_panel, menu_category_id)
values ('Remove Tutor Request', 'Shows a list of unread tutor requests', '/portal/tutor-requests/remove', false, 2);

insert into menu_item(menu_item_desc, menu_item_title, menu_item_relative_url, 
menu_item_load_onto_panel, menu_category_id)
values ('Edit Profile', 'Edit your own profile', '/portal/my-profile/edit', true, 5);

insert into menu_item(menu_item_desc, menu_item_title, menu_item_relative_url, 
menu_item_load_onto_panel, menu_category_id)
values ('Change Login', 'Edit your user login profile', '/portal/account/edit-credentials', true, 5);

insert into menu_item(menu_item_desc, menu_item_title, menu_item_relative_url, 
menu_item_load_onto_panel, menu_category_id)
values ('Log Out', 'Logs a user out', '/account/logout', true, 5);

COMMIT;
