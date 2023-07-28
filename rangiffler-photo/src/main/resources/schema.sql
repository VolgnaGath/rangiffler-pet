-- create database "rangiffler-photo" with owner postgres;

create extension if not exists "uuid-ossp";

create table if not exists photo
(
    id                      UUID unique        not null default uuid_generate_v1(),
    username                varchar(50)        not null,
    country_code            varchar(50)        not null,
    description             varchar(255),
    image                   text               not null,
    primary key (id)
);

alter table photo
    owner to postgres;
