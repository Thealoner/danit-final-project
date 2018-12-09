import React, { Component } from 'react';
import './index.scss';
import Tabs, {TabPane} from 'rc-tabs';
import 'rc-tabs/assets/index.css';
import TabContent from 'rc-tabs/lib/TabContent';
import ScrollableInkTabBar from 'rc-tabs/lib/ScrollableInkTabBar';
import { Route, NavLink } from 'react-router-dom';
import Grid from '../../Grid';
import Record from '../Record';

class TabbedArea extends Component {
  construct () {
    const disabled = true;

    return this.props.tabs.map((t) => {
      return (
        <TabPane
          key={t.tabKey}
          tab = {<span>{t.title}
            <NavLink
              to={'/manager/' + t.tabKey}
              className='tabbedarea__close'
              onClick={this.props.remove.bind(this, t.tabKey)}
            >x</NavLink>
          </span>}
        >
          <div>
            <Route exact path="/manager/:tabKey/:entityType" render={
              (props) => <Grid setTabContentUrl={this.props.setTabContentUrl} {...props} />
            } />
            <Route path="/manager/:tabKey/:entityType/:rowId" render={
              (props) => <Record setTabContentUrl={this.props.setTabContentUrl} {...props} />
            } />
          </div>
        </TabPane>
      );
    }).concat([
      <TabPane
        key={'__add'}
        disabled={disabled}
        tab={
          <span
            className='tabbedarea__link'
            onClick={this.props.add}>
            + Add
          </span>
        }
      />
    ]);
  }

  render () {
    return (
      <div>
        <Tabs
          renderTabBar={() => <ScrollableInkTabBar/>}
          renderTabContent={() => <TabContent/>}
          activeKey={this.props.activeKey}
          onChange={this.props.onTabChange}
        >
          {this.construct()}
        </Tabs>
      </div>
    );
  }
}

export default TabbedArea;
