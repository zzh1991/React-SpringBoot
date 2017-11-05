import React from 'react';
import { HashRouter as Router, Route } from 'react-router-dom';
import {Provider} from 'react-redux';

import store from './store';
import App from './containers/app';
import Main from  './containers/main'
import NotFound from './containers/notFound';
import SideBarContainer from './containers/sidebarContainer';
import ResumeContainer from  './containers/resumeContianer';

const Routes = () => (
  <Provider store={store}>
    <Router>
      <div>
        <Route exact path="/" component={ResumeContainer} />
        <Route path="/app" component={App} />
        <Route path="/main" component={Main} />
        <Route path="/app" component={App} />
        <Route path="/menu" component={SideBarContainer} />
        <Route path="/404" component={NotFound} />
        <Route path={'/me'} component={ResumeContainer} />
      </div>

    </Router>
  </Provider>
);

export default Routes;