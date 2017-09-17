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
import {withRouter} from 'react-router-dom';
import {Toolbar, ToolbarGroup, ToolbarSeparator, ToolbarTitle} from 'material-ui/Toolbar';
injectTapEventPlugin();

const styles = {
  btnStyle: {
    "margin-left": "300px",
  },
  cardStyle : {
    position: 'fixed',
    marginTop: '64px',
  },
  toolbar: {
    position: 'flex',
    marginLeft: '256px',

  },
  navbar: {
    position: 'flex',
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
    console.log(JSON.stringify(router));
    router.history.push('/404')
  };

  render() {
    return (
      <div>
        <MuiThemeProvider>
          <AppBar style={styles.navbar}
                  title="Title"
                  iconElementLeft={<IconButton><Menu /></IconButton>}
                  onLeftIconButtonTouchTap={this.handleToggle}
          />
        </MuiThemeProvider>
        <MuiThemeProvider>
          <Drawer containerStyle={styles.cardStyle} open={this.state.open}>
            <List>
              <ListItem primaryText="Home" onTouchTap={this.toHome} leftIcon= {<Home/>} />
              <ListItem primaryText="Sent mail" leftIcon={<ContentSend />} />
              <ListItem primaryText="Drafts" leftIcon={<ContentDrafts />} />
              <ListItem
                primaryText="Inbox"
                leftIcon={<ContentInbox />}
                initiallyOpen={true}
                primaryTogglesNestedList={true}
                nestedItems={[
                  <ListItem
                    key={1}
                    primaryText="Starred"
                    leftIcon={<ActionGrade />}
                  />,
                ]}
              />
            </List>
          </Drawer>
        </MuiThemeProvider>
        <MuiThemeProvider>
          <Toolbar style={styles.toolbar}>
            <ToolbarGroup firstChild={true}>

            </ToolbarGroup>
            <ToolbarGroup lastChild>
              <ToolbarTitle text="Options" />

              <ToolbarSeparator />
              <RaisedButton label="Create Broadcast" primary={true} />

            </ToolbarGroup>
          </Toolbar>
        </MuiThemeProvider>
      </div>
    );
  }
}

SideBar.contextTypes={
  router: PropTypes.object,
};

export default withRouter(SideBar);
