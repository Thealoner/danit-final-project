import React, { Component } from 'react';
import SockJsClient from 'react-stomp';
import AuthService from '../../helpers/authService';

class Stomp extends Component {
  state = {
    loaded: false
  }
  sendMessage = (msg) => {
    this.clientRef.sendMessage('/topics/all', msg);
  }

  authService = new AuthService();

  render () {
    const headers = {
      'Authorization': 'Bearer ' + this.authService.getToken()
    };

    if (this.state.loaded) {
      return <div></div>;
    }
    
    return (
      <div>
        <SockJsClient
          url='http://localhost:9000/socket'
          topics={['/events/put']}
          headers={headers}
          onMessage={(msg) => { console.log(msg); }}
          ref={(client) => { this.clientRef = client; }} />
      </div>
    );
  }

  componentDidMount () {
    this.setState({
      loaded: true
    });
  }
}

export default Stomp;