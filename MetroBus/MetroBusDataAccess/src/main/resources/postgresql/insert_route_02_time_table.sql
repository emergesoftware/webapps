
-- inserts all data associated with the bus route: 02

begin;

-- remove all route 02 records

delete from bus_arrivals where bus_departures_id in 
(select bus_departures_id from bus_departures where route_number = '02');

delete from bus_departures where route_number = '02';

delete from bus_route_desc where route_number = '02';

delete from route_bus_stop where route_number = '02';

-- INSERT THE BUS ROUTE DESCRIPTIONS

-- no bus route descriptions were found

-- INSERT THE BUS STOPS FOR THIS ROUTE

-- no bus stops data was found

-- INSERT THE BUS DEPARTURES FOR THIS BUS ROUTE + 
-- BUS ARRIVALS

--[1]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('02', 104, '06:20', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 105, false, true);

-- [2]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('02', 104, '16:20', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 105, false, true);

-- [3]

insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('02', 105, '07:00', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 104, false, true);

-- [4]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('02', 105, '16:30', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 104, false, true);

-- [5]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('02', 106, '07:05', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 104, false, true);

-- [6]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('02', 106, '16:40', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 105, false, true);

-- [7]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('02', 100, '17:05', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 105, false, true);

commit;
