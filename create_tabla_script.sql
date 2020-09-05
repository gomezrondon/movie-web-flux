create table movie
(
    id   int auto_increment  PRIMARY KEY,
    name varchar(50) not null
);

create unique index movie_id_uindex
    on movie (id);

Insert into movie(id, name) VALUES (1, 'Batman');
Insert into movie(id, name) VALUES (2, 'Matrix');
Insert into movie(id, name) VALUES (3, 'Terminator');
Insert into movie(id, name) VALUES (4, 'RoboCop');
Insert into movie(id, name) VALUES (5, 'Alien II');
Insert into movie(id, name) VALUES (6, 'RoboCop2');
Insert into movie(id, name) VALUES (7, 'Matrix 2');