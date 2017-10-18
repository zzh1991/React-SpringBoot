import fetch from 'isomorphic-fetch';

export default function(url, request) {
  if (request && request.body) {
    request.body = JSON.stringify(request.body);
  }

  const RequestHeader = {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  };

  request = {
    ...{RequestHeader},
    ...request,
  };

  return fetch(url, request)
    .then(response => response.json())
    .then((json) => {
      return json;
    });
}

