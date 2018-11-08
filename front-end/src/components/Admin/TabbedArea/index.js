import React, {Component} from 'react';
import './index.scss';
import Tabs, {TabPane} from 'rc-tabs';
import 'rc-tabs/assets/index.css';
import TabContent from 'rc-tabs/lib/TabContent';
import ScrollableInkTabBar from 'rc-tabs/lib/ScrollableInkTabBar';
import { Route, NavLink } from 'react-router-dom';
import Grid from '../Grid';
import Record from '../Record';
import $ from 'jquery';

class TabbedArea extends Component {
  componentDidMount () {
    $('.rc-tabs-nav').removeClass('rc-tabs-nav-animated');
    $('.rc-tabs-ink-bar').removeClass('rc-tabs-ink-bar-animated');
  }

  construct () {
    const disabled = true;

    return this.props.tabs.map((t) => {
      return (
        <TabPane
          key={t.tabKey}
          tab = {<span className="rc-tabs__title-wrapper">{t.title}
            <NavLink to={'/admin/' + t.tabKey} className='rc-tabs__close'
              onClick={this.props.remove.bind(this, t.tabKey)}>x</NavLink>
          </span>}>
          <Route exact path="/admin/:tabKey/:entityType" render={
            (props) => <Grid setTabContentUrl={this.props.setTabContentUrl} {...props} />
          } />
          <Route path="/admin/:tabKey/:entityType/:rowId" render={
            (props) => <Record setTabContentUrl={this.props.setTabContentUrl} {...props} />} />
        </TabPane>
      );
    }).concat([
      <TabPane key={'__add'} disabled={disabled} tab={<span className='rc-tabs__link' onClick={this.props.add}>
            +
      </span>}/>
    ]);
  }

  render () {
    return (
      <Tabs
        renderTabBar={() => <ScrollableInkTabBar/>}
        renderTabContent={() => <TabContent animated={true}/>}
        activeKey={this.props.activeKey}
        onChange={this.props.onTabChange}>
        {this.construct()}
      </Tabs>
    );
  }
}

export default TabbedArea;
