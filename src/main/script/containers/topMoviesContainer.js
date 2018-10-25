import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Spin } from 'antd';
import SideBar from '../components/sidebar.js';
import Main from '../containers/main';
import '../styles/style.css';
import { fetchMovieTop, syncTopMovieList } from '../actions/actions';

class TopMoviesContainer extends Component {
  componentDidMount() {
    this.props.dispatch(fetchMovieTop.request());
  };

  onSync = () => {
    this.props.dispatch(syncTopMovieList());
  };

  render() {
    const { data, loading } = this.props;
    return (
      <div>
        <Spin
          tip="Loading..."
          spinning={loading}
        >
          <Main
            data={data}
            showSyncButton
            syncMovies={this.onSync}
          />
        </Spin>
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    data: state.info.movieTopList.data,
    loading: state.info.movieTopList.loading,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(TopMoviesContainer);
