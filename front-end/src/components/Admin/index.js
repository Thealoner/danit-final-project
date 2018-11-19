import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './index.scss';
import TabbedArea from './TabbedArea';
import EntitiesMenu from './EntitiesMenu';

let index = 1;

class Admin extends Component {
  state = {
    tabs: [{
      title: 'Новая вкладка',
      tabKey: '1',
      contentUrl: '',
      data: {}
    }],
    activeKey: '1'
  };

  add = (e) => {
    e.stopPropagation();
    index++;
    const newTab = {
      title: 'Новая вкладка',
      tabKey: `${index}`,
      contentUrl: ''
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

  remove = (tabKey, e) => {
    e.stopPropagation();
    if (this.state.tabs.length === 1) {
      alert('Error. You cannot delete this tab');
      return;
    }
    let foundIndex = 0;
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
    this.setState({
      tabs: after,
      activeKey
    });
  };

  setTabTitle = (title) => {
    let currentTab = this.getCurrentTab();
    currentTab.title = title;
    return currentTab.title;
  };

  setTabContentUrl = (url) => {
    let currentTab = this.getCurrentTab();
    currentTab.contentUrl = url;
    return currentTab.contentUrl;
  };

  setTabData = (tabKey, data) => {
    let currentTabIndex = this.state.tabs.findIndex((tab) => {
      return tab.tabKey === tabKey;
    });
    
    this.state.tabs[currentTabIndex].data = data;
    this.forceUpdate();
  }

  getCurrentTab = () => {
    return this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });
  }

  render () {
    return (
      <main className="configurator">
        <div className="configurator__left">
          <EntitiesMenu activeKey={this.state.activeKey} setTabTitle={this.setTabTitle}/>
        </div>
        <div className="configurator__right">
          <TabbedArea
            className="tabs"
            add={this.add}
            onTabChange={this.onTabChange}
            remove={this.remove}
            activeKey={this.state.activeKey}
            tabs={this.state.tabs}
            currentTab={this.getCurrentTab}
            setTabContentUrl={this.setTabContentUrl}
            setTabData={this.setTabData}
          />
        </div>
      </main>
    );
  }
}

export default withRouter(Admin);