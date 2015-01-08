BEGIN;

USE xplain2me;

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2000, 'Create New User', 'User Management - Create', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2001, 'Assign User To Profile', 'User Management - User Profile Assignment', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2002, 'Amend User', 'User Management - Update', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2003, 'Deactivate User', 'User Management - Deactivate User', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2004, 'Create New User Profile', 'Profile Management - Create', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2005, 'Amend User Profile', 'Profile Management - Amend', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2006, 'Delete User', 'User Management - Delete', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2007, 'Delete User Profile', 'Profile Management - Delete', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(2008, 'Verify Own Profile', 'Own Profile Management - Verify', false);

COMMIT;