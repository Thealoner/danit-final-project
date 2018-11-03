import 'rc-tabs/assets/index.css';
import React, { Component } from 'react';
import './index.scss';
import Tabs, {TabPane} from 'rc-tabs';
import TabContent from 'rc-tabs/lib/TabContent';
import ScrollableInkTabBar from 'rc-tabs/lib/ScrollableInkTabBar';
import {Route, NavLink} from 'react-router-dom';
import Grid from '../Grid';

let index = 1;

class Tab extends Component {
  state = {
    tabs: [{
      title: 'Title',
      content: 'Content',
      tabId: ''
    }],
    activeKey: 'activeKey'
  };

  add = (e) => {
    e.stopPropagation();
    index++;
    const newTab = {
      title: `Title: ${index}`,
      content: `Content: ${index}`
    };
    this.setState({
      tabs: this.state.tabs.concat(newTab),
      activeKey: `ActiveKey: ${index}`,
      tabId: `${index}`
    });
  };

  onTabChange = (activeKey) => {
    this.setState({
      activeKey
    });
  };

  construct () {
    const disabled = true;
    return this.state.tabs.map((t) => {
      return (<TabPane
        tab = {<span>{t.title}
          <NavLink exact to={'/admin/:tabId'} className='tab__link' style={{
            position: 'absolute',
            cursor: 'pointer',
            color: 'black',
            right: 5,
            top: 0
          }}
          onClick={this.remove.bind(this, t.title)}
          >x</NavLink>
        </span>}
        key={t.title}
      >
        <div>
          <Route path="/admin/:tabId/:entityId" component={Grid} />
        </div>
      </TabPane>);
    }).concat([
      <TabPane
        tab={ <NavLink exact to={'/admin/' + this.state.tabs.tabId} className='tab__link' style={{ color: 'black', cursor: 'pointer' }} onClick={this.add}> + Add</NavLink>}
        disabled={disabled}
        key={'__add'}
      />
    ]);
  }

  remove = (title, e) => {
    e.stopPropagation();
    if (this.state.tabs.length === 1) {
      alert('Error. You cannot delete this tab');
      return;
    }
    let foundIndex = 0;
    const after = this.state.tabs.filter((t, i) => {
      if (t.title !== title) {
        return true;
      }
      foundIndex = i;
      return false;
    });

    let activeKey = this.state.activeKey;
    if (activeKey === title) {
      if (foundIndex) {
        foundIndex--;
      }
      activeKey = after[foundIndex].title;
    }
    this.setState({
      tabs: after,
      activeKey
    });
  };

  render () {
    return (
      <div style = {{margin: 20}}>
        <div>
          <Tabs
            renderTabBar={() => (
              <ScrollableInkTabBar/>
            )}
            renderTabContent={() => <TabContent/>}
            activeKey={this.state.activeKey}
            onChange={this.onTabChange}
          >
            {this.construct()}
          </Tabs>
        </div>
      </div>
    );
  }
}

export default Tab;
