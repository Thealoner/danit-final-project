import React, { Component } from 'react';
import { NavLink, withRouter } from 'react-router-dom';
import './index.scss';
import Tab from './Tab';
import EntitiesMenu from './EntitiesMenu';

let index = 1;

class Main extends Component {
  state = {
    tabs: [{
      title: 'Title',
      content: 'Content',
      tabId: '1',
      activeModule: ''
    }],
    activeKey: '1'
  };
  

  add = (e) => {
    e.stopPropagation();
    index++;
    const newTab = {
      title: `Title: ${index}`,
      content: `Content: ${index}`,
      tabId: `${index}`,
      activeModule: ''
    };
    
    this.setState({
      tabs: this.state.tabs.concat(newTab),
      activeKey: `${index}`
    });
    // debugger;
    // let activeKey = this.state.activeKey;
    // this.props.history.push('/admin/'  + activeKey + '/' + this.state.tabs.find(tab => tab.tabId === activeKey).activeModule);
  };

  onTabChange = (activeKey) => {
    this.setState({
      activeKey
    });

    let clickedTab = this.state.tabs.find((tab) => {
      return tab.tabId === activeKey;
    })
    this.props.history.push('/admin/'  + activeKey + '/' + clickedTab.activeModule);
  };

  remove = (tabId, e) => {
    e.stopPropagation();
    if (this.state.tabs.length === 1) {
      alert('Error. You cannot delete this tab');
      return;
    }
    let foundIndex = 0;
    const after = this.state.tabs.filter((t, i) => {
      if (t.tabId !== tabId) {
        return true;
      }
      foundIndex = i;
      return false;
    });

    let activeKey = this.state.activeKey;
    if (activeKey === tabId) {
      if (foundIndex) {
        foundIndex--;
      }
      activeKey = after[foundIndex].tabId;
    }
    this.setState({
      tabs: after,
      activeKey
    });
  };

  setActiveModule = (module) => {
    let currenTab = this.state.tabs.find((tab) => {
      return tab.tabId === this.state.activeKey;
    });

    currenTab.activeModule = module;
  }

  render () {
    return (

      <main className="main">
        <div className="main__left">
          <ul className="main__menu">
            <EntitiesMenu activeKey={this.state.activeKey} />
          </ul>
        </div>
        <div className="main__right">
          <Tab
            className="tabs"
            add={this.add}
            onTabChange={this.onTabChange}
            remove={this.remove}
            activeKey={this.state.activeKey}
            tabs={this.state.tabs}
            setActiveModule={this.setActiveModule}
          />
        </div>
      </main>
    );
  }
}

export default withRouter(Main);