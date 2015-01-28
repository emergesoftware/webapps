BEGIN;

USE xplain2me;

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1022, 'Update Own Login Password', 'Amend Own User Credentials - Update Password', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1023, 'Update Own Login Username', 'Amend Own User Credentials - Update Username', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1024, 'Update Own Personal Details', 'Amend Own Personal Information - Update', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1025, 'Update Own Contact Details', 'Amend Own Contact Details - Update', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1026, 'Update Own Residential Address', 'Amend Own Residential Address - Update', false);

COMMIT;
