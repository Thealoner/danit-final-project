import 'rc-tabs/assets/index.css';
import React, { Component } from 'react';
import './index.scss';
import Tabs, {TabPane} from 'rc-tabs';
import TabContent from 'rc-tabs/lib/TabContent';
import ScrollableInkTabBar from 'rc-tabs/lib/ScrollableInkTabBar';
import {Route, NavLink, withRouter} from 'react-router-dom';
import Grid from '../Grid';
import Record from '../Record';

class Tab extends Component {

  construct () {
    const disabled = true;
    return this.props.tabs.map((t) => {
      return (<TabPane
        tab = {<span>{t.title}
          <NavLink to={'/admin/' + t.tabId} className='tab__link' style={{
            position: 'absolute',
            cursor: 'pointer',
            color: 'black',
            right: 5,
            top: 0
          }}
          onClick={this.props.remove.bind(this, t.tabId)}
          >x</NavLink>
        </span>}
        key={t.tabId}
      >
        <div>
          <Route exact path="/admin/:tabId/:entityType" render={
            (props)=><Grid setActiveModule={this.props.setActiveModule} {...props} />
          }/>
          <Route path="/admin/:tabId/:entityType/:rowId" render={
            (props)=><Record setActiveModule={this.props.setActiveModule} {...props} />
          } />
        </div>
      </TabPane>);
    }).concat([
      <TabPane
        tab={ <NavLink exact to={'/admin/' + this.props.tabs.tabId} className='tab__link' style={{ color: 'black', cursor: 'pointer' }} onClick={this.props.add}> + Add</NavLink>}
        disabled={disabled}
        key={'__add'}
      />
    ]);
  }

  render () {
    return (
      <div style = {{margin: 20}}>
        <div>
          <Tabs
            renderTabBar={() => (
              <ScrollableInkTabBar/>
            )}
            renderTabContent={() => <TabContent/>}
            activeKey={this.props.activeKey}
            onChange={this.props.onTabChange}
          >
            {this.construct()}
          </Tabs>
        </div>
      </div>
    );
  }
}

export default Tab;
