BEGIN;

ALTER TABLE tutor_request ADD tutor_request_received BOOLEAN NOT NULL DEFAULT 0;
ALTER TABLE tutor_request ADD tutor_request_date_received TIMESTAMP;
ALTER TABLE tutor_request ADD tutor_request_request_ref_number VARCHAR(32);

COMMIT;
