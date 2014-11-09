BEGIN; 

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone="+00:00";

 CREATE TABLE IF NOT EXISTS academic_level (
    academic_level_id INT(11) NOT NULL AUTO_INCREMENT,
    academic_level_desc VARCHAR(128) NOT NULL,

    PRIMARY KEY (academic_level_id)
);

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

 CREATE TABLE IF NOT EXISTS user_role (
    user_role_id INT(11) NOT NULL,
    user_role_desc VARCHAR(64) NOT NULL,
    user_role_active BOOLEAN DEFAULT 1 NOT NULL,
    user_role_privilege_level INT(11)  DEFAULT 0 NOT NULL,
    user_role_allowed_login BOOLEAN DEFAULT 1 NOT NULL,

    PRIMARY KEY (user_role_id)
);

 CREATE TABLE IF NOT EXISTS users (
    user_name VARCHAR(24) NOT NULL,
    user_password TEXT NOT NULL,
    user_active BOOLEAN DEFAULT 0 NOT NULL,
    user_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_deactivated TIMESTAMP,
    user_role_id INT(11) NOT NULL,

    PRIMARY KEY (user_name),
    FOREIGN KEY (user_role_id) REFERENCES user_role(user_role_id)
);

 CREATE TABLE IF NOT EXISTS event (
    event_type INT(11) NOT NULL,
    event_short_desc VARCHAR(128) NOT NULL,
    event_desc TEXT NOT NULL,
    event_is_financial bool DEFAULT 0 NOT NULL,

    PRIMARY KEY (event_type)
);

 CREATE TABLE IF NOT EXISTS audit (

    audit_id INT(11) NOT NULL AUTO_INCREMENT,
    audit_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    event_type INT(11) NOT NULL,
    user_name VARCHAR(24) NOT NULL,
    audit_reference INT(11) DEFAULT 0 NOT NULL,
    audit_xml TEXT,
    audit_ip_address VARCHAR(255) NOT NULL DEFAULT '127.0.0.1',
    audit_user_agent TEXT NOT NULL,
    audit_authority_code INT(11),
    audit_authorised bool DEFAULT 1 NOT NULL,

    PRIMARY KEY (audit_id),
    FOREIGN KEY (event_type) REFERENCES event(event_type),
    FOREIGN KEY (user_name) REFERENCES users(user_name)

);

 CREATE TABLE IF NOT EXISTS physical_address (
    physical_address_id INT(11) AUTO_INCREMENT NOT NULL,
    physical_address_line_1 TEXT NOT NULL,
    physical_address_line_2 TEXT,
    physical_address_suburb TEXT NOT NULL,
    physical_address_city TEXT NOT NULL,
    physical_address_area_code TEXT, 

    PRIMARY KEY (physical_address_id)
);

 CREATE TABLE IF NOT EXISTS citizenship (
    citizenship_id INT(11) NOT NULL,
    citizenship_desc VARCHAR(128) NOT NULL,

    PRIMARY KEY (citizenship_id)
);

 CREATE TABLE IF NOT EXISTS contact_detail (
    contact_detail_id INT(11) AUTO_INCREMENT NOT NULL,
    contact_detail_cell_number VARCHAR(32) NOT NULL,
    contact_detail_email_address TEXT NOT NULL,

    PRIMARY KEY(contact_detail_id)
);

 CREATE TABLE IF NOT EXISTS gender (
    gender_id VARCHAR(1) NOT NULL UNIQUE,
    gender_desc VARCHAR(64) NOT NULL,

    PRIMARY KEY (gender_id)
);

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
    user_name VARCHAR(24) NOT NULL,

    PRIMARY KEY (person_id),
    FOREIGN KEY (gender_id) REFERENCES gender(gender_id),
    FOREIGN KEY (citizenship_id) REFERENCES citizenship(citizenship_id),
    FOREIGN KEY (contact_detail_id) REFERENCES contact_detail(contact_detail_id),
    FOREIGN KEY (physical_address_id) REFERENCES physical_address(physical_address_id),
    FOREIGN KEY (user_name) references users(user_name)
);

 CREATE TABLE IF NOT EXISTS profile_type (
    profile_type_id INT(11) NOT NULL,
    profile_type_desc VARCHAR(64) NOT NULL,
    profile_type_active bool DEFAULT 1 NOT NULL,

    PRIMARY KEY (profile_type_id)
);

 CREATE TABLE IF NOT EXISTS profile (
    profile_id INT(11) AUTO_INCREMENT NOT NULL,
    profile_type_id INT(11) NOT NULL,
    profile_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_name VARCHAR(24) NOT NULL,
    profile_verified BOOLEAN DEFAULT 0 NOT NULL,
    profile_verification_code TEXT,

    PRIMARY KEY (profile_id),
    FOREIGN KEY (user_name) REFERENCES users(user_name),
    FOREIGN KEY (profile_type_id) REFERENCES profile_type(profile_type_id)

);

 CREATE TABLE IF NOT EXISTS subject (
    subject_id INT(11) NOT NULL,
    subject_name VARCHAR(128) NOT NULL,

    PRIMARY KEY (subject_id)
);

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
    
    PRIMARY KEY (tutor_request_id),
    FOREIGN KEY (academic_level_id) REFERENCES academic_level(academic_level_id)
);

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

 CREATE TABLE IF NOT EXISTS user_salt (
    user_salt_id INT(11) AUTO_INCREMENT NOT NULL,
    user_salt_value TEXT NOT NULL,
    user_salt_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_name VARCHAR(24) NOT NULL,
    
    PRIMARY KEY(user_salt_id),
    FOREIGN KEY (user_name) REFERENCES users(user_name)
);

COMMIT;