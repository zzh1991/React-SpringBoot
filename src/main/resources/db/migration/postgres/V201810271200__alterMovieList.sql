-- ALTER TABLE movie_list ADD COLUMN IF NOT EXISTS update_time TIMESTAMP DEFAULT now();
-- ALTER TABLE movie_list ADD COLUMN IF NOT EXISTS movie_type TEXT DEFAULT 'NORMAL';

delete from public.movie_list where id not in (select max(id) from movie_list group by title);