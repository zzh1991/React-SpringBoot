package app.service;

import app.dao.FilmListRepository;
import app.dao.FilmRepository;
import app.dao.TopFilmRepository;
import app.dao.ViewFilmRepository;
import app.entity.Film;
import app.entity.FilmList;
import app.entity.TopFilm;
import app.entity.ViewFilm;
import app.vo.movie.Avatar;
import app.vo.movie.Movie;
import app.vo.movie.MovieSubject;
import app.vo.movie.MovieVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieService {

    private static final String SEPARATOR = ",";
    public static final String LARGE = "large";

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TopFilmRepository topFilmRepository;

    @Autowired
    private ViewFilmRepository viewFilmRepository;

    @Autowired
    private FilmListRepository filmListRepository;

    public void syncRecentMovies() throws IOException {
        deleteOutDataMovie();
        saveMovie();
        saveDetailToMovie();
    }

    public void syncTopMovies() throws IOException {
        deleteOutDataTopMovie();
        saveTopMovie();
        saveDetailToTopMovie();
    }

    public List<Movie> getMovies(String url) throws IOException {
        MovieVo movieVo = null;
        String context = getUrlContent(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
                    .movieId(movie.getId())
                    .title(movie.getTitle())
                    .rating(movie.getRating().getAverage())
                    .url(movie.getAlt())
                    .movieYear(movie.getYear())
                    .imageLarge(movie.getImages().get(LARGE))
                    .casts(getNames(movie.getCasts()))
                    .directors(getNames(movie.getDirectors()))
                    .genres(StringUtils.join(movie.getGenres(), SEPARATOR))
                    .current(true)
                    .build());
            saveToMovieListDatabase(movie);
        });
    }

    private void saveToMovieListDatabase(Movie movie) {
        FilmList filmList = FilmList.builder()
                .movieId(movie.getId())
                .title(movie.getTitle())
                .rating(movie.getRating().getAverage())
                .url(movie.getAlt())
                .movieYear(movie.getYear())
                .imageLarge(movie.getImages().get(LARGE))
                .casts(getNames(movie.getCasts()))
                .directors(getNames(movie.getDirectors()))
                .genres(StringUtils.join(movie.getGenres(), SEPARATOR))
                .build();
        FilmList originFilm = filmListRepository.findFirstByMovieId(movie.getId());
        if (originFilm != null) {
            filmList.setId(originFilm.getId());
        }
        filmListRepository.save(filmList);
    }

    private void saveTopMovie() throws IOException {
        String url1 = "https://api.douban.com/v2/movie/top250?start=0&count=100";
        List<Movie> movieList = Lists.newArrayList();
        movieList.addAll(getMovies(url1));

        movieList.forEach(movie -> {
            topFilmRepository.save(TopFilm.builder()
                    .movieId(movie.getId())
                    .title(movie.getTitle())
                    .rating(movie.getRating().getAverage())
                    .url(movie.getAlt())
                    .movieYear(movie.getYear())
                    .imageLarge(movie.getImages().get(LARGE))
                    .casts(getNames(movie.getCasts()))
                    .directors(getNames(movie.getDirectors()))
                    .genres(StringUtils.join(movie.getGenres(), SEPARATOR))
                    .current(true)
                    .build());
            saveToMovieListDatabase(movie);
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
            MovieSubject movieSubject = getMovieSubject(film.getMovieId());
            if (movieSubject != null) {
                film.setSummary(movieSubject.getSummary());
                film.setCountries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR));
                filmRepository.save(film);
                saveDetailToMovieList(film.getMovieId(), movieSubject);
            }
        }
    }

    private void saveDetailToMovieList(Long id, MovieSubject movieSubject) throws IOException {
        FilmList filmList = filmListRepository.findFirstByMovieId(id);
        if (filmList != null) {
            filmList.setSummary(movieSubject.getSummary());
            filmList.setCountries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR));
            filmListRepository.save(filmList);
        }
    }

    private void saveDetailToTopMovie() throws IOException {
        List<TopFilm> topFilmList = topFilmRepository.findByCurrentIsTrueOrderByRatingDesc();
        for (TopFilm topFilm : topFilmList) {
            MovieSubject movieSubject = getMovieSubject(topFilm.getMovieId());
            if (movieSubject != null) {
                topFilm.setSummary(movieSubject.getSummary());
                topFilm.setCountries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR));
                topFilmRepository.save(topFilm);
                saveDetailToMovieList(topFilm.getMovieId(), movieSubject);
            }
        }
    }

    public void setViewedState(Long id, Boolean viewed) {
        ViewFilm viewFilm = viewFilmRepository.findOneByMovieId(id);
        if (viewFilm != null) {
            viewFilm.setViewed(viewed);
            viewFilmRepository.save(viewFilm);
        } else {
            viewFilmRepository.save(ViewFilm.builder()
                    .movieId(id)
                    .time(new Date().toString())
                    .viewed(viewed)
                    .build());
        }

        Film film = filmRepository.findOneByMovieId(id);
        if (film != null) {
            film.setViewed(viewed);
            filmRepository.save(film);
        }

        TopFilm topFilm = topFilmRepository.findOneByMovieId(id);
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
            movieIdMap.put(viewFilm.getMovieId(), viewFilm.isViewed());
        });


        filmList.forEach(film -> {
            if (movieIdMap.containsKey(film.getMovieId())) {
                film.setViewed(movieIdMap.get(film.getMovieId()));
                filmRepository.save(film);
            }
        });

        topFilmList.forEach(topFilm -> {
            if (movieIdMap.containsKey(topFilm.getMovieId())) {
                topFilm.setViewed(movieIdMap.get(topFilm.getMovieId()));
                topFilmRepository.save(topFilm);
            }
        });
    }

    public FilmList getFilmListById(Long id) {
        return filmListRepository.findFirstByMovieId(id);
    }

    public List<FilmList> getStarList(List<Long> movieIdList) {
        return getFilmLists(movieIdList);
    }

    private List<FilmList> getFilmLists(List<Long> movieIdList) {
        List<FilmList> filmLists = filmListRepository.findByMovieIdIsIn(movieIdList);
        if (filmLists.isEmpty()) {
            for (Long movieId : movieIdList) {
                this.syncOneMovieToMovieList(movieId);
            }
        } else {
            List<Long> existedIdList = filmLists.stream()
                    .filter(filmList -> !Objects.isNull(filmList.getSummary()))
                    .map(FilmList::getMovieId).collect(Collectors.toList());
            for (Long movieId : movieIdList) {
                if (!existedIdList.contains(movieId)) {
                    this.syncOneMovieToMovieList(movieId);
                }
            }
        }
        return filmListRepository.findByMovieIdIsIn(movieIdList);
    }

    public List<FilmList> getViewedList(List<Long> movieIdList) {
        return getFilmLists(movieIdList);
    }

    public FilmList syncOneMovieToMovieList(Long movieId) {
        FilmList filmList = filmListRepository.findFirstByMovieId(movieId);
        MovieSubject movieSubject;
        FilmList syncedMovie = FilmList.builder().build();
        try {
            movieSubject = getMovieSubject(movieId);
        } catch (IOException e) {
            log.error("can not fetch this movieId: {}", movieId);
            return syncedMovie;
        }

        if (!Objects.isNull(movieSubject)) {
            syncedMovie = FilmList.builder()
                    .movieId(movieId)
                    .title(movieSubject.getTitle())
                    .rating(movieSubject.getRating().getAverage())
                    .url(movieSubject.getAlt())
                    .movieYear(movieSubject.getYear())
                    .imageLarge(movieSubject.getImages().get(LARGE))
                    .casts(getNames(movieSubject.getCasts()))
                    .directors(getNames(movieSubject.getDirectors()))
                    .genres(StringUtils.join(movieSubject.getGenres(), SEPARATOR))
                    .summary(movieSubject.getSummary())
                    .countries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR))
                    .build();
        } else {
            return syncedMovie;
        }

        if (!Objects.isNull(filmList)) {
            syncedMovie.setId(filmList.getId());
        }

        return filmListRepository.save(syncedMovie);
    }
}