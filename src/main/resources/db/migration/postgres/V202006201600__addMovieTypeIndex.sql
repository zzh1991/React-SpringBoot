create index movie_type__index
    on movie_list (movie_type desc);

create unique index movie_id_index
    on movie_list (movie_id);