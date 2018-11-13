import React, { Component } from 'react';
import AuthService from './AuthService';
import Settings from '../Settings';
import {connect} from 'react-redux';

function mapStateToProps (state) {
  return {
    lang: state.locale.lang
  };
}

export default function withAuth (AuthComponent) {
  const Auth = new AuthService(Settings.apiServerUrl);

  class AuthWrapped extends Component {
    constructor () {
      super();
      this.state = {
        user: null
      };
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
      if (this.state.user) {
        return (
          <AuthComponent history={this.props.history} user={this.state.user} lang={this.props.lang}/>
        );
      } else {
        return null;
      }
    }
  }

  return connect(mapStateToProps)(AuthWrapped);
}