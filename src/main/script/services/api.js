import CommonApi from './commonApi';

export default {
  fetchUser () {
    const url = '/data';
    return CommonApi(url, {
      method: 'GET',
    });
  },
}