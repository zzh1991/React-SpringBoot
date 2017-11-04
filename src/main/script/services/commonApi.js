import fetch from 'isomorphic-fetch';

export default function(url, request) {
  if (request && request.body) {
    request.body = JSON.stringify(request.body);
  }

  const headers = {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  };
  
  request = {
    ...{headers},
    ...request,
  };

  return fetch(url, request)
  	.then(response => response.json().then(body => ({ response, body })))
    .then(({ response, body }) => {
      return body;
    });
}

