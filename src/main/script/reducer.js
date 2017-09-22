import { ActionTypes } from './actionTypes';


// redux
// export default (state = {data: []}, action) => {
//   switch (action.type) {
//     case ActionTypes.FETCH_DATA_REQUEST:
//       return {
//         ...state,
//         data: [],
//       };
//     case ActionTypes.FETCH_DATA_SUCCESS:
//       return {
//         ...state,
//         data: action.data,
//       };
//     default:
//       return state;
//   }
// }


// sages
export default (state = {data: []}, action) => {
  const { payload } = action;
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
    default:
      return state;
  }
}