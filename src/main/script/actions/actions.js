import fetch from 'isomorphic-fetch';
import { ActionTypes } from './actionTypes';
import { fetchStudent, requestStudent } from './miniAction';

export const fetchData = () => {
  const url = '/data';
  return (dispatch) => {
    dispatch(requestStudent());
    return fetch(url)
      .then(response => response.json())
      .then(json => dispatch(fetchStudent(json)));
  }
};

function createAction(types) {
  return {
    request(data) {
      return {
        type: types[0],
        data,
      }
    },
    success(data) {
      return {
        type: types[1],
        data,
      }
    },
    error(data) {
      return {
        type: types[2],
        data,
      }
    },
  }
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