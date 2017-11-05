import React, { Component } from 'react';
import Divider from 'material-ui/Divider';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import { Icon, Tag } from 'antd';
import '../styles/resume.css';

class PersonalCard extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="personal-card">
        <div className="personal-image">
          <img
            className="img-circle"
            src="images/tianchi.jpg"
            style={{ width:'150px', height:'150px' }}
          />
        </div>
        <div className="personal-name">{'Zhihao Zhang'}</div>
        <div className="personal-position">{'Software Development Engineer'}</div>
        <MuiThemeProvider>
          <Divider inset />
        </MuiThemeProvider>
        <div className="personal-link">
          <span className="icon-space"><Icon type="environment" /></span>{' Shanghai, China '}
        </div>
        <div className="personal-link">
          <span className="icon-space"><Icon type="mail" /></span>{' zhihaozhangv5@gmail.com '}
        </div>
        <div className="personal-link">
          <span className="icon-space"><Icon type="github" /></span><a
          href="https://github.com/zzh1991"
          target={'_blank'}
        >
          {' https://github.com/zzh1991 '}
          </a>
        </div>
        <div className="skill-tag">
          <span className="icon-space"><Icon type="tags" /></span>
          <Tag color="green" >{'Spring Boot'}</Tag>
          <Tag color="cyan">{'React'}</Tag>
          <Tag color="blue" >{'Java'}</Tag>
          <Tag color="blue">{'Python'}</Tag>
          <Tag color="black">{'Linux'}</Tag>
        </div>
        <div className="skill-tag">
          <Tag color="yellow">{'JavaScript'}</Tag>
          <Tag color="red">{'Git'}</Tag>
          <Tag color="cyan">{'Redux'}</Tag>
          <Tag color="purple">{'Bootstrap'}</Tag>
          <Tag color="cyan">{'jQuery'}</Tag>
        </div>
      </div>
    );
  }
}

export default PersonalCard;
