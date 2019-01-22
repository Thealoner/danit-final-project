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

library.add(
  faPlus,
  faSignOutAlt,
  faAngleRight
);

const auth = new AuthService();

class HomePage extends Component {
  constructor (props) {
    super(props);
    this.state = {
      user: null
    };
  }

  handleLogout = () => {
    auth.logout();
    this.props.history.replace('/login');
  };

  render () {
    if (this.state.user) {
      return (
        <div className='home'>
          <Header handleLogout={this.handleLogout} userName={this.state.user.sub} />
          <Route exact path="/profile" component={Profile}/>
          <Route exact path='/' component={this.state.user.sub === 'Admin' ? Admin : Manager} />
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