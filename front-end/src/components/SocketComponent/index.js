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
    // {
    //   "creationDate":"2019-02-03 22-07-11",
    //   "id":9,
    //   "userId":2,
    //   "baseEntityName":"users",
    //   "baseEntityId":1,
    //   "tabOwnerName":"Sarah",
    //   "busy":true,
    //   "message":"tab is already opened by user - Sarah"
    // }
    
    if (frame.command === 'MESSAGE') {
      try {
        const collisionRecord = JSON.parse(frame.body);

        if (collisionRecord.busy) {
          showEditCollision(collisionRecord);
        } else {
          hideEditCollision(collisionRecord);
        }
      } catch (e) {
        console.log('Error parsing frame.body');
      }
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
    const { userId } = this.props;

    this.setState({
      loaded: true
    });

    this.client.connect(this.headers, (frame) => {
      this.client.subscribe('/events/' + userId, (frame) => {
        this.handleIncomingEvent(frame);
      }, this.headers);
    });
  };

  componentDidUpdate (prevProps) {
    const { currentTab, userId } = this.props;
    const prevCurrentTab = prevProps.currentTab;
    
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
    userId: state.user.profile.data.id,
    tabs: state.tabs,
    activeKey: state.activeKey,
    currentTab
  };
};

export default connect(mapStateToProps, { showEditCollision, hideEditCollision })(SocketComponent);