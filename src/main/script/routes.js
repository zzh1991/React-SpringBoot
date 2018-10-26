import React from 'react';
import { HashRouter as Router, Route } from 'react-router-dom';
import {Provider} from 'react-redux';
import Loadable from 'react-loadable';
import store from './store';
import SideBar from './components/sidebar.js';
import NotFound from './containers/notFound';

function Loading() {
  return <div>Loading...</div>;
}

const SideBarContainer = Loadable({
  loader: () => import('./containers/sidebarContainer'),
  loading: Loading,
});

const TopMoviesContainer = Loadable({
  loader: () => import('./containers/topMoviesContainer'),
  loading: Loading,
});

const ViewedMoviesContainer = Loadable({
  loader: () => import('./containers/viewedMoviesContainer'),
  loading: Loading,
});

const StarMoviesContainer = Loadable({
  loader: () => import('./containers/starMoviesContainer'),
  loading: Loading,
});

const AllMoviesContainer = Loadable({
  loader: () => import('./containers/allMoviesContainer'),
  loading: Loading,
});

const Routes = () => (
  <Provider store={store}>
    <Router>
      <SideBar>
        <Route exact path="/" component={SideBarContainer} />
        <Route path="/top" component={TopMoviesContainer} />
        <Route path="/view" component={ViewedMoviesContainer} />
        <Route path="/star" component={StarMoviesContainer} />
        <Route path="/all" component={AllMoviesContainer} />
        <Route path="/404" component={NotFound} />
      </SideBar>
    </Router>
  </Provider>
);

export default Routes;