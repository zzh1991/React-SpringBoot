import fetch from 'isomorphic-fetch';
import { ActionTypes } from './actionTypes';
import { fetchStudent, requestStudent, syncRecentMovies, syncTopMovies } from './miniAction';

export const fetchData = () => {
  const url = '/data';
  return (dispatch) => {
    dispatch(requestStudent());
    return fetch(url)
      .then(response => response.json())
      .then(json => dispatch(fetchStudent(json)));
  };
};

function createAction(types) {
  return {
    request(data) {
      return {
        type: types[0],
        data,
      };
    },
    success(data) {
      return {
        type: types[1],
        data,
      };
    },
    error(data) {
      return {
        type: types[2],
        data,
      };
    },
  };
}

export const fetchMovieRecent = createAction([
  ActionTypes.FETCH_MOVIE_RECENT_REQUEST,
  ActionTypes.FETCH_MOVIE_RECENT_SUCCESS,
  ActionTypes.FETCH_MOVIE_RECENT_FAILURE,
]);


export const fetchMovieTop = createAction([
  ActionTypes.FETCH_MOVIE_TOP_REQUEST,
  ActionTypes.FETCH_MOVIE_TOP_SUCCESS,
  ActionTypes.FETCH_MOVIE_TOP_FAILURE,
]);

export const fetchMovieViewed = createAction([
  ActionTypes.FETCH_MOVIE_VIEWED_REQUEST,
  ActionTypes.FETCH_MOVIE_VIEWED_SUCCESS,
  ActionTypes.FETCH_MOVIE_VIEWED_FAILURE,
]);

export const fetchViewdMovieList = createAction([
  ActionTypes.FETCH_VIEWED_MOVIE_LIST_REQUEST,
  ActionTypes.FETCH_VIEWED_MOVIE_LIST_SUCCESS,
  ActionTypes.FETCH_VIEWED_MOVIE_LIST_FAILURE,
]);

export const fetchStarMovieList = createAction([
  ActionTypes.FETCH_STAR_MOVIE_LIST_REQUEST,
  ActionTypes.FETCH_STAR_MOVIE_LIST_SUCCESS,
  ActionTypes.FETCH_STAR_MOVIE_LIST_FAILURE,
]);
export const saveMovieToLocal = (movieId, movieListName) => {
  const movieIds = localStorage.getItem(movieListName);
  if (movieIds !== null) {
    const movieSet = new Set(JSON.parse(movieIds));
    movieSet.add(movieId);
    localStorage.setItem(movieListName, JSON.stringify(Array.from(movieSet)));
  } else {
    localStorage.setItem(movieListName, JSON.stringify([movieId]));
  }
};

export const deleteMovieToLocal = (movieId, movieListName) => {
  const movieSet = new Set(JSON.parse(localStorage.getItem(movieListName)));
  movieSet.delete(movieId);
  localStorage.setItem(movieListName, JSON.stringify(Array.from(movieSet)));
};

export const getMovieList = (movieListName) => {
  const movieIds = localStorage.getItem(movieListName);
  if (movieIds !== null) {
    return new Set(JSON.parse(movieIds));
  } else {
    return new Set();
  }
};

export const syncRecentMovieList = () => {
  const url = 'sync/recent';
  return (dispatch) => {
    dispatch(syncRecentMovies());
    return fetch(url, {
      method: 'POST',
    })
      .then(() => dispatch(fetchMovieRecent.request()));
  };
};

export const syncTopMovieList = () => {
  const url = 'sync/top';
  return (dispatch) => {
    dispatch(syncTopMovies());
    return fetch(url, {
      method: 'POST',
    })
      .then(() => dispatch(fetchMovieTop.request()));
  };
};
