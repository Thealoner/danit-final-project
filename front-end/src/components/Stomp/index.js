import React, { Component } from 'react';
import Stomp from 'stompjs';
import AuthService from '../../helpers/authService';

class SocketComponent extends Component {
  state = {
    loaded: false
  }

  authService = new AuthService();

  render () {

    if (this.state.loaded) {
      return <div></div>;
    }


    
    return (
      <div>

      </div>
    );
  }

  componentDidMount(){

    this.setState({
      loaded: true
    });

    const headers = {
      'Authorization': this.authService.getToken()
    };

    let client = Stomp.client('ws://localhost:9000/socket')
    client.connect(headers, (frame) => {
      client.subscribe('/events/put', (frame) => {

      }, headers)
    })
  }
}

export default SocketComponent;