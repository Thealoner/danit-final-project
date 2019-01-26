import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import './index.scss';
import Header from '../Header';
import Profile from '../Profile';
import AuthService from '../../helpers/authService';
import {library} from '@fortawesome/fontawesome-svg-core';
import {faPlus, faSignOutAlt, faAngleRight} from '@fortawesome/free-solid-svg-icons';
import Admin from '../Admin';
import Manager from '../Manager';
import {Loader} from 'semantic-ui-react';
import {connect} from 'react-redux';

library.add(
  faPlus,
  faSignOutAlt,
  faAngleRight
);

const auth = new AuthService();

class HomePage extends Component {
  state = {
    user: null
  };

  handleLogout = () => {
    auth.logout();
    this.props.history.replace('/login');
  };

  render () {
    const { user } = this.state;

    if (user) {
      return (
        <div className='home'>
          <Header handleLogout={this.handleLogout} userName={user.sub} />
          <Route exact path="/profile" component={Profile} />
          <Route exact path='/' component={user.sub === 'Admin' ? Admin : Manager} />
        </div>
      );
    } else {
      return (
        <div className="loader-wrapper">
          <Loader active inline='centered' size='massive'/>
        </div>
      );
    }
  }

  componentDidMount () {
    if (!auth.loggedIn()) {
      this.props.history.replace('/login');
    } else {
      try {
        const profile = auth.getProfile();
        this.setState({
          user: profile
        });
      } catch (err) {
        auth.logout();
        this.props.history.replace('/login');
      }
    }
  }
}

export default HomePage;