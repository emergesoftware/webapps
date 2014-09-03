
-- inserts all data associated with the bus route: 01 / 01A

begin;

-- remove all route 01 records

delete from bus_arrivals where bus_departures_id in 
(select bus_departures_id from bus_departures where route_number = '01');

delete from bus_departures where route_number = '01';

delete from bus_route_desc where route_number = '01';

delete from route_bus_stop where route_number = '01';

-- INSERT THE BUS ROUTE DESCRIPTIONS

insert into bus_route_desc(bus_route_desc_text, route_number, start_location_id, end_location_id)
values('Turns Left into Eloff Str;
Turns Right into Bree Str;
Turns Left into Nugget Str;
Turns Left into Bok Str;
Turns Right into Twist Str;
Goes along Twist Turning Left into Empire Rd;
Curves Right into Victoria Rd;
Turns Right into Oxford Rd;
Goes Along Oxford;
Turns Left into Worcester Rd;
Turns Right into Bath Ave;
Turns Left into Tyrwhitt Ave;
Curves into 7th Ave;
Turns Right into 1st Ave West;
Turns Left into 10th Str;
Turns Right into 4th Ave;
Turns Right into 20th Str;
Turns Right into 3rd Ave;
Goes Along 3rd Avenue to Terminus at Parkhurst Primary School', 
'01', 100, 102);

insert into bus_route_desc(bus_route_desc_text, route_number, start_location_id, end_location_id)
values('From Parkhurst Primary Goes Along 3rd Ave;
Turns Left into 10th Ave;
Turns Right into 1st Ave West;
Turns Left into 7th Ave;
Curves along 7th Ave to Tyrwhitt Ave;
Turns Right into Bath Ave;
Turns Left into Worcester Rd;
Turns Right into Oxford Rd;
Goes Along Oxford Rd Curving into Victoria Ave;
Turns Left into Empire Rd;
Curves onto Bruce Str;
Turns Right into Claim Str;
Goes Along Claim Str Turning Right into Jeppe Str;
Turns Left into Harrison Str;
Turns Right into Marshall Str;
Turns Left into Rissik Str into Gandhi Square', 
'01', 102, 100);

-- INSERT THE BUS STOPS FOR THIS ROUTE

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 1, 'Commmisioner & Market Street', 
'Commmisioner & Market Street, Johannesburg',
-26.20271, 27.991969);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 1, 'Bree & Eloff Street', 
'Bree & Eloff Street, Johannesburg',
-26.200693, 28.043393);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 1, 'Bree & Twist Street', 
'Bree & Twist Street, Johannesburg',
-26.200075, 28.04875);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 1, 'Nugget & Plein Street', 
'Nugget & Plein Street, Johannesburg',
-26.19889, 28.052146);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 1, 'Bok & Quartz Street', 
'Bok & Quartz Street, Doornfontein',
-26.1958, 28.049157);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 1, 'Twist & Wolmarans Street', 
'Twist & Wolmarans Street, Doornfontein',
-26.194415, 28.047996);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 1, 'Twist & Pretoria Street', 
'Twist & Pretoria Street, Hillbrow',
-26.189075, 28.04724);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 2, 'Oxford Road & Sherwood Street', 
'Oxford Road & Sherwood Street, Forest Town',
-26.173542, 28.039742);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 2, 'Oxford Road & Waltham Street', 
'Oxford Road & Waltham Street, Forest Town',
-26.16921, 28.044072);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 2, 'Oxford Road & Annerly Street', 
'Oxford Road & Annerly Street, Killarney',
-26.16606, 28.047058);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 2, 'Oxford Road & Riveria Road', 
'Oxford Road & Riveria Road, Riveria',
-26.164795, 28.048112);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 2, 'Oxford Road & 11th Avenue', 
'Oxford Road & 11th Avenue, Saxonwold',
-26.155708, 28.046122);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 2, 'Oxford Road & Worcester Road', 
'Oxford Road & Worcester Road, Houghton Estate',
-26.155708, 28.046122);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 2, 'Bath Avenue, The Zone @ Rosebank', 
'Bath Avenue, The Zone @ Rosebank, Rosebank',
-26.146578, 28.0405);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 2, 'Tyrwhitt Avenue & 7th Avenue', 
'Tyrwhitt Avenue & 7th Avenue, Rosebank',
-26.145451, 28.035768);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 2, '2nd Avenue & 7th Avenue', 
'2nd Avenue & 7th Avenue, Parktown North',
-26.145114, 28.03211);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 3, '7th Avenue & 1st Avenue West', 
'7th Avenue & 1st Avenue West, Parktown North',
-26.14392, 28.022604);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 3, '1st Avenue West & 10th Street', 
'1st Avenue West & 10th Street, Parktown North',
-26.141261, 28.023248);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 3, '10th Street & 2nd Avenue', 
'10th Street & 2nd Avenue, Parkhurst',
-26.140838, 28.021231);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 3, '4th Avenue', 
'4th Avenue, Parkhurst',
 -26.1392, 28.017497);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 3, '4th Avenue & 17th Street', 
'4th Avenue & 17th Street, Parkhurst',
  -26.135328, 28.018227);

insert into route_bus_stop(route_number, zone_number, bus_stop_short_desc, bus_stop_full_desc,
                            gps_latitude_coordinate, gps_longitude_coordinate)
values('01', 3, 'Parkhurst Primary, 15th Street', 
'Parkhurst Primary, 15th Street, Parkhurst',
-26.13805, 28.02091);

-- INSERT THE BUS DEPARTURES FOR THIS BUS ROUTE + 
-- BUS ARRIVALS

--[1]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 100, '06:10', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 102, false, true);

-- [2]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 100, '06:40', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 102, false, true);

-- [3]

insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 100, '07:40', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 102, false, true);

-- [4]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 100, '08:10', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 102, false, true);

-- [5]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 100, '11:20', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 102, false, true);

-- [6]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 100, '13:20', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 102, false, true);

-- [7]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 100, '14:20', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 102, false, true);

-- [9]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 100, '16:40', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 102, false, true);

-- [10]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 100, '17:25', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 102, false, true);

-- [11]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 102, '06:35', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 103, false, true);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
2, 100, false, true);

-- [12]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 102, '07:15', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 103, false, true);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
2, 100, false, true);

-- [13]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 102, '08:30', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [14]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 102, '09:00', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [15]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 102, '12:00', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [16]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 102, '14:00', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [17]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 102, '15:00', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 103, false, true);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
2, 100, false, true);

-- [18]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 102, '17:20', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 103, false, true);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
2, 100, false, true);

-- [19]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 102, '18:05', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 103, false, true);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
2, 100, false, true);

-- [20]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '06:35', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [21]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '07:00', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [22]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '07:20', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [23]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '12:15', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [24]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '14:15', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 103, false, true);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
2, 100, false, true);

-- [25]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '15:10', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 103, false, true);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
2, 100, false, true);

-- [26]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '16:00', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [27]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '16:45', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 100, false, true);

-- [28]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '17:25', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 103, false, true);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
2, 100, false, true);

-- [29]
insert into bus_departures(route_number, bus_departure_location_id, bus_departure_time, 
                          is_weekday_service, is_saturday_service, is_sunday_service)
values('01', 101, '18:10', true, false, false);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
1, 103, false, true);

insert into bus_arrivals(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
                       is_via_location, is_turn_around_location)
values((select max(bus_departures_id) from bus_departures), 
2, 100, false, true);

commit;
