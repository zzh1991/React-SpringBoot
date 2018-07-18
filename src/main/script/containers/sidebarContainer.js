import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Spin } from 'antd';
import { fetchMovieRecent, syncRecentMovieList } from '../actions/actions';
import Main from '../containers/main';
import '../styles/style.css';
import SideBar from '../components/sidebar.js';

class SideBarContainer extends Component {
  componentDidMount() {
    this.props.dispatch(fetchMovieRecent.request());
  };

  onSync = () => {
    this.props.dispatch(syncRecentMovieList());
  };

  render() {
    const { data, loading } = this.props;
    return (
      <SideBar
        keys={'/'}
        showSyncButton
        syncMovies={this.onSync}
      >
        <Spin
          tip="Loading..."
          spinning={loading}
        >
          <Main
            data={data}
          />
        </Spin>
      </SideBar>
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

export default connect(mapStateToProps, mapDispatchToProps)(SideBarContainer);
