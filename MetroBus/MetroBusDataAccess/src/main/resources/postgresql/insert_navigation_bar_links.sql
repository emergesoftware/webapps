begin;

insert into navigation_bar_link
values(1, 'Home', null, '/index', 'glyphicon glyphicon-home', true);

insert into navigation_bar_link
values(2, 'Time Tables', null, '/time-tables/select-option', 'glyphicon glyphicon-th-lg', true);

insert into navigation_bar_link
values(3, 'Search', null, '/search/find-available-routes', 'glyphicon glyphicon-search', true);

insert into navigation_bar_link
values(4, 'Routes', null, '/routes/info', 'glyphicon glyphicon-locate', true);

insert into navigation_bar_link
values(5, 'Notifications', null, '/notifications/main', 'glyphicon glyphicon-bell', true);


commit;
