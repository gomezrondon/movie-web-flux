create table movie
(
    id   int auto_increment  PRIMARY KEY,
    name varchar(50) not null
);

create unique index movie_id_uindex
    on movie (id);