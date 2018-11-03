import React, {Component, Fragment} from 'react';
import './index.scss';
import Main from './Main';
import Login from '../Login';
import Forgot from '../Login/Forgot';
import Registration from '../Login/Registration';

class Admin extends Component {
  render () {
    return (
      <Fragment>
        <Main/>
        <Login/>
        <Forgot/>
        <Registration/>
      </Fragment>
    );
  }
}

export default Admin;