import fetch from 'isomorphic-fetch';
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