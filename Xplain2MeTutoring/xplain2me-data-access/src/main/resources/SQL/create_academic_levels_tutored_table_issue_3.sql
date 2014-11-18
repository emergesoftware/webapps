BEGIN;

CREATE TABLE IF NOT EXISTS academic_levels_tutored_before (
    academic_levels_tutored_before_id INT(11)  NOT NULL AUTO_INCREMENT UNIQUE,
    become_tutor_request_id INT(11) NOT NULL,
    academic_level_id INT(11) NOT NULL,

    PRIMARY KEY (academic_levels_tutored_before_id),
    FOREIGN KEY (become_tutor_request_id) REFERENCES become_tutor_request(become_tutor_request_id),
    FOREIGN KEY (academic_level_id) REFERENCES academic_level(academic_level_id)
);

CREATE TABLE IF NOT EXISTS become_tutor_supporting_documents (
    become_tutor_supporting_documents_id INT(11)  NOT NULL AUTO_INCREMENT UNIQUE,
    become_tutor_supporting_documents_label VARCHAR(64) NOT NULL,
    become_tutor_supporting_documents_file LONGBLOB NULL,
    become_tutor_request_id INT(11) NOT NULL,

    PRIMARY KEY (become_tutor_supporting_documents_id),
    FOREIGN KEY (become_tutor_request_id) REFERENCES become_tutor_request(become_tutor_request_id)
);

ALTER TABLE become_tutor_request ADD become_tutor_request_tutored_before BOOLEAN DEFAULT FALSE NOT NULL;
ALTER TABLE become_tutor_request ADD become_tutor_request_motivation_text TEXT;

COMMIT;
