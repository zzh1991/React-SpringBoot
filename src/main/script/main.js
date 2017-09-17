import React from 'react';
import {withRouter} from 'react-router-dom';

const Main = ({children}) => {
  return (
    <div>
      <div>{children}</div>
    </div>
  );
};

export default withRouter(Main);
