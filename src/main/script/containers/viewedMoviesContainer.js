import React, { Component } from 'react';
import SideBar from '../components/sidebar.js'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
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
      <div>
        <MuiThemeProvider>
          <SideBar>
            <Toolbar>
              <ToolbarGroup firstChild={true}>
                <h2 className={'toolbar-title'}>{'已观影'}</h2>
              </ToolbarGroup>
            </Toolbar>
            <Main
              data={data}
            />
          </SideBar>
        </MuiThemeProvider>
      </div>
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
