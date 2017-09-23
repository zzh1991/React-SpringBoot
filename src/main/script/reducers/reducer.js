import { ActionTypes } from '../actions/actionTypes';

export default (state = {data: []}, action) => {
  console.log(JSON.stringify(action));
  switch (action.type) {
    case ActionTypes.FETCH_DATA_REQUEST:
      return {
        ...state,
        data: [],
      };
    case ActionTypes.FETCH_DATA_SUCCESS:
      return {
        ...state,
        data: action.data,
      };
    case ActionTypes.FETCH_STUDENT_INFO_SUCCESS:
      return {
        ...state,
        data: [action.data],
      };
    default:
      return state;
  }
};