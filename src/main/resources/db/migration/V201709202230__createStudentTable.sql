CREATE SEQUENCE IF NOT EXISTS public.student_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.student (
  id BIGINT PRIMARY KEY DEFAULT nextval('public.student_id_seq'),
  name TEXT
);
