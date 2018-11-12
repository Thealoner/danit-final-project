import React, {Component} from 'react';
import PreLoader from './components/PreLoader';
import './App.scss';
import {Link, Route} from 'react-router-dom';
import Home from './components/Home';
import AuthService from './components/Login/AuthService';
import avatar from './img/avatar.png';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Admin from './components/Admin';
import Manager from './components/Manager';
import withAuth from './components/Login/withAuth';
import {IntlProvider} from 'react-intl';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import messages from './messages';

const Auth = new AuthService();

class App extends Component {
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

  render () {
    const {lang} = this.props;
    return (
      <IntlProvider locale={lang} messages={messages[lang]}>
        <div className="app">
          <div className="app__preloader" ref="preloader"><PreLoader/></div>
          <header className="app__header">
            <Link to="/" className="app__logo">LOGO</Link>
            <div className="app__user">
              <img className="app__avatar" src={avatar} alt="" title={this.props.user.sub}/>
              <button type="button" className="app__btn-logout" onClick={this.handleLogout.bind(this)}>Logout</button>
            </div>
          </header>
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
      self.refs.preloader.style.opacity = '0';
      self.refs.preloader.style.visibility = 'hidden';
    }, 2000);
    setTimeout(function () {
      self.refs.preloader.style.display = 'none';
    }, 3000);
  }
}

App.propTypes = {
  lang: PropTypes.string.isRequired
};

function mapStateToProps (state) {
  return {
    lang: state.locale.lang
  };
}

export default connect(mapStateToProps)(withAuth(App));
