import { call, put } from 'redux-saga/effects';
import { takeLatest } from 'redux-saga';
import Api from '../services/api';
import { ActionTypes } from '../actions/actionTypes';
import { fetchStudentData } from '../actions/actions';
import { fetchStudent, requestStudent, fetchStudentError } from '../actions/miniAction';


function* fetchDataSage(action) {
  // const { request } = action.payload;
  try {
    const payload = yield call(Api.fetchUser);
    yield put(fetchStudent(payload));
  } catch (error) {
    yield put(fetchStudentError(error.message));
  }
}

function* watchFetchData() {
  yield* takeLatest(ActionTypes.FETCH_DATA_REQUEST, fetchDataSage)
}

export default function* HelloSaga() {
  yield [
    watchFetchData(),
  ]
}
