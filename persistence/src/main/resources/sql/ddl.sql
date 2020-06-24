create table system_country
(
    country_id varchar(2) not null
        constraint system_country_pk primary key,
    name       text       not null,
    constraint check_country_length check (length(country_id) = 2 and country_id ~ '[A-Z]+')
);

create table system_province
(
    province_id serial not null
        constraint system_province_pk
            primary key,
    country_id  varchar(2)
        constraint system_province_country
            references system_country
            on update restrict on delete cascade,
    name        text   not null
);

create table system_locality
(
    province_id integer
        constraint system_locality_province
            references system_province
            on update restrict on delete restrict,
    name        text   not null,
    locality_id serial not null
        constraint system_locality_pk
            primary key
);

create table picture
(
    picture_id serial not null
        constraint picture_pk primary key,
    name       text,
    mime_type  text   not null,
    size       bigint not null default 0,
    data       bytea  not null
);

create table users
(
    email      text   not null,
    password   text   not null,
    users_id   serial not null primary key,
    first_name text   not null,
    surname    text   not null,
    phone      text,
    verified bool default false,
    token text,
    token_created_date timestamp,
    profile_id int,
    constraint users_picture_picture_id_fk
        foreign key (profile_id) references picture
            on update cascade on delete set null
);

create table office
(
    office_id   serial  not null
        constraint office_pk
            primary key,
    name        text    not null,
    street      text,
    locality_id integer not null
        constraint office_province_id
            references system_locality
            on update restrict on delete restrict,
    phone       text,
    email       text,
    url text
);

create table staff
(
    staff_id            serial not null
        constraint staff_pk
            primary key,
    office_id           integer
        constraint staff_office
            references office
            on update restrict on delete cascade,
    phone               text,
    email               text,
    registration_number integer,
    user_id int,
    constraint staff_users_users_id_fk
        foreign key (user_id) references users
            on update restrict on delete set null
);

create table system_staff_specialty
(
    specialty_id serial not null
        constraint specialty_pk
            primary key,
    name         text   not null
);

create table system_staff_specialty_staff
(
    specialty_id integer not null
        constraint specialty_staff_system_specialty
            references system_staff_specialty
            on update restrict on delete restrict,
    staff_id     integer not null
        constraint specialty_staff_staff
            references staff
            on update restrict on delete cascade,
    constraint system_staff_specialty_staff_pk
        primary key (specialty_id, staff_id)
);

create table patient
(
    user_id    int
        constraint patient_users_users_id_fk
            references users
            on update restrict on delete set null,
    office_id  int    not null
        constraint patient_office_office_id_fk
            references office
            on update restrict on delete cascade,
    patient_id serial not null primary key
);

create table appointment
(
    appointment_id serial  not null
        constraint appointment_pk
            primary key,
    status         text    not null,
    patient_id     integer not null
        constraint appointment_patient_patient_id_fk
            references patient
            on update restrict on delete restrict,
    staff_id       integer not null,
    from_date      timestamp    not null,
    motive text,
    message text,
    constraint appointment_staff_staff_id_fk
        foreign key (staff_id) references staff
            on update set null on delete set null
);

create table workday
(
    workday_id   serial not null primary key,
    staff_id     int    not null
        constraint workday_staff_staff_id_fk
            references staff
            on update restrict on delete cascade,
    start_hour   int    not null,
    end_hour     int    not null,
    start_minute int    not null default 0,
    end_minute   int    not null default 0,
    day          text   not null
);

create unique index system_country_country_id_uindex
    on system_country (country_id);

create unique index system_province_province_id_uindex
    on system_province (province_id);

create unique index specialty_specialty_id_uindex
    on system_staff_specialty (specialty_id);

create unique index office_office_id_uindex
    on office (office_id);

create unique index staff_staff_id_uindex
    on staff (staff_id);

create unique index system_locality_locality_id_uindex
    on system_locality (locality_id);

create unique index picture_picture_id_uindex
    on picture (picture_id);

create unique index user_email_uindex
    on users (email);

create unique index user_users_id_uindex
    on users (users_id);

create unique index patient_patient_id_uindex
    on patient (patient_id);

create index staff_user_id_index
    on staff (user_id);

create unique index appointment_appointment_id_uindex
    on appointment (appointment_id);

create index appointment_from_date_to_date_index
    on appointment (from_date);

create index appointment_status_status_index
    on appointment (status, status);

create index workday_day_index
    on workday (day);

create unique index workday_workday_id_uindex
    on workday (workday_id);

create unique index users_token_uindex
    on users (token);

create function unaccent(text) returns text
    immutable
    strict
    language sql
as
$$
SELECT translate(
               $1,
               'âãäåāăąÁÂÃÄÅĀĂĄèééêëēĕėęěĒĔĖĘĚìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮ',
               'aaaaaaaaaaaaaaaeeeeeeeeeeeeeeeiiiiiiiiiiiiiiiiooooooooooooooouuuuuuuuuuuuuuuu'
           );
$$;
