BEGIN;

USE xplain2me;

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1020, 'Mark Tutor Request As Read', 'Amend Tutor Request - Mark As Read', false);

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1021, 'Delete Tutor Request', 'Amend Tutor Request - Delete', false);


COMMIT;