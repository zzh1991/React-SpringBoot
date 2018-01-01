import React, { Component } from 'react';
import { connect } from 'react-redux';
import SideBar from '../components/sidebar.js';
import Main from '../containers/main';
import '../styles/style.css';
import { fetchMovieTop, syncTopMovieList } from '../actions/actions';

class TopMoviesContainer extends Component {
  componentDidMount = () => {
    this.props.dispatch(fetchMovieTop.request());
  };

  onSync = () => {
    this.props.dispatch(syncTopMovieList());
  };

  render() {
    const { data } = this.props;
    return (
      <SideBar
        keys={'/top'}
        showSyncButton
        syncMovies={this.onSync}
      >
        <Main
          data={data}
        />
      </SideBar>
    );
  }
}

function mapStateToProps(state) {
  return {
    data: state.info.movieTopList.data,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(TopMoviesContainer);
