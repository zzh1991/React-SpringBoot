import React, { Component } from 'react';
import PersonalCard from '../components/personalCard';
import PersonalInfo from '../components/personalInfo';

class ResumeContainer extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <PersonalCard />
        <PersonalInfo />
      </div>
    );
  }
}

export default ResumeContainer;
