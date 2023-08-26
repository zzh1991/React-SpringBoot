package com.zzhpro.movie.util;

import com.google.common.collect.Lists;
import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.entity.Film;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhihao zhang
 * @since 7/6/20
 */

@Slf4j
@UtilityClass
public final class JsoupUtils {
    private final String NOWPLAYING = "nowplaying";
    private final String RECENT_URL = "https://movie.douban.com/cinema/nowplaying/hangzhou/";
    private final String DOUBAN_URL = "https://movie.douban.com/subject/";
    private final String TOP_DOUBAN_URL = "https://movie.douban.com/top250?start=";
    private final String PROPERTY = "property";

    public List<Film> getFilmListFromDouban() {
        List<Film> filmList = Lists.newArrayList();
        LocalDateTime updateTime = LocalDateTime.now();

        try {
            Document document = Jsoup.parse(new URL(RECENT_URL), 30000);
            Element element = document.getElementById(NOWPLAYING);

            if (Objects.isNull(element)) {
                return filmList;
            }

            Elements elements = element.getElementsByClass("list-item");
            elements.forEach(item -> filmList.add(Film.builder()
                    .movieId(Long.valueOf(item.attr("id")))
                    .title(item.attr("data-title"))
                    .rating(Double.parseDouble(item.attr("data-score")))
                    .movieYear(Integer.parseInt(item.attr("data-release")))
                    .countries(getJoinString(item.attr("data-region"), " "))
                    .directors(getJoinString(item.attr("data-director"), " "))
                    .casts(getJoinString(item.attr("data-actors"), "/"))
                    .url(DOUBAN_URL.concat(item.attr("id")))
                    .imageLarge(getImageUrl(item))
                    .updateTime(updateTime)
                    .movieTypeEnum(MovieTypeEnum.RECENT)
                    .build()));
        } catch (Exception e) {
            log.error("get recent movies error: ", e);
        }
        return filmList;
    }

    public List<Film> getTopFilmListFromDouban() {
        int gap = 25;
        int n = 10;
        LocalDateTime updateTime = LocalDateTime.now();
        List<Film> filmList = Lists.newArrayList();
        for (int i = 0; i < n; i++) {
            try {
                Document document = Jsoup.parse(new URL(TOP_DOUBAN_URL.concat(String.valueOf(i * gap))), 30000);
                Element gridView = document.getElementById("content");
                if (Objects.isNull(gridView)) {
                    return filmList;
                }
                Elements liElements = gridView.getElementsByTag("li");
                filmList.addAll(liElements.stream()
                        .map(item -> {
                            Element aElement = item.getElementsByTag("a").get(0);
                            Element imgElement = aElement.getElementsByTag("img").get(0);
                            String doubanUrl = aElement.attr("href");
                            doubanUrl = doubanUrl.substring(0, doubanUrl.length() - 1);
                            return Film.builder()
                                    .url(doubanUrl)
                                    .movieId(Long.parseLong(doubanUrl.substring(doubanUrl.lastIndexOf("/") + 1)))
                                    .imageLarge(imgElement.attr("src"))
                                    .title(imgElement.attr("alt"))
                                    .movieTypeEnum(MovieTypeEnum.TOP)
                                    .updateTime(updateTime)
                                    .build();
                        })
                        .collect(Collectors.toList()));
            } catch (Exception e) {
                log.error("get top movies error: ", e);
            }
        }
        return filmList;
    }

    public Film getFilmDetailByMovieTypeAndUrl(MovieTypeEnum movieTypeEnum, String url) {
        if (MovieTypeEnum.RECENT.equals(movieTypeEnum)) {
            return getFilmDetailFromUrl(url);
        } else if (MovieTypeEnum.TOP.equals(movieTypeEnum)) {
            return getTopFilmDetailFromUrl(url);
        }
        return null;
    }

    private Film getFilmDetailFromUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            Document document = Jsoup.parse(new URL(url), 30000);
            String summary = null;
            String genres = null;
            Element summaryElement = document.getElementById("link-report-intra");
            if (Objects.nonNull(summaryElement)) {
                Elements summaryElements = summaryElement.getElementsByTag("span");
                summary = StringUtils.strip(summaryElements.get(0).text());
            }

            Element infoElement = document.getElementById("info");
            if (Objects.nonNull(infoElement)) {
                Elements spanElements = infoElement.getElementsByTag("span");
                List<String> genreList = spanElements.stream()
                        .filter(item -> "v:genre".equals(item.attr(PROPERTY)))
                        .map(Element::text)
                        .collect(Collectors.toList());
                genres = StringUtils.join(genreList, ConstantUtils.SEPARATOR);
            }

            return Film.builder()
                    .summary(summary)
                    .genres(genres)
                    .build();
        } catch (Exception e) {
            log.error("failed to get detail from url: {}", url, e);
            return null;
        }
    }

    private Film getTopFilmDetailFromUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            Document document = Jsoup.parse(new URL(url), 30000);
            String summary = null;
            String genres;
            Element summaryElement = document.getElementById("link-report-intra");
            if (Objects.nonNull(summaryElement)) {
                Elements summaryElements = summaryElement.getElementsByTag("span");
                summary = StringUtils.strip(summaryElements.get(0).text());
            }

            Element ratingElement = document.getElementById("interest_sectl");
            Optional<Double> rating = Optional.of(0d);
            if (Objects.nonNull(ratingElement)) {
                Elements strongElements = ratingElement.getElementsByTag("strong");
                rating = strongElements.stream()
                        .filter(item -> "v:average".equals(item.attr(PROPERTY)))
                        .map(Element::text)
                        .map(Double::parseDouble)
                        .limit(1)
                        .findFirst();
            }

            Film film = Film.builder()
                    .summary(summary)
                    .rating(rating.orElse(0d))
                    .build();

            Element infoElement = document.getElementById("info");
            if (Objects.nonNull(infoElement)) {
                Elements spanElements = infoElement.getElementsByTag("span");
                List<String> genreList = spanElements.stream()
                        .filter(item -> "v:genre".equals(item.attr(PROPERTY)))
                        .map(Element::text)
                        .collect(Collectors.toList());
                genres = StringUtils.join(genreList, ConstantUtils.SEPARATOR);

                Elements aElements = infoElement.getElementsByTag("a");
                List<String> directorList = aElements.stream()
                        .filter(item -> "v:directedBy".equals(item.attr("rel")))
                        .map(Element::text)
                        .collect(Collectors.toList());

                List<String> actorList = aElements.stream()
                        .filter(item -> "v:starring".equals(item.attr("rel")))
                        .map(Element::text)
                        .limit(5)
                        .collect(Collectors.toList());

                Optional<Integer> movieYear = spanElements.stream()
                        .filter(item -> "v:initialReleaseDate".equals(item.attr(PROPERTY)))
                        .map(Element::text)
                        .map(item -> Integer.parseInt(item.substring(0, 4)))
                        .limit(1)
                        .findFirst();

                film.setGenres(genres);
                film.setDirectors(StringUtils.join(directorList, ConstantUtils.SEPARATOR));
                film.setCasts(StringUtils.join(actorList, ConstantUtils.SEPARATOR));
                film.setMovieYear(movieYear.orElse(0));
            }

            return film;
        } catch (Exception e) {
            log.error("failed to get detail from url: {}", url, e);
            return null;
        }
    }

    private String getImageUrl(Element item) {
        Elements img = item.getElementsByTag("img");
        String url = "";
        try {
            url = img.get(0).attr("src");
        } catch (Exception e) {
            log.error("get image url error: {}", img.html(), e);
        }
        return url;
    }

    private String getJoinString(String text, String split) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        String[] strings = text.split(split);
        List<String> textList = Lists.newArrayList();
        for (String string : strings) {
            textList.add(string.trim());
        }
        return StringUtils.join(textList, ConstantUtils.SEPARATOR);
    }
}
