import React from 'react';
import '../styles/login.css';
import {MdArrowBack} from 'react-icons/lib/md';

const NavigationPanel = (props) => {

	return (
		<div className='NavigationPanel'>
			<MdArrowBack onClick={props.initialState} className='back'/>
		</div>
	);
}

export default NavigationPanel;