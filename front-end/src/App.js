import React, {Component} from 'react';
import PreLoader from './components/PreLoader';
import './App.scss';
import {Route} from 'react-router-dom';
import Home from './components/Home';
import AuthService from './components/Login/AuthService';
import avatar from './img/avatar.png';
import Admin from './components/Admin';
import Manager from './components/Manager';
import withAuth from './components/Login/withAuth';

const Auth = new AuthService();

class App extends Component {
  handleLogout () {
    Auth.logout();
    this.props.history.replace('/login');
  }

  render () {
    return (
      <div className="app">
        <div className="app__preloader" ref="preloader"><PreLoader/></div>
        <div className="app__user">
          <div className="app__user-wrapper">
            <img className="app__logo" src={avatar} alt=""/>
            <span>{this.props.user.sub}</span>
          </div>
          <button type="button" className="app__btn-logout" onClick={this.handleLogout.bind(this)}>Logout</button>
        </div>
        <Route exact path="/" component={Home} />
        <Route path="/admin" component={Admin} />
        <Route path="/manager" component={Manager} />
      </div>
    );
  }

  componentWillMount () {
    if (!Auth.loggedIn()) {
      this.props.history.replace('/login');
    } else {
      try {
        const profile = Auth.getProfile();
        this.setState({
          user: profile
        });
      } catch (err) {
        Auth.logout();
        this.props.history.replace('/login');
      }
    }
  }

  componentDidMount () {
    let self = this;
    setTimeout(function () {
      self.refs.preloader.style.opacity = '0';
      self.refs.preloader.style.visibility = 'hidden';
    }, 2000);
    setTimeout(function () {
      self.refs.preloader.style.display = 'none';
    }, 3000);
  }
}

export default withAuth(App);
