import React, { Component } from 'react';
import SideBar from '../components/sidebar.js'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Main from '../containers/main';
import '../styles/style.css';
import {Toolbar, ToolbarGroup} from 'material-ui/Toolbar';
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
      <div>
        <MuiThemeProvider>
          <SideBar>
            <Toolbar>
              <ToolbarGroup firstChild={true}>
                <h2 className={'toolbar-title'}>{'想看'}</h2>
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
    data: state.info.movieStarList.data,
  }
}

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(StarMoviesContainer);
