import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Spin } from 'antd';
import { fetchMovieRecent, syncRecentMovieList } from '../actions/actions';
import Main from '../containers/main';
import '../styles/style.css';

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

export default connect(mapStateToProps, mapDispatchToProps)(SideBarContainer);
