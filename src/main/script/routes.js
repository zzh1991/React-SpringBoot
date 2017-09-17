import React from 'react';
import { HashRouter as Router, Route } from 'react-router-dom';
import {Provider} from 'react-redux';

import store from './store';
import App from './app';
import Main from  './main'
import NotFound from './notFound';
import SideBarContainer from './sidebarContainer';

const Routes = () => (
  <Provider store={store}>
    <Router>
      <div>
        <Route exact path="/" component={SideBarContainer} />
        <Route path="/app" component={App} />
        <Route path="/main" component={Main} />
        <Route path="/404" component={NotFound} />
      </div>

    </Router>
  </Provider>
);

export default Routes;