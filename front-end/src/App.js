import React, {Component, Fragment} from 'react';
import PreLoader from './components/PreLoader';
import './App.scss';
import {Route} from 'react-router-dom';
import Admin from './components/Admin';
import Manager from './components/Manager';
import Home from './components/Home';
import Login from './components/Login';
import Registration from './components/Login/Registration';
import Forgot from './components/Login/Forgot';

class App extends Component {
  render () {
    return (
      <Fragment>
        <div className="preloader" ref="preloader"><PreLoader/></div>
        <div className="app">
          <Route exact path="/" component={Home} />
          <Route path="/login" component={Login} />
          <Route path="/forgot-password" component={Forgot} />
          <Route path="/registration" component={Registration} />
          <Route path="/admin" component={Admin} />
          <Route path="/manager" component={Manager} />
        </div>
      </Fragment>
    );
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

export default App;
