create table movie
(
    id   int auto_increment  PRIMARY KEY,
    name varchar(50) not null
);

create unique index movie_id_uindex
    on movie (id);

Insert into movie(id, name) VALUES (1, 'Matrix');
Insert into movie(id, name) VALUES (2, 'Terminator');
Insert into movie(id, name) VALUES (3, 'RoboCop');
Insert into movie(id, name) VALUES (4, 'Alien II');
Insert into movie(id, name) VALUES (5, 'RoboCop2');
Insert into movie(id, name) VALUES (6, 'Matrix 2');
Insert into movie(id, name) VALUES (7, 'Batman');
