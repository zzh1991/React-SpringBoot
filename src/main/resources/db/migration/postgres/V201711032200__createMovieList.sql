create table movie_list
(
    id          bigserial not null
                constraint movie_list_id_pk
                primary key,
    movie_id    bigint,
    title       text,
    rating      double precision,
    movie_year  integer,
    image_large text,
    url         text,
    casts       text,
    directors   text,
    genres      text,
    summary     text,
    countries   text,
    viewed      boolean                  default false,
    star        boolean                  default false,
    update_time timestamp with time zone default now(),
    movie_type  text                     default 'NORMAL'::text,
    object_id   text                     default ''::text
);

create index movie_type__index
    on movie_list (movie_type);

create index movie_rating__index
    on movie_list (rating desc);

create unique index movie_id_index
    on movie_list (movie_id);


