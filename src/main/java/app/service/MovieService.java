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
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhihao zhang
 * @date 2017.10.18
 */

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieService {

    private static final String SEPARATOR = ",";
    private static final String LARGE = "large";

    private FilmRepository filmRepository;
    private TopFilmRepository topFilmRepository;
    private ViewFilmRepository viewFilmRepository;
    private FilmListRepository filmListRepository;

    public void syncRecentMovies() throws IOException {
        List<Film> newFilmList = Lists.newArrayList();
        deleteOutDataMovie();
        saveMovie();
        saveDetailToMovie(newFilmList);
        filmRepository.saveAll(newFilmList);
        log.info("update movie summary and country");
    }

    public void syncTopMovies() throws IOException {
        List<TopFilm> newTopFilmList = Lists.newArrayList();
        deleteOutDataTopMovie();
        saveTopMovie();
        saveDetailToTopMovie(newTopFilmList);
        topFilmRepository.saveAll(newTopFilmList);
        log.info("update top movie summary and country");
    }

    private List<Movie> getMovies(String url) throws IOException {
        String context = getUrlContent(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MovieVo movieVo = mapper.readValue(context,
                TypeFactory.defaultInstance().constructType(MovieVo.class));
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
        List<Film> filmList = Lists.newArrayList();
        List<FilmList> filmItemList = Lists.newArrayList();

        this.getRecentFilmList(movieList, filmList, filmItemList);
        filmRepository.saveAll(filmList);
        log.info("save movies");
        this.saveToMovieListDatabase(filmItemList);
        log.info("save movie list");
    }

    private void getRecentFilmList(List<Movie> movieList, List<Film> filmList, List<FilmList> filmItemList) {
        for (Movie movie : movieList) {
            FilmList filmItem = FilmList.builder().build();
            Film film = filmRepository.findFirstByMovieIdOrderByIdDesc(movie.getId());
            Film newFilm = Film.builder()
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
                    .build();
            BeanUtils.copyProperties(newFilm, filmItem);
            if (Objects.nonNull(film)) {
                newFilm.setCountries(film.getCountries());
                newFilm.setSummary(film.getSummary());
                BeanUtils.copyProperties(newFilm, filmItem);
                newFilm.setId(film.getId());
            }
            filmList.add(newFilm);
            filmItemList.add(filmItem);
        }
    }

    private void saveToMovieListDatabase(List<FilmList> filmItemList) {
        for (FilmList filmItem : filmItemList) {
            FilmList originFilm = filmListRepository.findFirstByMovieId(filmItem.getMovieId());
            if (originFilm != null) {
                filmItem.setId(originFilm.getId());
            }
        }
        filmListRepository.saveAll(filmItemList);
    }

    private void saveTopMovie() throws IOException {
        String url1 = "https://api.douban.com/v2/movie/top250?start=0&count=100";
        List<Movie> movieList = Lists.newArrayList();
        movieList.addAll(getMovies(url1));

        List<TopFilm> topFilmList = Lists.newArrayList();
        List<FilmList> filmItemList = Lists.newArrayList();
        this.getTopFilmList(movieList, topFilmList, filmItemList);

        topFilmRepository.saveAll(topFilmList);
        log.info("save movies");
        this.saveToMovieListDatabase(filmItemList);
        log.info("save movie list");
    }

    private void getTopFilmList(List<Movie> movieList, List<TopFilm> filmList, List<FilmList> filmItemList) {
        for (Movie movie : movieList) {
            FilmList filmItem = FilmList.builder().build();
            TopFilm film = topFilmRepository.findFirstByMovieIdOrderByIdDesc(movie.getId());
            TopFilm newFilm = TopFilm.builder()
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
                    .build();
            BeanUtils.copyProperties(newFilm, filmItem);
            if (Objects.nonNull(film)) {
                newFilm.setCountries(film.getCountries());
                newFilm.setSummary(film.getSummary());
                BeanUtils.copyProperties(newFilm, filmItem);
                newFilm.setId(film.getId());
            }
            filmList.add(newFilm);
            filmItemList.add(filmItem);
        }
    }

    public List<Film> getRecentFilmList() {
        return filmRepository.findByCurrentIsTrueOrderByRatingDesc();
    }

    public List<TopFilm> getTopFilmList() {
        return topFilmRepository.findByCurrentIsTrueOrderByRatingDesc();
    }

    private void deleteOutDataMovie() {
        List<Film> filmList = filmRepository.findByCurrentIsTrueOrderByRatingDesc();
        filmList.forEach(film -> film.setCurrent(false));
        filmRepository.saveAll(filmList);
        log.info("delete old movies");
    }

    private void deleteOutDataTopMovie() {
        List<TopFilm> topFilmList = topFilmRepository.findByCurrentIsTrueOrderByRatingDesc();
        topFilmList.forEach(film -> film.setCurrent(false));
        topFilmRepository.saveAll(topFilmList);
        log.info("delete old top movies");
    }

    private String getNames(List<Avatar> avatars) {
        List<String> nameList = Lists.newArrayList();
        avatars.forEach(avatar ->
            nameList.add(avatar.getName())
        );
        return StringUtils.join(nameList.toArray(), SEPARATOR);
    }

    public MovieSubject getMovieSubject(Long id) throws IOException {
        String url = "https://api.douban.com/v2/movie/subject/" + id;
        String context = getUrlContent(url);
        ObjectMapper mapper = new ObjectMapper();
        MovieSubject movieSubject;
        try {
            movieSubject = mapper.readValue(context, TypeFactory.defaultInstance().constructType(MovieSubject.class));
        } catch (Exception e) {
            return null;
        }
        return movieSubject;
    }

    private void saveDetailToMovie(List<Film> newFilmList) throws IOException {
        List<Film> filmList = filmRepository.findByCurrentIsTrueOrderByRatingDesc();
        for (Film film : filmList) {
            if (Strings.isNullOrEmpty(film.getSummary())) {
                MovieSubject movieSubject = getMovieSubject(film.getMovieId());
                if (movieSubject != null) {
                    film.setSummary(movieSubject.getSummary());
                    film.setCountries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR));
                    newFilmList.add(film);
                    this.saveDetailToMovieList(film.getMovieId(), movieSubject);
                }
            }
        }
    }

    private void saveDetailToMovieList(Long id, MovieSubject movieSubject) {
        FilmList filmList = filmListRepository.findFirstByMovieId(id);
        if (Objects.nonNull(filmList) && Strings.isNullOrEmpty(filmList.getSummary())) {
            filmList.setSummary(movieSubject.getSummary());
            filmList.setCountries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR));
            filmListRepository.save(filmList);
            log.info("update movie item summary and country");
        }
    }

    private void saveDetailToTopMovie(List<TopFilm> newTopFilmList) throws IOException {
        List<TopFilm> topFilmList = topFilmRepository.findByCurrentIsTrueOrderByRatingDesc();
        for (TopFilm topFilm : topFilmList) {
            if (Strings.isNullOrEmpty(topFilm.getSummary())) {
                MovieSubject movieSubject = getMovieSubject(topFilm.getMovieId());
                if (movieSubject != null) {
                    topFilm.setSummary(movieSubject.getSummary());
                    topFilm.setCountries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR));
                    newTopFilmList.add(topFilm);
                    this.saveDetailToMovieList(topFilm.getMovieId(), movieSubject);
                }
            }
        }
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

    public void setViewedState(Long id, Boolean viewed) {
        ViewFilm viewFilm = viewFilmRepository.findFirstByMovieId(id);
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

        Film film = filmRepository.findFirstByMovieIdOrderByIdDesc(id);
        if (film != null) {
            film.setViewed(viewed);
            filmRepository.save(film);
        }

        TopFilm topFilm = topFilmRepository.findFirstByMovieIdOrderByIdDesc(id);
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
        viewFilmList.forEach(viewFilm ->
                movieIdMap.put(viewFilm.getMovieId(), viewFilm.isViewed())
        );


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

    public List<FilmList> getAllMoviesList() {
        return filmListRepository.findAllByOrderByMovieYearDescRatingDesc();
    }
}