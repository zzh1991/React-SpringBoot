CREATE TABLE IF NOT EXISTS public.movie_list (
  id BIGSERIAL PRIMARY KEY,
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
  star BOOLEAN DEFAULT FALSE,
  update_time TIMESTAMP DEFAULT now(),
  movie_type TEXT DEFAULT 'NORMAL'
);
