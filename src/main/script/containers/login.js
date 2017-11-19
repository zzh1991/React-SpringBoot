import React, {Component} from 'react';
import '../styles/login.css';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import NavigationPanel from '../components/NavigationPanel';
import Modal from '../components/Modal';

class Login extends Component {

	constructor(props) {
		super(props);
	}

	componentDidMount() {
		this.setState({ mounted: true });
	}
	
	handleSubmit = (e) => {
		e.preventDefault();
	}

	render() {		
		return(
			<div className="App">
				<ReactCSSTransitionGroup 
					transitionName="example"
					transitionEnterTimeout={500}
					transitionLeaveTimeout={300}>
					<div className="App_test">
						<NavigationPanel></NavigationPanel>
						<Modal onSubmit={this.handleSubmit}/>
					</div>
				</ReactCSSTransitionGroup>
			</div>
		);
	}
}

export default Login;
