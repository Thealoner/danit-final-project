import React, { Component } from 'react';
import Stomp from 'stompjs';
import AuthService from '../../helpers/authService';
import { connect } from 'react-redux';

class SocketComponent extends Component {
  state = {
    loaded: false
  }

  client = Stomp.client('ws://localhost:9000/socket');
  authService = new AuthService();

  headers = {
    'Authorization': this.authService.getToken()
  };


  render () {
    if (this.state.loaded) {
      return <div></div>;
    }

    return (
      <div></div>
    );
  };

  componentDidMount () {
    this.setState({
      loaded: true
    });

    
    this.client.connect(this.headers, (frame) => {
      this.client.subscribe('/events', (frame) => {
      }, this.headers);
    });
  };

  componentDidUpdate () {
    const { lastClosedTab } = this.props;
    
    if (lastClosedTab) {
      this.client.send('/events', this.headers, JSON.stringify(lastClosedTab));
      // Example:
      // lastClosedTab: {
      //     entityId: 3,
      //     entity: 'packets'
      //   }
    }
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    lastClosedTab: state.tabs.lastClosedTab
  };
}

export default connect(mapStateToProps, null)(SocketComponent);