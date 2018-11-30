import React, {Component} from 'react';
import './App.scss';
import {Route} from 'react-router-dom';
import Home from './components/Home';
import AuthService from './components/Login/AuthService';
import Admin from './components/Admin';
import Manager from './components/Manager';
import withAuth from './components/Login/withAuth';
import Header from './components/Header';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faPlus, faSignOutAlt } from '@fortawesome/free-solid-svg-icons';

library.add(faPlus);
library.add(faSignOutAlt);

const Auth = new AuthService();

class App extends Component {
  constructor () {
    super();
    this.handleLogout = this.handleLogout.bind(this);
  }

  handleLogout () {
    Auth.logout();
    this.props.history.replace('/login');
  }

  render () {
    return (
      <div className="app">
        <Header handleLogout={this.handleLogout} userName={this.props.user.sub}/>
        <Route exact path="/" component={Home}/>
        <Route path="/admin" component={Admin}/>
        <Route path="/manager" component={Manager}/>
      </div>
    );
  }
}

export default withAuth(App);
