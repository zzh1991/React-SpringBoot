import {createStore, combineReducers, applyMiddleware, compose} from 'redux';
import createSagaMiddleware from 'redux-saga'
import thunkMiddleware from 'redux-thunk';
import logger from 'redux-logger';
import reducer from './reducers/reducer';
import HelloSaga from './sagas/sagas'

const combinedReducer = combineReducers({
  info: reducer,
});

const PRODUCTION = 'production';
const middlewares = [];
const sagaMiddleware = createSagaMiddleware();
middlewares.push(sagaMiddleware);
middlewares.push(thunkMiddleware);
if (process.env.NODE_ENV !== PRODUCTION) {
  middlewares.push(logger);
}

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(combinedReducer, composeEnhancers(
  applyMiddleware(...middlewares))
);


sagaMiddleware.run(HelloSaga);
export default store;