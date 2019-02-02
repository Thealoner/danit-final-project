import React, { Component } from 'react';
import Stomp from 'stompjs';
import AuthService from '../../helpers/authService';
import { connect } from 'react-redux';
import {
  showEditCollision,
  hideEditCollision
} from '../../actions/tabActions';

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

  handleIncomingEvent = frame => {
    debugger;

    const collisionRecords = [
      {
        entity: 'pakets',
        id: 2
      },
      {
        entity: 'clients',
        id: 4
      }
    ];

    if ('/events/open') {
      showEditCollision(collisionRecords);
    }

    if ('/events/close') {
      hideEditCollision(collisionRecords);
    }
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
        this.handleIncomingEvent(frame);
      }, this.headers);
    });
  };

  componentDidUpdate (prevProps) {
    const { currentTab, activeKey } = this.props;
    const prevCurrentTab = prevProps.currentTab;

    if (currentTab && prevCurrentTab && prevCurrentTab.type !== currentTab.type) {
      if (currentTab.type === 'form') {
        this.sendMessage('/api/tab/open', {
          baseEntityName: currentTab.tabKey,
          baseEntityId: currentTab.form.data.id
        });
      } else if (currentTab.type === 'grid') {
        this.sendMessage('/api/tab/close', {
          baseEntityName: prevCurrentTab.tabKey,
          baseEntityId: prevCurrentTab.form.data.id
        });
      }
    } else if (!currentTab && prevCurrentTab && prevCurrentTab.type === 'form') {
      this.sendMessage('/api/tab/close', {
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
    activeKey: state.activeKey,
    currentTab
  };
}

const mapDispatchToProps = dispatch => {
  return {
    showEditCollision: (payload) => {
      dispatch(showEditCollision(payload));
    },
    hideEditCollision: (payload) => {
      dispatch(hideEditCollision(payload));
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(SocketComponent);