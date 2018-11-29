import React, {Component, Fragment} from 'react';
import AuthService from './AuthService';
import Settings from '../Settings';
import { FadeLoader } from 'react-spinners';
import {connect} from 'react-redux';

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

    preLoader = React.createRef();

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

    render () {
      if (this.state.user) {
        return (
          <Fragment>
            <div className="app__loader-wrapper" ref={preLoader => (this.preLoader = preLoader)}>
              <FadeLoader
                sizeUnit={'px'}
                size={50}
                color={'#999'}
                loading={this.state.loading}
              />
            </div>
            <AuthComponent history={this.props.history} user={this.state.user}/>
          </Fragment>
        );
      } else {
        return null;
      }
    }
  }

  return connect()(AuthWrapped);
}