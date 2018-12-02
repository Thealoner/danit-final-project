import React, {Component, Fragment} from 'react';
import AuthService from './AuthService';
import Settings from '../Settings';
import {FadeLoader} from 'react-spinners';
import {connect} from 'react-redux';

export default function withAuth(AuthComponent) {
  const Auth = new AuthService(Settings.apiServerUrl);

  class AuthWrapped extends Component {
    constructor() {
      super();
      this.state = {
        user: null
      };
    }

    UNSAFE_componentWillMount() {
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
          <Fragment>
            {this.props.loading
              ? <div className="app__loader-wrapper">
                <FadeLoader sizeUnit={'px'} size={50} color={'#000'} loading={this.props.loading}/>
              </div>
              : <AuthComponent history={this.props.history} user={this.state.user}/>}
          </Fragment>
        );
      } else {
        return null;
      }
    }
  }

  const mapStateToProps = (store) => {
    return {
      loading: store.loading
    };
  };

  return connect(mapStateToProps)(AuthWrapped);
}