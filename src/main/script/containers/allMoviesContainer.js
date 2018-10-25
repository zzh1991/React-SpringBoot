import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Spin } from 'antd';
import { fetchAllMovieList } from '../actions/actions';
import Main from '../containers/main';

class AllMoviesContainer extends Component {
  componentDidMount() {
    this.props.dispatch(fetchAllMovieList.request());
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
          />
        </Spin>
      </div>
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
