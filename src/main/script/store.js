import {createStore, combineReducers, applyMiddleware, compose} from 'redux';
import createSagaMiddleware from 'redux-saga'
import reducer from './reducer';
import { helloSaga } from './sagas'

// const reducer = combineReducers({
//   info: reducer,
// });

const middlewares = [];
middlewares.push(createSagaMiddleware(helloSaga));
const store = createStore(
  reducer,
  applyMiddleware(...middlewares),
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

export default store;