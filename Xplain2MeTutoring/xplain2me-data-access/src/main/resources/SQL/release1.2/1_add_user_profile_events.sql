BEGIN;

USE xplain2me;

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1022, 'Update Own Login Password', 'Amend Own User Credentials - Update Password', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1023, 'Update Own Login Username', 'Amend Own User Credentials - Update Username', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1024, 'Update Own Personal Details', 'Amend Own Personal Information - Update', false);

COMMIT;
