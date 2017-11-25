import { call, put } from 'redux-saga/effects';
import { takeLatest } from 'redux-saga';
import Api from '../services/api';
import { ActionTypes } from '../actions/actionTypes';
import { fetchMovieRecent, fetchMovieTop,
  fetchMovieViewed, fetchViewdMovieList,
  fetchStarMovieList
 } from '../actions/actions';
import { fetchStudent, fetchStudentError, fetchStudentInfoSuccess,
fetchStudentInfoFailure
} from '../actions/miniAction';


function* fetchDataSage(action) {
  try {
    const payload = yield call(Api.fetchUser);
    yield put(fetchStudent(payload));
  } catch (error) {
    yield put(fetchStudentError(error.message));
  }
}

function* fetchStudentInfoSage(action) {
  const request = action.data;
  try {
    const payload = yield call(Api.fetchStudentInfo, request);
    yield put(fetchStudentInfoSuccess(payload));
  } catch (error) {
    yield put(fetchStudentInfoFailure(error.message));
  }
}

function* fetchMovieRecentSage(action) {
  try {
    const payload = yield call(Api.fetchMovieRecent);
    yield put(fetchMovieRecent.success(payload));
  } catch (error) {
    yield put(fetchMovieRecent.error(error.message));
  }
}

function* fetchMovieTopSage(action) {
  try {
    const payload = yield call(Api.fetchMovieTop);
    yield put(fetchMovieTop.success(payload));
  } catch (error) {
    yield put(fetchMovieTop.error(error.message));
  }
}

function* fetchMovieViewedSage(action) {
  const request = action.data;
  try {
    const payload = yield call(Api.fetchMovieViewed, request);
    yield put(fetchMovieViewed.success(payload));
  } catch (error) {
    yield put(fetchMovieViewed.error(error.message));
  }
}

function* fetchViewedMovieListSage(action) {
  const request = action.data;
  try {
    const payload = yield call(Api.fetchViewedMovieList, request);
    yield put(fetchViewdMovieList.success(payload));
  } catch (error) {
    yield put(fetchViewdMovieList.error(error.message));
  }
}

function* fetchStarMovieListSage(action) {
  const request = action.data;
  try {
    const payload = yield call(Api.fetchStarMovieList, request);
    yield put(fetchStarMovieList.success(payload));
  } catch (error) {
    yield put(fetchStarMovieList.error(error.message));
  }
}

function* watchFetchData() {
  yield* takeLatest(ActionTypes.FETCH_DATA_REQUEST, fetchDataSage)
}

function* watchFetchStudentInfo() {
  yield* takeLatest(ActionTypes.FETCH_STUDENT_INFO_REQUEST, fetchStudentInfoSage)
}

function* watchFetchMovieRecent() {
  yield* takeLatest(ActionTypes.FETCH_MOVIE_RECENT_REQUEST, fetchMovieRecentSage)
}

function* watchFetchMovieTop() {
  yield* takeLatest(ActionTypes.FETCH_MOVIE_TOP_REQUEST, fetchMovieTopSage)
}

function* watchFetchMovieViewed() {
  yield* takeLatest(ActionTypes.FETCH_MOVIE_VIEWED_REQUEST, fetchMovieViewedSage)
}

function* watchFetchViewedMovieList() {
  yield* takeLatest(ActionTypes.FETCH_VIEWED_MOVIE_LIST_REQUEST, fetchViewedMovieListSage)
}

function* watchFetchStarMovieList() {
  yield* takeLatest(ActionTypes.FETCH_STAR_MOVIE_LIST_REQUEST, fetchStarMovieListSage)
}

export default function* HelloSaga() {
  yield [
    watchFetchData(),
    watchFetchStudentInfo(),
    watchFetchMovieRecent(),
    watchFetchMovieTop(),
    watchFetchMovieViewed(),
    watchFetchViewedMovieList(),
    watchFetchStarMovieList(),
  ]
}