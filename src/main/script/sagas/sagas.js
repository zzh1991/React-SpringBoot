import { call, put } from 'redux-saga/effects';
import { takeLatest } from 'redux-saga';
import Api from '../services/api';
import { ActionTypes } from '../actions/actionTypes';
import { fetchStudentData } from '../actions/actions';
import { fetchStudent, fetchStudentError, fetchStudentInfoSuccess,
fetchStudentInfoFailure,
} from '../actions/miniAction';


function* fetchDataSage(action) {
  // const { request } = action.payload;
  try {
    const payload = yield call(Api.fetchUser);
    yield put(fetchStudent(payload));
  } catch (error) {
    yield put(fetchStudentError(error.message));
  }
}

function* fetchStudentInfoSage(action) {
  console.log(JSON.stringify(action));
  const request = action.data;
  console.log(request);
  try {
    const payload = yield call(Api.fetchStudentInfo, request);
    yield put(fetchStudentInfoSuccess(payload));
  } catch (error) {
    yield put(fetchStudentInfoFailure(error.message));
  }
}

function* watchFetchData() {
  yield* takeLatest(ActionTypes.FETCH_DATA_REQUEST, fetchDataSage)
}

function* watchFetchStudentInfo() {
  yield* takeLatest(ActionTypes.FETCH_STUDENT_INFO_REQUEST, fetchStudentInfoSage)
}

export default function* HelloSaga() {
  yield [
    watchFetchData(),
    watchFetchStudentInfo(),
  ]
}