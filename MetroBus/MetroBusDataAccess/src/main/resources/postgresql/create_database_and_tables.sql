begin;

-- drop the public schema and re-create
drop schema public cascade;
create schema public;
alter schema public owner to postgres;

-- create a table to store bus zones
create table bus_zone (
        zone_number int not null unique,
        primary key (zone_number),
        zone_desc text not null unique
);

-- create a bus route table
create table bus_route (
        route_number varchar(12) not null unique,
        primary key (route_number),
        route_desc text not null unique,
        weekday_service boolean not null default true,
        saturday_service boolean not null default false,
        sunday_service boolean not null default false
);

-- create table to store locations
create table locations (

        location_id int not null unique,
        primary key (location_id),
        location_short_name text not null unique,
        location_full_name text not null unique,
        gps_latitude numeric not null,
        gps_longitude numeric not null
);

-- create a sequence for the bus departure entries
create sequence bus_departures_id_sequence
start 1 minvalue 1 no maxvalue increment by 1 cache 1;

-- create a bus departure times table
create table bus_departures (
        bus_departures_id int not null unique default nextval('bus_departures_id_sequence'),
        primary key (bus_departures_id),
        route_number varchar(12) not null,
        foreign key (route_number) references bus_route,
        bus_departure_location_id int not null,
        foreign key (bus_departure_location_id) references locations(location_id),
        bus_departure_time time not null,
        is_weekday_service boolean not null default true,
        is_saturday_service boolean not null default false,
        is_sunday_service boolean not null default false
);

alter table bus_departures add constraint bus_departures_entry_unique_key 
unique(route_number, bus_departure_location_id, bus_departure_time, 
is_weekday_service, is_saturday_service, is_sunday_service);

-- create the sequence for bus arrivals id
create sequence bus_arrivals_id_sequence
start 1 minvalue 1 no maxvalue increment by 1 cache 1;

-- create table for bus arrivals and vias
create table bus_arrivals (
        bus_arrivals_id int not null unique default nextval('bus_arrivals_id_sequence'),
        primary key (bus_arrivals_id),
        bus_departures_id int not null,
        foreign key (bus_departures_id) references bus_departures,
        bus_arrival_sequence int not null default 1,
        bus_arrival_location_id int not null,
        foreign key (bus_arrival_location_id) references locations(location_id),
        is_via_location boolean not null default false,
        is_turn_around_location boolean not null default true
);

alter table bus_arrivals add constraint bus_arrivals_unique_entry_key 
unique(bus_departures_id, bus_arrival_sequence, bus_arrival_location_id, 
is_via_location, is_turn_around_location);

-- create route description sequence
create sequence route_desc_id_sequence
start 1 minvalue 1 no maxvalue increment by 1 cache 1;

-- create the table for holding route description
create table bus_route_desc (
    
    bus_route_desc_id int not null unique default nextval('route_desc_id_sequence'),
    primary key (bus_route_desc_id),
    bus_route_desc_text text not null unique,
    route_number varchar(12) not null,
    foreign key (route_number) references bus_route,
    start_location_id int not null,
    foreign key (start_location_id) references locations(location_id),
    end_location_id int not null,
    foreign key (end_location_id) references locations(location_id)

);

alter table bus_route_desc add constraint 
route_number_locations_ukey unique(route_number, start_location_id, end_location_id);

-- create sequence for the bus stops
create sequence route_bus_stop_id_sequence
start 1 minvalue 1 no maxvalue increment by 1 cache 1;

-- create table for the bus stops
create table route_bus_stop (

    route_bus_stop_id int not null unique default nextval('route_bus_stop_id_sequence'),
    primary key (route_bus_stop_id),
    route_number varchar(12) not null,
    foreign key (route_number) references bus_route,
    zone_number int not null,
    foreign key (zone_number) references bus_zone,
    bus_stop_short_desc text not null,
    bus_stop_full_desc text not null,
    gps_latitude_coordinate numeric not null,
    gps_longitude_coordinate numeric not null

);

-- create the navigation bar links table
create table navigation_bar_link (
        navigation_bar_link_id int not null unique,
        navigation_bar_link_text varchar(128) not null unique,
        navigation_bar_link_desc text,
        navigation_bar_link_href varchar(256) not null,
        navigation_bar_link_glyphicon_css varchar(128) not null,
        navigation_bar_link_active boolean not null default true
);


commit;

