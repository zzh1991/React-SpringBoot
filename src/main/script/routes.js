import React from 'react';
import { HashRouter as Router, Route } from 'react-router-dom';
import {Provider} from 'react-redux';

import store from './store';
import App from './containers/app';
import Main from  './containers/main';
import TopMoviesContainer from  './containers/topMoviesContainer';
import ViewedMoviesContainer from './containers/viewedMoviesContainer';
import StarMoviesContainer from './containers/starMoviesContainer';
import NotFound from './containers/notFound';
import SideBarContainer from './containers/sidebarContainer';

const Routes = () => (
  <Provider store={store}>
    <Router>
      <div>
        <Route exact path="/" component={SideBarContainer} />
        <Route path="/app" component={App} />
        <Route path="/main" component={Main} />
        <Route path="/top" component={TopMoviesContainer} />
        <Route path="/view" component={ViewedMoviesContainer} />
        <Route path="/star" component={StarMoviesContainer} />
        <Route path="/404" component={NotFound} />
      </div>

    </Router>
  </Provider>
);

export default Routes;