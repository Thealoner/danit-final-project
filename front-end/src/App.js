import React, {Component} from 'react';
import PreLoader from './components/PreLoader';
import './App.scss';
import {Route} from 'react-router-dom';
import Home from './components/Home';
import AuthService from './components/Login/AuthService';
import Admin from './components/Admin';
import Manager from './components/Manager';
import withAuth from './components/Login/withAuth';
import Header from './components/Header';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faUserCircle, faPlus, faSignOutAlt } from '@fortawesome/free-solid-svg-icons';

library.add(faUserCircle);
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

  preLoader = React.createRef();

  render () {
    return (
      <div className="app">
        <div className="app__preloader" ref={preLoader => (this.preLoader = preLoader)}>
          <PreLoader/>
        </div>
        <Header handleLogout={this.handleLogout} userName={this.props.user.sub}/>
        <Route exact path="/" component={Home}/>
        <Route path="/admin" component={Admin}/>
        <Route path="/manager" component={Manager}/>
      </div>
    );
  }

  componentDidMount () {
    let self = this;
    setTimeout(function () {
      self.preLoader.style.opacity = '0';
      self.preLoader.style.visibility = 'hidden';
    }, 40);
    setTimeout(function () {
      self.preLoader.style.display = 'none';
    }, 2000);
  }
}

export default withAuth(App);
