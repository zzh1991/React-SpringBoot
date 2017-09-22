import fetch from 'isomorphic-fetch';
import { ActionTypes } from './actionTypes';
import { fetchStudent, requestStudent } from './miniAction';
import { createAction } from 'redux-actions';

export const fetchData = () => {
  const url = '/data';
  return (dispatch) => {
    dispatch(requestStudent());
    return fetch(url)
      .then(response => response.json())
      .then(json => dispatch(fetchStudent(json)));
  }
};

export const fetchStudentData = createAction([
  ActionTypes.FETCH_DATA_REQUEST,
  ActionTypes.FETCH_DATA_SUCCESS,
  ActionTypes.FETCH_DATA_FAILURE,
]);