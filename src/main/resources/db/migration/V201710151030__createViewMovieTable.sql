
CREATE TABLE public.view_movie
(
  id BIGINT PRIMARY KEY,
  movie_id BIGINT,
  time TEXT,
  viewed BOOLEAN DEFAULT FALSE
);

CREATE SEQUENCE public.view_movie_id_seq START 1 NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.view_movie ALTER COLUMN id SET DEFAULT nextval('public.view_movie_id_seq');
ALTER SEQUENCE public.view_movie_id_seq OWNED BY public.view_movie.id;

CREATE TABLE IF NOT EXISTS public.movie_detail (
  id BIGINT PRIMARY KEY,
  movie_id BIGINT,
  summary TEXT,
  countries TEXT
);

CREATE SEQUENCE public.movie_detail_id_seq START 1 NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.movie_detail ALTER COLUMN id SET DEFAULT nextval('public.movie_detail_id_seq');
ALTER SEQUENCE public.movie_detail_id_seq OWNED BY public.movie_detail.id;