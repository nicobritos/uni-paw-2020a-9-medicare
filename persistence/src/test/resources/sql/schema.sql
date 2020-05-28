create table if not exists system_country
(
    country_id varchar(2)   not null
        constraint system_country_pk
            primary key,
    name       varchar(255) not null
);

create sequence if not exists system_province_province_id_seq minvalue 1 start with 1;
create table if not exists system_province
(
    province_id int generated by default as sequence system_province_province_id_seq not null primary key,
    country_id varchar(2)
        constraint system_province_country
            references system_country,
    name       varchar(255) not null
);

create sequence if not exists system_staff_specialty_specialty_id_seq minvalue 1 start with 1;
create table if not exists system_staff_specialty
(
    specialty_id int generated by default as sequence system_staff_specialty_specialty_id_seq not null primary key,
    name varchar(255)     not null
);

create sequence if not exists system_locality_locality_id_seq minvalue 1 start with 1;
create table if not exists system_locality
(
    province_id integer
        constraint system_locality_province
            references system_province,
    name        varchar(255) not null,
    locality_id int generated by default as sequence system_locality_locality_id_seq not null primary key
);

create sequence if not exists office_office_id_seq minvalue 1 start with 1;
create table if not exists office
(
    office_id int generated by default as sequence office_office_id_seq not null primary key,
    name        varchar(255) not null,
    street      varchar(255),
    locality_id integer
        constraint office_province_id
            references system_locality,
    phone       varchar(255),
    email       varchar(255),
    url         varchar(255)
);

create sequence if not exists picture_picture_id_seq minvalue 1 start with 1;
create table if not exists picture
(
    picture_id int generated by default as sequence picture_picture_id_seq not null primary key,
    name      varchar(1023),
    mime_type varchar(255)     not null,
    size      bigint           not null default 0,
    data      varbinary(65535) not null
);

create sequence if not exists users_users_id_seq minvalue 1 start with 1;
create table if not exists users
(
    email              varchar(255) not null,
    password           varchar(255) not null,
    users_id int generated by default as sequence users_users_id_seq not null primary key,
    first_name         varchar(255) not null,
    surname            varchar(255) not null,
    phone              varchar(255),
    verified           boolean default false,
    token              varchar(1023),
    token_created_date timestamp,
    profile_id         int
        constraint users_picture_picture_id_fk
            foreign key references picture
                on delete set null
);

create sequence if not exists staff_staff_id_seq minvalue 1 start with 1;
create table if not exists staff
(
    staff_id int generated by default as sequence staff_staff_id_seq not null primary key,
    office_id           integer
        constraint staff_office
            references office,
    first_name          varchar(255) not null,
    surname             varchar(255) not null,
    phone               varchar(255),
    email               varchar(255),
    registration_number integer,
    user_id             integer
        constraint staff_users_users_id_fk
            references users
);

create sequence if not exists picture_picture_id_seq minvalue 1 start with 1;
create table if not exists system_staff_specialty_staff
(
    specialty_id integer not null
        constraint specialty_staff_system_specialty
            references system_staff_specialty,
    staff_id     integer not null
        constraint specialty_staff_staff
            references staff,
    constraint system_staff_specialty_staff_pk
        primary key (specialty_id, staff_id)
);

create sequence if not exists patient_patient_id_seq minvalue 1 start with 1;
create table if not exists patient
(
    user_id   integer
        constraint patient_user_user_id_fk
            references users,
    office_id integer   not null
        constraint patient_office_office_id_fk
            references office,
    patient_id int generated by default as sequence patient_patient_id_seq not null primary key
);

create sequence if not exists appointment_appointment_id_seq minvalue 1 start with 1;
create table if not exists appointment
(
    appointment_id int generated by default as sequence appointment_appointment_id_seq not null primary key,
    patient_id integer      not null
        constraint appointment_patient_patient_id_fk
            references patient,
    staff_id   integer      not null
        constraint appointment_staff_staff_id_fk
            foreign key references staff
                on delete cascade,
    from_date  timestamp    not null,
    status     varchar(20),
    motive     varchar(65535),
    message    varchar(65535)
);

create sequence if not exists workday_workday_id_seq minvalue 1 start with 1;
create table if not exists workday
(
    workday_id int generated by default as sequence workday_workday_id_seq not null primary key,
    staff_id     int          not null
        constraint workday_staff_staff_id_fk
            references staff
            on delete cascade,
    start_hour   int          not null,
    end_hour     int          not null,
    start_minute int          not null default 0,
    end_minute   int          not null default 0,
    day          varchar(255) not null
);

drop function unaccent if exists;
create function unaccent(t varchar(255))
    returns varchar(255)
    return translate(t,
                     'âãäåāăąÁÂÃÄÅĀĂĄèééêëēĕėęěĒĔĖĘĚìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮ',
                     'aaaaaaaaaaaaaaaeeeeeeeeeeeeeeeiiiiiiiiiiiiiiiiooooooooooooooouuuuuuuuuuuuuuuu');
