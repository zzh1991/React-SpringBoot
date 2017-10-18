package app.service;

import app.dao.FilmRepository;
import app.dao.TopFilmRepository;
import app.dao.ViewFilmRepository;
import app.entity.Film;
import app.entity.TopFilm;
import app.entity.ViewFilm;
import app.vo.movie.Avatar;
import app.vo.movie.Movie;
import app.vo.movie.MovieSubject;
import app.vo.movie.MovieVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {

    private static final String SEPARATOR = ",";

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TopFilmRepository topFilmRepository;

    @Autowired
    private ViewFilmRepository viewFilmRepository;

    public void sync() throws IOException {
        deleteOutDataMovie();
        saveMovie();
        deleteOutDataTopMovie();
        saveTopMovie();
        saveDetailToMovie();
        saveDetailToTopMovie();
        syncViewData();
    }

    public List<Movie> getMovies(String url) throws IOException {
        MovieVo movieVo = null;
        String context = getUrlContent(url);
        ObjectMapper mapper = new ObjectMapper();
        movieVo = mapper.readValue(context, TypeFactory.defaultInstance().constructType(MovieVo.class));
        return movieVo.getSubjects();
    }

    private String getUrlContent(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void saveMovie() throws IOException {
        String url = "https://api.douban.com/v2/movie/in_theaters?city=上海";
        List<Movie> movieList = getMovies(url);
        movieList.forEach(movie -> {
            filmRepository.save(Film.builder()
                    .movieid(movie.getId())
                    .title(movie.getTitle())
                    .rating(movie.getRating().getAverage())
                    .url(movie.getAlt())
                    .movie_year(movie.getYear())
                    .image_large(movie.getImages().get("large"))
                    .casts(getNames(movie.getCasts()))
                    .directors(getNames(movie.getDirectors()))
                    .genres(StringUtils.join(movie.getGenres(), SEPARATOR))
                    .current(true)
                    .build());
        });
    }

    private void saveTopMovie() throws IOException {
        String url1 = "https://api.douban.com/v2/movie/top250?start=0&count=100";
        List<Movie> movieList = Lists.newArrayList();
        movieList.addAll(getMovies(url1));

        movieList.forEach(movie -> {
            topFilmRepository.save(TopFilm.builder()
                    .movieid(movie.getId())
                    .title(movie.getTitle())
                    .rating(movie.getRating().getAverage())
                    .url(movie.getAlt())
                    .movie_year(movie.getYear())
                    .image_large(movie.getImages().get("large"))
                    .casts(getNames(movie.getCasts()))
                    .directors(getNames(movie.getDirectors()))
                    .genres(StringUtils.join(movie.getGenres(), SEPARATOR))
                    .current(true)
                    .build());
        });
    }

    public List<Film> getFilmList() {
        return filmRepository.findByCurrentIsTrueOrderByRatingDesc();
    }

    public List<TopFilm> getTopFilmList() {
        return topFilmRepository.findByCurrentIsTrueOrderByRatingDesc();
    }

    private void deleteOutDataMovie() {
        List<Film> filmList = filmRepository.findByCurrentIsTrueOrderByRatingDesc();
        filmList.forEach(film -> {
            film.setCurrent(false);
            filmRepository.save(film);
        });
    }

    private void deleteOutDataTopMovie() {
        List<TopFilm> topFilmList = topFilmRepository.findByCurrentIsTrueOrderByRatingDesc();
        topFilmList.forEach(film -> {
            film.setCurrent(false);
            topFilmRepository.save(film);
        });
    }

    private String getNames(List<Avatar> avatars) {
        List<String> nameList = Lists.newArrayList();
        avatars.forEach(avatar -> {
            nameList.add(avatar.getName());
        });
        return StringUtils.join(nameList.toArray(), SEPARATOR);
    }

    public MovieSubject getMovieSubject(Long id) throws IOException {
        String url = "https://api.douban.com/v2/movie/subject/" + id;
        String context = getUrlContent(url);
        ObjectMapper mapper = new ObjectMapper();
        MovieSubject movieSubject = null;
        try {
            movieSubject = mapper.readValue(context, TypeFactory.defaultInstance().constructType(MovieSubject.class));
        } catch (Exception e) {
            return null;
        }
        return movieSubject;
    }

    private void saveDetailToMovie() throws IOException {
        List<Film> filmList = filmRepository.findByCurrentIsTrueOrderByRatingDesc();
        for (Film film : filmList) {
            MovieSubject movieSubject = getMovieSubject(film.getMovieid());
            if (movieSubject != null) {
                film.setSummary(movieSubject.getSummary());
                film.setCountries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR));
                filmRepository.save(film);
            }
        }
    }

    private void saveDetailToTopMovie() throws IOException {
        List<TopFilm> topFilmList = topFilmRepository.findByCurrentIsTrueOrderByRatingDesc();
        for (TopFilm topFilm : topFilmList) {
            MovieSubject movieSubject = getMovieSubject(topFilm.getMovieid());
            if (movieSubject != null) {
                topFilm.setSummary(movieSubject.getSummary());
                topFilm.setCountries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR));
                topFilmRepository.save(topFilm);
            }
        }
    }

    public void setViewedState(Long id, Boolean viewed) {
        ViewFilm viewFilm = viewFilmRepository.findOneByMovieid(id);
        if (viewFilm != null) {
            viewFilm.setViewed(viewed);
            viewFilmRepository.save(viewFilm);
        } else {
            viewFilmRepository.save(ViewFilm.builder()
                    .movieid(id)
                    .time(new Date().toString())
                    .viewed(viewed)
                    .build());
        }

        Film film = filmRepository.findOneByMovieid(id);
        if (film != null) {
            film.setViewed(viewed);
            filmRepository.save(film);
        }

        TopFilm topFilm = topFilmRepository.findOneByMovieid(id);
        if (topFilm != null) {
            topFilm.setViewed(viewed);
            topFilmRepository.save(topFilm);
        }
    }

    private void syncViewData() {
        List<Film> filmList = filmRepository.findByCurrentIsTrueOrderByRatingDesc();
        List<TopFilm> topFilmList = topFilmRepository.findByCurrentIsTrueOrderByRatingDesc();
        List<ViewFilm> viewFilmList = viewFilmRepository.findAll();
        Map<Long, Boolean> movieIdMap = Maps.newHashMap();
        viewFilmList.forEach(viewFilm -> {
            movieIdMap.put(viewFilm.getMovieid(), viewFilm.isViewed());
        });


        filmList.forEach(film -> {
            if (movieIdMap.containsKey(film.getMovieid())) {
                film.setViewed(movieIdMap.get(film.getMovieid()));
                filmRepository.save(film);
            }
        });

        topFilmList.forEach(topFilm -> {
            if (movieIdMap.containsKey(topFilm.getMovieid())) {
                topFilm.setViewed(movieIdMap.get(topFilm.getMovieid()));
                topFilmRepository.save(topFilm);
            }
        });
    }
}