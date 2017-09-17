import { call, put } from 'redux-saga/effects';
import { takeLatest } from 'redux-saga';
import Api from './app';


export function* fetchData(request) {
  try {
    const data = yield call(Api.fetchUser, request);
    yield put({type: "FETCH_SUCCEEDED", data});
  } catch (error) {
    yield put({type: "FETCH_FAILED", error});
  }
}

function* watchFetchData() {
  yield* takeLatest('FETCH_REQUESTED', fetchData)
}

export const HelloSage={
  watchFetchData,
};