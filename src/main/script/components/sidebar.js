import React from 'react';
import PropTypes from 'prop-types';
import { Layout, Icon, Menu } from 'antd';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import injectTapEventPlugin from 'react-tap-event-plugin';
import { withRouter } from 'react-router-dom';
import '../styles/style.css';
import { white } from 'material-ui/styles/colors';

const { Header, Content, Sider } = Layout;
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
      collapsed: false,
    };
  }

  toPath = (path) => {
    let { router } = this.context;
    router.history.push(path)
  };

  toggle = () => {
    this.setState({
      collapsed: !this.state.collapsed,
    });
  };

  clickMenu  = (e) => {
    this.toPath(e.key);
  };

  render() {
    return (
      <Layout>
          <Header className="header" style={{
            display: 'flex',
            alignItems: 'center',
            paddingLeft: '20px',
          }} >
            <div style={{ fontSize: 20, color: 'white', margin: 4 }}>
              <Icon
                className="trigger"
                type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
                onClick={this.toggle}
              />
              {'  电影助手'}
            </div>
          </Header>
          <Layout style={{ height: '92vh', }} >
            <Sider
              trigger={null}
              collapsible
              collapsed={this.state.collapsed}
            >
              <Menu theme="dark" mode="inline" onClick={this.clickMenu}  defaultSelectedKeys={[this.props.keys]}>
                <Menu.Item key="/">
                  <Icon type="home" />
                  <span>上映电影</span>
                </Menu.Item>
                <Menu.Item key="/top">
                  <Icon type="heart" />
                  <span>Top 100</span>
                </Menu.Item>
                <Menu.Item key="/view">
                  <Icon type="eye" />
                  <span>已观影</span>
                </Menu.Item>
                <Menu.Item key="/star">
                  <Icon type="star" />
                  <span>想看</span>
                </Menu.Item>
              </Menu>
            </Sider>
            <Content style={{ padding: 20, }} >
              <MuiThemeProvider>
                {this.props.children}
              </MuiThemeProvider>
            </Content>
          </Layout>
      </Layout>
    );
  }
}

SideBar.contextTypes={
  router: PropTypes.object,
};

export default withRouter(SideBar);
