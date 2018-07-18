import React, { Component } from 'react';
import { Spin } from 'antd';
import SideBar from '../components/sidebar.js'
import Main from '../containers/main';
import '../styles/style.css';
import { connect } from 'react-redux';
import { fetchStarMovieList, getMovieList } from '../actions/actions';

const starMovieName = 'starMovieList';

class StarMoviesContainer extends Component {

  componentDidMount() {
    let list = getMovieList(starMovieName);
    list = list === null ? [] : Array.from(list);
    this.props.dispatch(fetchStarMovieList.request(list));
  };

  render() {
    const { data, loading } = this.props;
    return (
      <SideBar keys={'/star'} >
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
};

function mapStateToProps(state) {
  return {
    data: state.info.movieStarList.data,
    loading: state.info.movieStarList.loading,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(StarMoviesContainer);
