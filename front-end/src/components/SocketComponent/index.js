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
    // frame = {
    //   "id":1,
    //   "userId":1,
    //   "baseEntityName":"services",
    //   "baseEntityId":1,
    //   "lastModifiedDate":"2019-02-02 22-28-49",
    //   "busy":null
    // }
    
    if (frame.command === 'MESSAGE') {
      try {
        const collisionRecord = JSON.parse(frame.body);
        showEditCollision(collisionRecord);
      } catch (e) {
        console.log('Error parsing frame.body');
      }

      // if ('/events/close') {
      //   hideEditCollision(collisionRecords);
      // }
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
    const { user } = this.props;

    this.setState({
      loaded: true
    });

    // TODO: USER ID
    const userId = 1;

    this.client.connect(this.headers, (frame) => {
      this.client.subscribe('/events/' + userId, (frame) => {
        this.handleIncomingEvent(frame);
      }, this.headers);
    });
  };

  componentDidUpdate (prevProps) {
    const { currentTab, activeKey } = this.props;
    const prevCurrentTab = prevProps.currentTab;
    
    // TODO: USER ID
    const userId = 1;

    if (currentTab && prevCurrentTab && prevCurrentTab.type !== currentTab.type) {
      if (currentTab.type === 'form') {
        this.sendMessage('/api/tab/open', {
          userId: userId,
          baseEntityName: currentTab.tabKey,
          baseEntityId: currentTab.form.data.id
        });
      } else if (currentTab.type === 'grid') {
        this.sendMessage('/api/tab/close', {
          userId: userId,
          baseEntityName: prevCurrentTab.tabKey,
          baseEntityId: prevCurrentTab.form.data.id
        });
      }
    } else if (!currentTab && prevCurrentTab && prevCurrentTab.type === 'form') {
      this.sendMessage('/api/tab/close', {
        userId: userId,
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
    user: state.user,
    tabs: state.tabs,
    activeKey: state.activeKey,
    currentTab
  };
};

const mapDispatchToProps = dispatch => {
  return {
    showEditCollision: (collisionRecord) => {
      dispatch(showEditCollision(collisionRecord));
    },
    hideEditCollision: (collisionRecord) => {
      dispatch(hideEditCollision(collisionRecord));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SocketComponent);