import React, {Component} from 'react';
import PreLoader from './components/PreLoader';
import './App.scss';
import {Route} from 'react-router-dom';
import Home from './components/Home';
import AuthService from './components/Login/AuthService';
import Admin from './components/Admin';
import Manager from './components/Manager';
import withAuth from './components/Login/withAuth';
import {IntlProvider} from 'react-intl';
import PropTypes from 'prop-types';
import messages from './messages';
import Header from './components/Header';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faUserCircle } from '@fortawesome/free-solid-svg-icons';

library.add(faUserCircle);

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

  preLoader = React.createRef();

  render () {
    const {lang} = this.props;
    return (
      <IntlProvider locale={lang} messages={messages[lang]}>
        <div className="app">
          <div className="app__preloader" ref={preLoader => (this.preLoader = preLoader)}><PreLoader/></div>
          <Header handleLogout={this.handleLogout} userName={this.props.user.sub}/>
          <Route exact path="/" component={Home}/>
          <Route path="/admin" component={Admin}/>
          <Route path="/manager" component={Manager}/>
        </div>
      </IntlProvider>
    );
  }

  componentDidMount () {
    let self = this;
    setTimeout(function () {
      self.preLoader.style.opacity = '0';
      self.preLoader.style.visibility = 'hidden';
    }, 2000);
    setTimeout(function () {
      self.preLoader.style.display = 'none';
    }, 3000);
  }
}

App.propTypes = {
  lang: PropTypes.string.isRequired
};

export default withAuth(App);
