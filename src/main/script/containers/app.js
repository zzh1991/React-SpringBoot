import React from 'react';
import { Button } from 'antd';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import TextField from 'material-ui/TextField';

class App extends React.Component {
  constructor(props) {
    super(props);}

  render() {
    return (
      <div>
        <h1>Welcome to React</h1>

        <div>
          <MuiThemeProvider>
            <div>
              <TextField
                id="text"
                defaultValue={'Code'}
                floatingLabelText="Input Name"
              />
              <TextField
                id="text"
                defaultValue={'Hello'}
                floatingLabelText="Input Name"
              />
            </div>
          </MuiThemeProvider>
        </div>
        <div>
          <Button type={'danger'}>Button</Button>
        </div>
      </div>
    )
  }
}

export default App;

