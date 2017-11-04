import React, { Component } from 'react';
import PersonalCard from '../components/personalCard';
import PersonalInfo from '../components/personalInfo';
import '../styles/resume.css';

class ResumeContainer extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="global-container">
        <PersonalCard />
        <PersonalInfo />
      </div>
    );
  }
}

export default ResumeContainer;
