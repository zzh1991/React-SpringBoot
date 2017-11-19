import React, { Component } from 'react';
import SideBar from '../components/sidebar.js'
import Main from '../containers/main';
import '../styles/style.css';
import {Toolbar, ToolbarGroup} from 'material-ui/Toolbar';
import { connect } from 'react-redux';
import { fetchMovieTop } from '../actions/actions';

class TopMoviesContainer extends Component {

  componentDidMount = () => {
    this.props.dispatch(fetchMovieTop.request())
  };

  render() {
    const { data } = this.props;
    return (
      <SideBar keys={'/top'} >
        <Main
          data={data}
        />
      </SideBar>
    );
  }
};

function mapStateToProps(state) {
  return {
    data: state.info.movieTopList.data,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(TopMoviesContainer);
