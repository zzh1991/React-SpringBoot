import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Spin } from 'antd';
import { fetchMovieRecent, syncRecentMovieList } from '../actions/actions';
import Main from './main';
import '../styles/style.css';

class SidebarContainer extends Component {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch(fetchMovieRecent.request());
  }

  onSync = () => {
    const { dispatch } = this.props;
    dispatch(syncRecentMovieList());
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
    data: state.info.movieRecentList.data,
    loading: state.info.movieRecentList.loading,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(SidebarContainer);
