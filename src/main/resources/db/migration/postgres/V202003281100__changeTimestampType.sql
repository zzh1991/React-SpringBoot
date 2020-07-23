alter table movie_list alter column update_time type timestamptz using update_time::timestamptz;
