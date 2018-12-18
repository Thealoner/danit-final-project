import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './index.scss';
import TabbedArea from './TabbedArea';
import EntitiesMenu from './EntitiesMenu';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

let index = 1;

class Admin extends Component {
  state = {
    tabs: [{
      title: 'Новая вкладка',
      tabKey: '1',
      contentUrl: ''
    }],
    activeKey: '1'
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

    const clickedTab = this.state.tabs.find((tab) => {
      return tab.tabKey === activeKey;
    });

    this.props.history.push('/admin/' + activeKey + '/' + clickedTab.contentUrl);
  };

  remove = (tabKey, e) => {
    e.stopPropagation();
    if (this.state.tabs.length === 1) {
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
    const currentTab = this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });

    currentTab.contentUrl = url;
    return currentTab.contentUrl;
  };

  getCurrentTab = () => {
    return this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });
  };

  configurator = React.createRef();

  render () {
    return (
      <main className="configurator" ref={el => this.configurator = el}>
        <div className="configurator__left">
          <div className="configurator__close-panel">
            <FontAwesomeIcon icon="angle-right" size="1x" onClick={() => this.hideConfiguratorMenu()}/>
          </div>
          <div className="configurator__menu-wrapper">
            <EntitiesMenu activeKey={this.state.activeKey} setTabTitle={this.setTabTitle}/>
          </div>
        </div>
        <div className="configurator__right">
          <TabbedArea
            className="tabs"
            add={this.add}
            onTabChange={this.onTabChange}
            remove={this.remove}
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

  hideConfiguratorMenu = () => this.configurator.classList.toggle('configurator__menu-hide');
}

export default withRouter(Admin);