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
    $('.rc-tabs').addClass('tabs_area');
    $('.rc-tabs-bar').addClass('tabs_area__header');
    $('.rc-tabs-content').addClass('tabs_area__content');
    $('.rc-tabs-tab:not(.rc-tabs-tab:last-child)').addClass('tabs_area__tab');
    $('.rc-tabs-nav-container, .rc-tabs-nav-wrap, .rc-tabs-nav-scroll, .rc-tabs-nav').css('height', '100%');
    $('.rc-tabs-nav').removeClass('rc-tabs-nav-animated');
    $('.rc-tabs-ink-bar').removeClass('rc-tabs-ink-bar-animated');
  }

  construct () {
    const disabled = true;

    return this.props.tabs.map((t) => {
      return (
        <TabPane
          key={t.tabKey}
          tab = {<span className="tabs_area__title-wrapper">{t.title}
            <NavLink to={'/admin/' + t.tabKey} className='tabs_area__close'
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
      <TabPane key={'__add'} disabled={disabled} tab={<span className='tabs_area__link' onClick={this.props.add}>
            + Add
      </span>}/>
    ]);
  }

  render () {
    return (
      <Tabs
        renderTabBar={() => <ScrollableInkTabBar/>}
        renderTabContent={() => <TabContent animated={false}/>}
        activeKey={this.props.activeKey}
        onChange={this.props.onTabChange}>
        {this.construct()}
      </Tabs>
    );
  }
}

export default TabbedArea;
