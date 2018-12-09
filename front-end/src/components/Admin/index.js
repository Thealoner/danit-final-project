import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './index.scss';
import TabbedArea from './TabbedArea';
import EntitiesMenu from './EntitiesMenu';

class Admin extends Component {
  state = {
    tabs: [],
    activeKey: null
  };

  setRecordData = (data, edited) => {
    const tabs = this.state.tabs;

    const currentTabIndex = tabs.findIndex((tab) => {
      return tab.tabKey === this.state.activeKey;
    });

    this.setState(prevState => ({
      tabs: [
        ...prevState.tabs.filter((tab, index) => index !== currentTabIndex),
        {
          ...prevState.tabs[currentTabIndex],
          recordData: data,
          recordDataEdited: edited
        }
      ]
    }));
  };

  getRecordData = () => {
    const currentTab = this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });

    return currentTab.recordData;
  };

  addTab = (e, url = '', entityId, entityName) => {
    e.stopPropagation();

    const entityTab = this.state.tabs.find((tab) => {
      return tab.tabKey === entityId;
    });
    
    if (entityTab) {
      this.setState({
        activeKey: entityTab
      });

      this.props.history.push('/admin/' + entityTab + '/');
    } else {
      const newTab = {
        title: entityName,
        tabKey: entityId,
        contentUrl: url
      };

      this.setState({
        tabs: this.state.tabs.concat(newTab),
        activeKey: entityId
      });

      this.props.history.push(url);
    }
  };

  onTabChange = (activeKey) => {
    this.setState({
      activeKey
    });

    const clickedTab = this.state.tabs.find((tab) => {
      return tab.tabKey === activeKey;
    });

    this.props.history.push('/admin/' + activeKey + '/' + clickedTab.contentUrl);
  };

  closeTab = (tabKey, e) => {
    e.stopPropagation();
    if (this.state.tabs.length === 1) {
      return;
    }
    let foundIndex = -1;
    const after = this.state.tabs.filter((t, i) => {
      if (t.tabKey !== tabKey) {
        return true;
      }
      foundIndex = i;
      return false;
    });

    let activeKey = this.state.activeKey;
    if (activeKey === tabKey) {
      if (foundIndex) {
        foundIndex--;
      }
      activeKey = after[foundIndex].tabKey;
    }
    
    this.props.history.push('/admin/' + activeKey + '/' + after[foundIndex].contentUrl);
    this.setState({
      tabs: after,
      activeKey
    });
  };

  setTabTitle = (title) => {
    const currentTabIndex = this.state.tabs.findIndex((tab) => {
      return tab.tabKey === this.state.activeKey;
    });

    this.setState(prevState => ({
      tabs: [
        ...prevState.tabs.filter((tab, index) => index !== currentTabIndex),
        {
          ...prevState.tabs[currentTabIndex],
          title: title
        }
      ]
    }));
  };

  setTabContentUrl = (url) => {
    const tabs = this.state.tabs;

    const currentTabIndex = tabs.findIndex((tab) => {
      return tab.tabKey === this.state.activeKey;
    });

    this.setState(prevState => ({
      tabs: [
        ...prevState.tabs.filter((tab, index) => index !== currentTabIndex),
        {
          ...prevState.tabs[currentTabIndex],
          contentUrl: url
        }
      ]
    }));
  };

  getCurrentTab = () => {
    return this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });
  };

  render () {
    return (
      <main className="configurator">
        <div className="configurator__left">
          <EntitiesMenu
            activeKey={this.state.activeKey}
            addTab={this.addTab}
            setTabTitle={this.setTabTitle}
          />
        </div>
        <div className="configurator__right">
          <TabbedArea
            className="tabs"
            addTab={this.addTab}
            onTabChange={this.onTabChange}
            closeTab={this.closeTab}
            activeKey={this.state.activeKey}
            currentTab={this.getCurrentTab()}
            tabs={this.state.tabs}
            setTabContentUrl={this.setTabContentUrl}
            getRecordData={this.getRecordData}
            setRecordData={this.setRecordData}
          />
        </div>
      </main>
    );
  }
}

export default withRouter(Admin);