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

  sendMessage = (channel = '/events', message) => {
    this.client.send(channel, this.headers, JSON.stringify(message));
  }


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

  componentDidUpdate (prevProps) {
    const { currentTab } = this.props;
    const prevCurrentTab = prevProps.currentTab;

    if (currentTab && prevCurrentTab && prevCurrentTab.type !== currentTab.type) {
      if (currentTab.type === 'form') {
        this.sendMessage('/events/open', {
          baseEntityName: currentTab.tabKey,
          baseEntityId: currentTab.form.data.id
        });
      } else if (currentTab.type === 'grid') {
        this.sendMessage('/events/close', {
          baseEntityName: prevCurrentTab.tabKey,
          baseEntityId: prevCurrentTab.form.data.id
        });
      }
    } else if (!currentTab && prevCurrentTab && prevCurrentTab.type === 'form') {
      this.sendMessage('/events/close', {
        baseEntityName: prevCurrentTab.tabKey,
        baseEntityId: prevCurrentTab.form.data.id
      });
    }
  }
}

const mapStateToProps = state => {
  let currentTab = null;

  if (state.tabs.activeKey && state.tabs.tabsArray.length > 0) {
    currentTab = state.tabs.tabsArray.filter(t => t.tabKey === state.tabs.activeKey)[0];
  }

  return {
    tabs: state.tabs,
    currentTab
  };
}

export default connect(mapStateToProps, null)(SocketComponent);