import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Spin } from 'antd';
import { fetchAllMovieList } from '../actions/actions';
import Main from '../containers/main';
import SideBar from '../components/sidebar.js';

class AllMoviesContainer extends Component {
  componentDidMount() {
    this.props.dispatch(fetchAllMovieList.request());
  };

  render() {
    const { data, loading } = this.props;
    return (
      <SideBar
        keys={'/all'}
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
    data: state.info.allMoviesList.data,
    loading: state.info.allMoviesList.loading,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(AllMoviesContainer);
