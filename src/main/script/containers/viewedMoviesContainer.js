import React, { Component } from 'react';
import SideBar from '../components/sidebar.js'
import Main from '../containers/main';
import '../styles/style.css';
import {Toolbar, ToolbarGroup} from 'material-ui/Toolbar';
import { connect } from 'react-redux';
import { fetchViewdMovieList, getMovieList } from '../actions/actions';

const watchedMovieName = 'watchedMovieList';

class ViewedMoviesContainer extends Component {

  componentDidMount = () => {
    let list = getMovieList(watchedMovieName);
    list = list === null ? [] : Array.from(list);
    this.props.dispatch(fetchViewdMovieList.request(list));
  };

  render() {
    const { data } = this.props;
    return (
      <SideBar keys={'/view'} >
        <Main
          data={data}
        />
      </SideBar>
    );
  }
};

function mapStateToProps(state) {
  return {
    data: state.info.movieViewedList.data,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(ViewedMoviesContainer);
