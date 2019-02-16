import React, { Component } from 'react';
import { Spin } from 'antd';
import Main from '../containers/main';
import '../styles/style.css';
import { connect } from 'react-redux';
import { fetchViewdMovieList, getMovieList } from '../actions/actions';

const watchedMovieName = 'watchedMovieList';

class ViewedMoviesContainer extends Component {

  componentDidMount() {
    let list = getMovieList(watchedMovieName);
    list = list === null ? [] : Array.from(list);
    this.props.dispatch(fetchViewdMovieList.request(list));
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
};

function mapStateToProps(state) {
  return {
    data: state.info.movieViewedList.data,
    loading: state.info.movieViewedList.loading,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(ViewedMoviesContainer);
