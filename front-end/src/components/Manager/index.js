import React, {Component, Fragment} from 'react';
import './index.scss';
import Main from './Main';
import Forgot from '../Login/Forgot';
import Registration from '../Login/Registration';

class Manager extends Component {
  render () {
    return (
      <Fragment>
        <Main/>
        <Forgot/>
        <Registration/>
      </Fragment>
    );
  }
}

export default Manager;