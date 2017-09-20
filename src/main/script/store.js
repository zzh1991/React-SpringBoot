import {createStore, combineReducers, applyMiddleware, compose} from 'redux';
import createSagaMiddleware from 'redux-saga'
import thunkMiddleware from 'redux-thunk';
import logger from 'redux-logger';
import reducer from './reducer';
import { helloSaga } from './sagas'

// const reducer = combineReducers({
//   info: reducer,
// });

const middlewares = [];
middlewares.push(createSagaMiddleware(helloSaga));
middlewares.push(thunkMiddleware);
middlewares.push(logger);
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(reducer, composeEnhancers(
  applyMiddleware(...middlewares)));

export default store;