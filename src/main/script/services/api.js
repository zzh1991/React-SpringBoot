import CommonApi from './commonApi';

export default {
  fetchUser () {
    const url = '/data';
    return CommonApi(url, {
      method: 'GET',
    });
  },
  fetchStudentInfo (request) {
    const url = `/student/${request}`;
    return CommonApi(url, {
      method: 'GET',
    });
  }
}
