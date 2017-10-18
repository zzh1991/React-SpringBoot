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
  },
  fetchMovieRecent() {
    const url = 'movie/recent';
    return CommonApi(url, {
      method: 'GET',
      mode: 'cors',
    });
  },
  fetchMovieTop() {
    const url = 'movie/top250';
    return CommonApi(url, {
      method: 'GET',
      mode: 'cors',
    });
  },
  fetchMovieViewed(request) {
    const url = `movie/viewed/${request.id}/${request.viewed}`;
    console.log(url);
    return CommonApi(url, {
      method: 'GET',
      mode: 'cors',
    });
  },
}
