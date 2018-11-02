import React, {Component, Fragment} from 'react';
import './index.scss';
import Header from './Header';
import Main from './Main';
import Login from '../Login';
import Forgot from '../Login/Forgot';
import Registration from '../Login/Registration';

class Admin extends Component {
  render () {
    return (
      <Fragment>
        <Header/>
        <Login/>
        <Forgot/>
        <Registration/>
        <Main/>
      </Fragment>
    );
  }
}

export default Admin;