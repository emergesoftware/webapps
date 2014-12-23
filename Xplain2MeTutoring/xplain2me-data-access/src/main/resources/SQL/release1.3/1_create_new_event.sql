BEGIN;

USE xplain2me;

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1027, 'Send Tutor Job Interview Invitation', 'Tutor Job Applications Management - Approve', false);

COMMIT;
