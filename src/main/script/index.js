import React from 'react';
import ReactDOM from 'react-dom';
import Routes from './routes';

ReactDOM.render(
  <Routes />,
  document.getElementById('react'),
);

if (module.hot) module.hot.accept('./routes', () => render(Routes));
