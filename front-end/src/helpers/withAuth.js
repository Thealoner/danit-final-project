import React, {Component} from 'react';
import AuthService from './authService';
import Settings from '../components/Settings/index';
import { Loader } from 'semantic-ui-react';

export default function withAuth (AuthComponent) {
  const Auth = new AuthService(Settings.apiServerUrl);

  class AuthWrapped extends Component {
    constructor (props) {
      super(props);
      this.state = {
        user: null
      };
    }

    render () {
      if (this.state.user) {
        return (
          <AuthComponent history={this.props.history} user={this.state.user}/>
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
  }

  return AuthWrapped;
}