import { ActionTypes } from './actionTypes';

export const requestStudent = () => {
  return {
    type: ActionTypes.FETCH_DATA_REQUEST,
  };
};

export const fetchStudent = (data) => {
  return {
    type: ActionTypes.FETCH_DATA_SUCCESS,
    data,
  };
};

export const fetchStudentError = (error) => {
  return {
    type: ActionTypes.FETCH_DATA_FAILURE,
    error,
  };
};

export const fetchStudentInfoRequest = (data) => {
  return {
    type: ActionTypes.FETCH_STUDENT_INFO_REQUEST,
    data,
  };
};

export const fetchStudentInfoSuccess = (data) => {
  return {
    type: ActionTypes.FETCH_STUDENT_INFO_SUCCESS,
    data,
  };
};

export const fetchStudentInfoFailure = (data) => {
  return {
    type: ActionTypes.FETCH_STUDENT_INFO_FAILURE,
    data,
  };
};

export const syncRecentMovies = () => {
  return {
    type: ActionTypes.SYNC_MOVIE_RECENT_REQUEST,
  };
};

export const syncTopMovies = () => {
  return {
    type: ActionTypes.SYNC_MOVIE_TOP_REQUEST,
  };
};
