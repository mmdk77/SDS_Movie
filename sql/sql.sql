
create table movie(
movie_id number primary key,
title varchar2(50),
genre_id number,
runtime number,
openday date
);

create table genre(
genre_id number primary key,
title varchar2(20)
);

create table score(
score_id number primary key,
jumsu number default 0,
regdate date default sysdate,
movie_id number
);

create table director(
director_id number primary key,
name varchar2(50)
);

create table casting(
casting_id number primary key,
actor varchar2(50),
movie_id number
);


create sequence seq_movie
increment by 1 start with 1;

create sequence seq_genre
increment by 1 start with 1;

create sequence seq_score
increment by 1 start with 1;

create sequence seq_director
increment by 1 start with 1;

create sequence seq_casting
increment by 1 start with 1;