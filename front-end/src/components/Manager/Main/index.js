import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './index.scss';
import TabbedArea from './TabbedArea';
import EntitiesMenu from './EntitiesMenu';

let index = 1;

class Main extends Component {
  state = {
    tabs: [{
      title: 'Title',
      tabKey: '1',
      contentUrl: ''
    }],
    activeKey: '1'
  };

  add = (e) => {
    e.stopPropagation();
    index++;
    const newTab = {
      title: `Title: ${index}`,
      tabKey: `${index}`,
      contentUrl: ''
    };
    
    this.setState({
      tabs: this.state.tabs.concat(newTab),
      activeKey: `${index}`
    });
    
    this.props.history.push('/manager/' + index + '/');
  };

  onTabChange = (activeKey) => {
    this.setState({
      activeKey
    });

    let clickedTab = this.state.tabs.find((tab) => {
      return tab.tabKey === activeKey;
    });

    this.props.history.push('/manager/' + activeKey + '/' + clickedTab.contentUrl);
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

  setTabContentUrl = (url) => {
    let currenTab = this.state.tabs.find((tab) => {
      return tab.tabKey === this.state.activeKey;
    });

    currenTab.contentUrl = url;
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
          <TabbedArea
            className="tabs"
            add={this.add}
            onTabChange={this.onTabChange}
            remove={this.remove}
            activeKey={this.state.activeKey}
            tabs={this.state.tabs}
            setTabContentUrl={this.setTabContentUrl}
          />
        </div>
      </main>
    );
  }
}

export default withRouter(Main);