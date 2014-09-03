begin;

insert into bus_route
values('01', 'GANDHI SQUARE TO PARKHURST VIA OXFORD ROAD', true, true, false); 

insert into bus_route
values('02', 'WESTGATE TO BIRNAM VIA METRO CENTRE', true, false, false); 

insert into bus_route
values('03', 'WESTGATE TO HIGHLANDS NORTH VIA ROSEBANK , BRAMLEY', true, false, false); 

insert into bus_route
values('04', 'GANDHI SQUARE TO ROSEBANK VIA HIGHLANDS NORTH BRAMLEY', true, false, false); 

insert into bus_route
values('05', 'STOCK EXCHANGE TO BIRNAM VIA ILLOVO', true, true, false); 

insert into bus_route values('05CD', 'GANDHI SQUARE TO SUNNINGHILL VIA SANDTON', true, true, false);

insert into bus_route
values('05DC', 'SUNNINGHILL / SANDTON TO GANDHI SQUARE', true, true, false);

insert into bus_route 
values('06', 'GANDHI SQUARE TO LEEUWKOP PRISON VIA WOODMEAD, SUNNINGHILL & PETERVALE',
true, false, false);

insert into bus_route
values('07', 'GANDHI SQUARE TO MIDRAND, MIDRAND EXTENTION, CENTURION AND PRETORIA', true, false, false);

insert into bus_route
values ('08', 'STOCK EXCHANGE TO BARLOW PARK VIA WINSTON RIDGE', true, false, false);

insert into bus_route
values ('09', 'GANDHI SQUARE TO LINBRO BUSINESS PARK', true, false, false);

insert into bus_route
values ('10', 'STOCK EXCHANGE TO MELROSE ARCH VIA WAVERLEY', true, true, false);

commit;
