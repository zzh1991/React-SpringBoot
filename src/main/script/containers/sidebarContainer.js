import React, { Component } from 'react';
import { connect } from 'react-redux';
import { fetchMovieRecent, syncRecentMovieList } from '../actions/actions';
import Main from '../containers/main';
import '../styles/style.css';
import SideBar from '../components/sidebar.js'

class SideBarContainer extends Component {

  componentDidMount = () => {
    this.props.dispatch(fetchMovieRecent.request());
  };

  onSync = () => {
    this.props.dispatch(syncRecentMovieList());
  };

  render() {
    const { data } = this.props;
    return (
        <SideBar
          keys={'/'}
          showSyncButton
          syncMovies={this.onSync}
        >
          <Main
            data={data}
          />
        </SideBar>
    );
  }
};

function mapStateToProps(state) {
  return {
    data: state.info.movieRecentList.data,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(SideBarContainer);
