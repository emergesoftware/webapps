BEGIN;

INSERT INTO event(event_type, event_short_desc, event_desc, event_is_financial)
VALUES(1020, 'Mark Tutor Request As Read', 'Amend Tutor Request - Mark As Read', false);

COMMIT;