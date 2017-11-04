CREATE TABLE IF NOT EXISTS public.movie_list (
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
  summary TEXT,
  countries TEXT,
  viewed BOOLEAN DEFAULT FALSE,
  star BOOLEAN DEFAULT FALSE
);

CREATE SEQUENCE public.movie_list_id_seq START 1 NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.movie_list ALTER COLUMN id SET DEFAULT nextval('public.movie_list_id_seq');
ALTER SEQUENCE public.movie_list_id_seq OWNED BY public.movie_list.id;