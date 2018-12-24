import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import './index.scss';
import Header from '../Header';
import AuthService from '../../helpers/authService';
import {library} from '@fortawesome/fontawesome-svg-core';
import {faPlus, faSignOutAlt, faAngleRight} from '@fortawesome/free-solid-svg-icons';
import UserNavigation from '../UserNavigation';
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
          <Header handleLogout={this.handleLogout} userName={this.state.user.sub}/>
          <Route exact path='/' component={UserNavigation}/>
          <Route path='/admin' component={Admin}/>
          <Route path='/manager' component={Manager}/>
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