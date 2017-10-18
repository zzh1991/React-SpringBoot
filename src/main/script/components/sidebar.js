import React from 'react';
import PropTypes from 'prop-types';
import Drawer from 'material-ui/Drawer';
import RaisedButton from 'material-ui/RaisedButton';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import injectTapEventPlugin from 'react-tap-event-plugin';
import {List, ListItem} from 'material-ui/List';
import ActionGrade from 'material-ui/svg-icons/action/grade';
import ContentInbox from 'material-ui/svg-icons/content/inbox';
import ContentDrafts from 'material-ui/svg-icons/content/drafts';
import ContentSend from 'material-ui/svg-icons/content/send';
import Home from 'material-ui/svg-icons/action/home';
import AppBar from 'material-ui/AppBar';
import IconButton from 'material-ui/IconButton';
import Menu from 'material-ui/svg-icons/navigation/menu';
import MapsLocalMovies from 'material-ui/svg-icons/maps/local-movies';
import ActionFavorite from 'material-ui/svg-icons/action/favorite';
import {withRouter} from 'react-router-dom';
import {Toolbar, ToolbarGroup, ToolbarSeparator, ToolbarTitle} from 'material-ui/Toolbar';
import Main from '../containers/main';
import '../styles/style.css';
import { white, cyan500 } from 'material-ui/styles/colors';


injectTapEventPlugin();

const styles = {
  btnStyle: {
    "margin-left": "300px",
  },
  cardStyle : {
    position: 'fixed',
    marginTop: '64px',
  },
  listStyle: {
    backgroundColor: 'cyan',
    color: 'white',
  },
  toolbar: {
    position: 'fixed',
    marginTop: '64px',
    marginLeft: '256px',
  },
  navbar: {
    position: 'fixed',
  },
};

class SideBar extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      open: true,
      openMenu: false,
    };
  }

  handleToggle = () => {
    this.setState({open: !this.state.open});
  };

  toHome = () => {
    let { router } = this.context;
    router.history.push('/')
  };

  toTop = () => {
    let { router } = this.context;
    router.history.push('/top')
  };

  render() {
    return (
      <div>
        <MuiThemeProvider>
          <AppBar style={styles.navbar}
                  title="电影助手"
                  iconElementLeft={<IconButton><Menu /></IconButton>}
                  onLeftIconButtonTouchTap={this.handleToggle}
          />
        </MuiThemeProvider>
        <MuiThemeProvider>
          <Drawer containerStyle={styles.cardStyle} open={this.state.open}>
            <List>
              <ListItem primaryText="上映电影" onTouchTap={this.toHome} leftIcon= {<MapsLocalMovies color={cyan500} />} />
              <ListItem primaryText="Top 100" onTouchTap={this.toTop} leftIcon={<ActionFavorite color={cyan500}  />} />
              {/*<ListItem primaryText="Drafts" leftIcon={<ContentDrafts />} />*/}
              {/*<ListItem*/}
                {/*primaryText="Inbox"*/}
                {/*leftIcon={<ContentInbox />}*/}
                {/*initiallyOpen={true}*/}
                {/*primaryTogglesNestedList={true}*/}
                {/*nestedItems={[*/}
                  {/*<ListItem*/}
                    {/*key={1}*/}
                    {/*primaryText="Starred"*/}
                    {/*leftIcon={<ActionGrade />}*/}
                  {/*/>,*/}
                {/*]}*/}
              {/*/>*/}
            </List>
          </Drawer>
        </MuiThemeProvider>
        <MuiThemeProvider>
          <div className={this.state.open ? 'container-normal' : 'container-expand'} >
            {/*<Toolbar>*/}
              {/*<ToolbarGroup firstChild={true}>*/}
                {/*<h2>{'电影列表'}</h2>*/}
              {/*</ToolbarGroup>*/}
              {/*<ToolbarGroup lastChild>*/}
                {/*/!*<ToolbarTitle text="Options" />*!/*/}

                {/*/!*<ToolbarSeparator />*!/*/}
                {/*/!*<RaisedButton label="Create Broadcast" primary={true} />*!/*/}

              {/*</ToolbarGroup>*/}
            {/*</Toolbar>*/}
            {/*<Main />*/}
            {this.props.children}
          </div>
        </MuiThemeProvider>
      </div>
    );
  }
}

SideBar.contextTypes={
  router: PropTypes.object,
};

export default withRouter(SideBar);
