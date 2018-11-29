import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './index.scss';
import TabbedArea from './TabbedArea';
import EntitiesMenu from './EntitiesMenu';

let index = 0;

class Admin extends Component {
  state = {
    tabs: [],
    activeKey: '0'
  };

  setRecordData = (data, edited) => {
    let tabs = this.state.tabs;

    let currentTabIndex = tabs.findIndex((tab) => {
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
    let currentTab = this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });

    return currentTab.recordData;
  };

  addTab = (e, url = '', title = 'Новая вкладка') => {
    e.stopPropagation();
    index++;
    const newTab = {
      title: title,
      tabKey: `${index}`,
      contentUrl: url
    };
    
    this.setState({
      tabs: this.state.tabs.concat(newTab),
      activeKey: `${index}`
    });
    
    this.props.history.push('/admin/' + index + '/');
  };

  onTabChange = (activeKey) => {
    this.setState({
      activeKey
    });

    let clickedTab = this.state.tabs.find((tab) => {
      return tab.tabKey === activeKey;
    });

    this.props.history.push('/admin/' + activeKey + '/' + clickedTab.contentUrl);
  };

  closeTab = (tabKey, e) => {
    e.stopPropagation();
    if (this.state.tabs.length === 1) {
      alert('Error. You cannot delete this tab');
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
    let currentTab = this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });

    currentTab.title = title;
    return currentTab.title;
  };

  setTabContentUrl = (url) => {
    let currentTab = this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });

    currentTab.contentUrl = url;
    return currentTab.contentUrl;
  };

  getCurrentTab = () => {
    return this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });
  }

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