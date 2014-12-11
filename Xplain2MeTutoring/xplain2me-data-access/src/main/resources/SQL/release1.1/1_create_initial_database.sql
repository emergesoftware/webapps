BEGIN; 

-- DROP AND CREATE THE SCHEMA
DROP DATABASE xplain2me;
CREATE DATABASE xplain2me;

-- DROP THE USER ACCOUNTS
DROP USER 'xplain2me'@'localhost';
DROP USER 'xplain2me'@'127.0.0.1';

-- CREATE A NEW USER @ LOCALHOST
CREATE USER 'xplain2me'@'localhost' IDENTIFIED BY '#xplain2me10XMORE';
CREATE USER 'xplain2me'@'127.0.0.1' IDENTIFIED BY '#xplain2me10XMORE';

GRANT ALL PRIVILEGES ON * . * TO 'xplain2me'@'localhost';
GRANT ALL PRIVILEGES ON * . * TO 'xplain2me'@'127.0.0.1';

FLUSH PRIVILEGES;

USE xplain2me;

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone="+00:00";

-- ACADEMIC LEVELS
CREATE TABLE IF NOT EXISTS academic_level (

    academic_level_id INT(11) NOT NULL AUTO_INCREMENT,
    academic_level_desc VARCHAR(128) NOT NULL,

    PRIMARY KEY (academic_level_id)
);

-- APP CLIENT INFORMATION
CREATE TABLE IF NOT EXISTS app_client (

    app_client_id INT(11) NOT NULL AUTO_INCREMENT,
    app_client_name VARCHAR(128) NOT NULL,
    app_client_trading_name VARCHAR(128) NOT NULL,
    app_client_business_reg_number VARCHAR(64),
    app_client_contact_number VARCHAR(24) NOT NULL,
    app_client_contact_person VARCHAR(128) NOT NULL,
    app_client_physical_address TEXT,
    app_client_postal_address TEXT,
    app_client_email_address TEXT NOT NULL,
    app_client_date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    app_client_active BOOLEAN DEFAULT 1 NOT NULL,
    app_client_date_inactive TIMESTAMP,

    PRIMARY KEY (app_client_id)
);

-- USERS
CREATE TABLE IF NOT EXISTS users (
    
    user_id INT AUTO_INCREMENT NOT NULL UNIQUE,
    user_name VARCHAR(24) NOT NULL UNIQUE,
    user_password TEXT NOT NULL,
    user_active BOOLEAN DEFAULT 0 NOT NULL,
    user_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_deactivated TIMESTAMP,
    user_role_id INT(11) NOT NULL,

    PRIMARY KEY (user_id)
);

-- EVENT
CREATE TABLE IF NOT EXISTS event (
    event_type INT(11) NOT NULL,
    event_short_desc VARCHAR(128) NOT NULL,
    event_desc TEXT NOT NULL,
    event_is_financial bool DEFAULT 0 NOT NULL,

    PRIMARY KEY (event_type)
);

-- AUDIT
CREATE TABLE IF NOT EXISTS audit (

    audit_id INT(11) NOT NULL AUTO_INCREMENT,
    audit_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    event_type INT(11) NOT NULL,
    user_id INT NOT NULL,
    audit_reference INT(11) DEFAULT 0 NOT NULL,
    audit_xml TEXT,
    audit_ip_address VARCHAR(255) NOT NULL DEFAULT '127.0.0.1',
    audit_user_agent TEXT NOT NULL,
    audit_authority_code INT(11),
    audit_authorised bool DEFAULT 1 NOT NULL,

    PRIMARY KEY (audit_id),
    FOREIGN KEY (event_type) REFERENCES event(event_type),
    FOREIGN KEY (user_id) REFERENCES users(user_id)

);

-- PHYSICAL ADDRESS
CREATE TABLE IF NOT EXISTS physical_address (

    physical_address_id INT(11) AUTO_INCREMENT NOT NULL,
    physical_address_line_1 TEXT NOT NULL,
    physical_address_line_2 TEXT,
    physical_address_suburb TEXT NOT NULL,
    physical_address_city TEXT NOT NULL,
    physical_address_area_code TEXT, 

    PRIMARY KEY (physical_address_id)
);

-- CITIZENSHIP
CREATE TABLE IF NOT EXISTS citizenship (

    citizenship_id INT(11) NOT NULL,
    citizenship_desc VARCHAR(128) NOT NULL,

    PRIMARY KEY (citizenship_id)
);

-- CONTACT DETAILS
CREATE TABLE IF NOT EXISTS contact_detail (

    contact_detail_id INT(11) AUTO_INCREMENT NOT NULL,
    contact_detail_cell_number VARCHAR(32) NOT NULL,
    contact_detail_email_address TEXT NOT NULL,

    PRIMARY KEY(contact_detail_id)
);

-- GENDER
CREATE TABLE IF NOT EXISTS gender (
    gender_id VARCHAR(1) NOT NULL UNIQUE,
    gender_desc VARCHAR(64) NOT NULL,

    PRIMARY KEY (gender_id)
);

-- PERSON
CREATE TABLE IF NOT EXISTS person (

    person_id INT(11) AUTO_INCREMENT NOT NULL,
    person_last_name VARCHAR(128) NOT NULL,
    person_first_names TEXT NOT NULL,
    person_date_of_birth TIMESTAMP NOT NULL,
    person_id_number VARCHAR(32) NOT NULL,
    gender_id VARCHAR(1) NOT NULL,
    citizenship_id INT(11)  NOT NULL,
    contact_detail_id INT(11)  NOT NULL,
    physical_address_id INT(11)  NOT NULL,
    user_id INT NOT NULL,

    PRIMARY KEY (person_id),
    FOREIGN KEY (gender_id) REFERENCES gender(gender_id),
    FOREIGN KEY (citizenship_id) REFERENCES citizenship(citizenship_id),
    FOREIGN KEY (contact_detail_id) REFERENCES contact_detail(contact_detail_id),
    FOREIGN KEY (physical_address_id) REFERENCES physical_address(physical_address_id),
    FOREIGN KEY (user_id) references users(user_id)
);

-- PROFILE TYPE
CREATE TABLE IF NOT EXISTS profile_type (

    profile_type_id INT(11) NOT NULL,
    profile_type_desc VARCHAR(64) NOT NULL,
    profile_type_active BOOLEAN DEFAULT TRUE NOT NULL,

    PRIMARY KEY (profile_type_id)
);

-- PROFILE
CREATE TABLE IF NOT EXISTS profile (

    profile_id INT(11) AUTO_INCREMENT NOT NULL,
    profile_type_id INT(11) NOT NULL,
    profile_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    person_id INT NOT NULL,
    profile_verified BOOLEAN DEFAULT 0 NOT NULL,
    profile_verification_code TEXT,

    PRIMARY KEY (profile_id),
    FOREIGN KEY (person_id) REFERENCES person(person_id),
    FOREIGN KEY (profile_type_id) REFERENCES profile_type(profile_type_id)

);

-- SUBJECT
CREATE TABLE IF NOT EXISTS subject (
    subject_id INT(11) NOT NULL,
    subject_name VARCHAR(128) NOT NULL,

    PRIMARY KEY (subject_id)
);

-- TUTOR REQUEST
CREATE TABLE IF NOT EXISTS tutor_request (

    tutor_request_id INT(11) AUTO_INCREMENT NOT NULL,
    tutor_request_first_names TEXT NOT NULL,
    tutor_request_last_name VARCHAR(128) NOT NULL,
    tutor_request_gender bool DEFAULT 1 NOT NULL,
    tutor_request_email_address TEXT NOT NULL,
    tutor_request_contact_number VARCHAR(16) NOT NULL,
    tutor_request_street_address TEXT NOT NULL,
    tutor_request_city VARCHAR(128) NOT NULL,
    tutor_request_suburb VARCHAR(128) NOT NULL,
    tutor_request_area_code VARCHAR(6) NOT NULL,
    academic_level_id INT(11)  NOT NULL,
    tutor_request_additional_information text,
    tutor_request_agreed_to_terms_of_service bool DEFAULT 0 NOT NULL,
    tutor_request_received BOOLEAN DEFAULT 0 NOT NULL,
    tutor_request_date_received TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    tutor_request_request_ref_number VARCHAR(32) NOT NULL UNIQUE,

    PRIMARY KEY (tutor_request_id),
    FOREIGN KEY (academic_level_id) REFERENCES academic_level(academic_level_id)
);

-- TUTOR REQUEST SUBJECTS
CREATE TABLE IF NOT EXISTS tutor_request_subjects (

    tutor_request_subjects_id INT(11) AUTO_INCREMENT NOT NULL,
    tutor_request_id INT(11)  NOT NULL,
    subject_id INT(11)  NOT NULL,
    
    PRIMARY KEY(tutor_request_subjects_id),
    FOREIGN KEY (tutor_request_id) REFERENCES tutor_request(tutor_request_id),
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);

ALTER TABLE tutor_request_subjects ADD CONSTRAINT tutor_request_subjects_u_key 
UNIQUE (tutor_request_id, subject_id);

-- USER SALT
CREATE TABLE IF NOT EXISTS user_salt (

    user_salt_id INT(11) AUTO_INCREMENT NOT NULL,
    user_salt_value TEXT NOT NULL,
    user_salt_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_id INT NOT NULL,
    
    PRIMARY KEY(user_salt_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- BECOME A TUTOR
CREATE TABLE IF NOT EXISTS become_tutor_request (

    become_tutor_request_id int(11) auto_increment not null unique,
    become_tutor_request_last_name varchar(256) not null,
    become_tutor_request_first_names varchar(256) not null,
    become_tutor_request_contact_number varchar(13) not null unique,
    become_tutor_request_email_address varchar(256) not null unique,
    become_tutor_request_street_address text not null,
    become_tutor_request_suburb varchar(128) not null,
    become_tutor_request_city varchar(128) not null,
    become_tutor_request_area_code varchar(6) not null,
    become_tutor_request_id_number varchar(13) not null unique,
    become_tutor_request_date_of_birth timestamp not null,
    citizenship_id int(11) not null,
    gender_id varchar(1) not null,
    agreed_to_terms_of_service boolean default false not null ,
    become_tutor_request_submitted timestamp not null,
    become_tutor_request_tutored_before BOOLEAN DEFAULT FALSE NOT NULL,
    become_tutor_request_motivation_text TEXT,
    
    primary key (become_tutor_request_id),
    foreign key (citizenship_id) references citizenship(citizenship_id),
    foreign key (gender_id) references gender(gender_id)

);

-- ACADEMIC LEVELS TUTORED BEFORE
CREATE TABLE IF NOT EXISTS academic_levels_tutored_before (

    academic_levels_tutored_before_id INT(11) NOT NULL AUTO_INCREMENT UNIQUE,
    become_tutor_request_id INT(11) NOT NULL,
    academic_level_id INT(11) NOT NULL,

    PRIMARY KEY (academic_levels_tutored_before_id),
    FOREIGN KEY (become_tutor_request_id) REFERENCES become_tutor_request(become_tutor_request_id),
    FOREIGN KEY (academic_level_id) REFERENCES academic_level(academic_level_id)
);

-- BECOME A TUTOR SUPPORTING DOCUMENTS
CREATE TABLE IF NOT EXISTS become_tutor_supporting_documents (

    become_tutor_supporting_documents_id INT(11)  NOT NULL AUTO_INCREMENT UNIQUE,
    become_tutor_supporting_documents_label VARCHAR(64) NOT NULL,
    become_tutor_supporting_documents_file LONGBLOB NULL,
    become_tutor_request_id INT(11) NOT NULL,

    PRIMARY KEY (become_tutor_supporting_documents_id),
    FOREIGN KEY (become_tutor_request_id) REFERENCES become_tutor_request(become_tutor_request_id)
);

-- PROVINCES
create table if not exists province (
    province_id int(11) not null unique,
    province_desc varchar(64) not null unique,

    primary key (province_id)
);

-- MENU CATEOGORY TABLE
create table menu_category (

    menu_category_id int not null unique,
    menu_category_desc varchar(255) not null unique,
    menu_category_glyphicon_css varchar(255) not null,
    menu_category_load_onto_panel boolean default true not null,

    primary key (menu_category_id)
);

-- MENU ITEM TABLE
create table menu_item (

    menu_item_id int not null unique,
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

COMMIT;