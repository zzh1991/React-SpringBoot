import React , {Component} from 'react';
import PropTypes from 'prop-types';
import { Form, Icon, Input, Button, Checkbox } from 'antd';
import '../styles/login.css';
import {Motion, spring} from 'react-motion';
import MdInput from './Input';
import SubmitButton from './SubmitButton';

const FormItem = Form.Item;

class NormalLoginForm extends React.Component {
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received values of form: ', values);
      }
    });
  }
  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form onSubmit={this.handleSubmit} className="login-form">
        <FormItem>
          {getFieldDecorator('userName', {
            rules: [{ required: true, message: 'Please input your username!' }],
          })(
            <Input prefix={<Icon type="user" style={{ fontSize: 13 }} />} placeholder="Username" />
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('password', {
            rules: [{ required: true, message: 'Please input your Password!' }],
          })(
            <Input prefix={<Icon type="lock" style={{ fontSize: 13 }} />} type="password" placeholder="Password" />
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('remember', {
            valuePropName: 'checked',
            initialValue: true,
          })(
            <Checkbox style={{ color: '#FFFCE8' }}>Remember me</Checkbox>
          )}
          <Button type="primary" htmlType="submit" className="login-form-button" style={{ 
						position: 'absolute',
						top: '-50px',
					}}>
            Log in
          </Button>
        </FormItem>
      </Form>
    );
  }
}

const WrappedNormalLoginForm = Form.create()(NormalLoginForm);

class SignExpanded extends Component {

	constructor(props) {
		super(props);
		this.state = {
			flexState: false,
			animIsFinished: false
		};
	}

	componentDidMount () {
     	this.setState({flexState: !this.state.flexState});  
  	}


	isFinished = () => {
		this.setState({animIsFinished: true});
	}

	render () {
		const { type } = this.props;
		return (
			<Motion style={{
				flexVal: spring(this.state.flexState ? 8 : 1)
			}} onRest={this.isFinished}>
			{({flexVal}) =>
			<div className={this.props.type=='signIn' ? 'signInExpanded' : 'signUpExpanded'} style={{
				flexGrow: `${flexVal}`
			}}>
				<Motion style={{ 
					opacity: spring(this.state.flexState ? 1 : 0,{stiffness: 300, damping: 17}),
					y: spring(this.state.flexState ? 0 : 50, {stiffness: 100, damping: 17})
				 }} >
						{({opacity, y}) =>
						<div className='logForm' style={{
							WebkitTransform: `translate3d(0, ${y}px, 0)`,
							transform: `translate3d(0, ${y}px, 0)`,
							opacity: `${opacity}`
						}}>
							<h2>{type == 'signIn' ? 'SIGN IN' : 'SIGN UP'}</h2>
							{type === 'signIn' &&
								<WrappedNormalLoginForm />
							}
							{type === 'signUp' &&
							<div>
								<MdInput
									id="login"
									type="text"
									placeholder="LOGIN" />
								<MdInput
									id="password"
									type="password"
									placeholder="PASSWORD" />
									<SubmitButton type={this.props.type}></SubmitButton>
							</div>
							}
							<a href="url" className='forgotPass'>{type == 'signIn' ? 'Forgot password?' : ''}</a>
						</div>
						}
				</Motion>
			</div>
		}
			</Motion>
		);
	}

}

SignExpanded.PropTypes ={
	type: PropTypes.string	
};

export default SignExpanded;