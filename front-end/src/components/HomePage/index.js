import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import withAuth from '../../helpers/withAuth';
import './index.scss';
import Header from '../Header';
import AuthService from '../../helpers/authService';
import {library} from '@fortawesome/fontawesome-svg-core';
import {faPlus, faSignOutAlt} from '@fortawesome/free-solid-svg-icons';
import UserNavigation from '../UserNavigation';
import Admin from '../Admin';
import Manager from '../Manager';

library.add(
  faPlus,
  faSignOutAlt
);

const auth = new AuthService();

class HomePage extends Component {
  handleLogout = () => {
    auth.logout();
    this.props.history.replace('/login');
  };

  render () {
    return (
      <div className='home'>
        <Header handleLogout={this.handleLogout} userName={this.props.user.sub}/>
        <Route exact path='/' component={UserNavigation}/>
        <Route path='/admin' component={Admin}/>
        <Route path='/manager' component={Manager}/>
      </div>
    );
  }
}

export default withAuth(HomePage);