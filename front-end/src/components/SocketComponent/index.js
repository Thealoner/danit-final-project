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

  client = null;
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

    const { showEditCollision, hideEditCollision } = this.props;
    
    if (frame.command === 'MESSAGE') {
      try {
        const collisionRecord = JSON.parse(frame.body);

        if (collisionRecord.busy) {
          showEditCollision(collisionRecord);
        } else {
          hideEditCollision(collisionRecord);
        }
      } catch (e) {
        console.log('Error parsing frame.body', e, frame.body);
      }
    }
  }

  connect = () => {
    const { userId } = this.props;
    this.client = Stomp.client('wss://' + window.location.hostname + ':9000/socket'); // use 'wss' protocol on production and 'ws' on dev

    this.client.connect(
      this.headers,
      () => {
        this.client.subscribe('/events/' + userId, (frame) => {
          this.handleIncomingEvent(frame);
        }, this.headers);
      },
      error => {
        console.log('===ERROR===', error);
        setTimeout(
          () => this.connect(),
          5000
        );
      });
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

    this.connect();
  };

  componentDidUpdate (prevProps) {
    const { currentTab, userId, tabs } = this.props;
    const prevCurrentTab = prevProps.currentTab;
    let isPrevTabClosed = false;
    
    if (prevCurrentTab) {
      isPrevTabClosed = !tabs.tabsArray.some(t => t.tabKey === prevCurrentTab.tabKey);
    }

    if (currentTab && prevCurrentTab && prevCurrentTab.tabKey === currentTab.tabKey && prevCurrentTab.type !== currentTab.type) {
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
    } else if (((!currentTab && prevCurrentTab) || isPrevTabClosed) && prevCurrentTab.type === 'form') {
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