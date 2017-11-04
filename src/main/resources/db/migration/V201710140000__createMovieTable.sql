CREATE TABLE IF NOT EXISTS public.movie (
  id BIGINT PRIMARY KEY,
  movie_id BIGINT,
  title TEXT,
  rating FLOAT,
  movie_year INT,
  image_large TEXT,
  url TEXT,
  casts TEXT,
  directors TEXT,
  genres TEXT,
  current BOOLEAN,
  summary TEXT,
  countries TEXT,
  viewed BOOLEAN DEFAULT FALSE
);

CREATE SEQUENCE public.movie_id_seq START 1 NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.movie ALTER COLUMN id SET DEFAULT nextval('public.movie_id_seq');
ALTER SEQUENCE public.movie_id_seq OWNED BY public.movie.id;

CREATE TABLE IF NOT EXISTS public.movie_top (
  id BIGINT PRIMARY KEY,
  movie_id BIGINT,
  title TEXT,
  rating FLOAT,
  movie_year INT,
  image_large TEXT,
  url TEXT,
  casts TEXT,
  directors TEXT,
  genres TEXT,
  current BOOLEAN,
  summary TEXT,
  countries TEXT,
  viewed BOOLEAN DEFAULT FALSE
);

CREATE SEQUENCE public.movie_top_id_seq START 1 NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.movie_top ALTER COLUMN id SET DEFAULT nextval('public.movie_top_id_seq');
ALTER SEQUENCE public.movie_top_id_seq OWNED BY public.movie_top.id;