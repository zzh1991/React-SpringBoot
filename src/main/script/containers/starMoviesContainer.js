import React, { Component } from 'react';
import SideBar from '../components/sidebar.js'
import Main from '../containers/main';
import '../styles/style.css';
import { connect } from 'react-redux';
import { fetchStarMovieList, getMovieList } from '../actions/actions';

const starMovieName = 'starMovieList';

class StarMoviesContainer extends Component {

  componentDidMount = () => {
    let list = getMovieList(starMovieName);
    list = list === null ? [] : Array.from(list);
    this.props.dispatch(fetchStarMovieList.request(list));
  };

  render() {
    const { data } = this.props;
    return (
      <SideBar keys={'/star'} >
        <Main
          data={data}
        />
      </SideBar>
    );
  }
};

function mapStateToProps(state) {
  return {
    data: state.info.movieStarList.data,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(StarMoviesContainer);
