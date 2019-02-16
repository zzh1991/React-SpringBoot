import React, { Suspense, lazy } from 'react';
import { HashRouter as Router, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import SideBar from './components/sidebar.js';
import NotFound from './containers/notFound';

const SideBarContainer = lazy(() => import('./containers/SidebarContainer'));
const TopMoviesContainer = lazy(() => import('./containers/TopMoviesContainer'));
const ViewedMoviesContainer = lazy(() => import('./containers/ViewedMoviesContainer'));
const StarMoviesContainer = lazy(() => import('./containers/StarMoviesContainer'));
const AllMoviesContainer = lazy(() => import('./containers/AllMoviesContainer'));

const Routes = () => (
  <Provider store={store}>
    <Router>
      <SideBar>
        <Suspense fallback={<div>Loading...</div>}>
          <Route exact path="/" component={SideBarContainer} />
          <Route path="/top" component={TopMoviesContainer} />
          <Route path="/view" component={ViewedMoviesContainer} />
          <Route path="/star" component={StarMoviesContainer} />
          <Route path="/all" component={AllMoviesContainer} />
          <Route path="/404" component={NotFound} />
        </Suspense>
      </SideBar>
    </Router>
  </Provider>
);

export default Routes;
