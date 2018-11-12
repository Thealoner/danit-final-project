import React, {Component} from 'react';
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
    let tabsNav = $('.rc-tabs-nav');

    tabsNav.removeClass('rc-tabs-nav-animated');
    $('.rc-tabs-ink-bar').removeClass('rc-tabs-ink-bar-animated');

    tabsNav.on('click', function (e) {
      $('.rc-tabs-tab').css('border-bottom-right-radius', '0');

      if ($(e.target).hasClass('rc-tabs__close-btn')) {
        $(e.target).closest('.rc-tabs-tab').prev().css('border-bottom-right-radius', '0');
      } else {
        $(e.target).closest('.rc-tabs-tab').prev().css('border-bottom-right-radius', '10px');
      }

      setTimeout(function () {
        $('.rc-tabs-tab-active').prev().css('border-bottom-right-radius', '10px');
      }, 40);
    });
  }

  construct () {
    const disabled = true;

    return this.props.tabs.map((t) => {
      return <TabPane
        key={t.tabKey}
        tab={<span className="rc-tabs__title-wrapper">{t.title}
          <NavLink to={'/manager/' + t.tabKey} className='rc-tabs__close-btn'
            onClick={this.props.remove.bind(this, t.tabKey)}/>
        </span>}>
        <Route exact path="/manager/:tabKey/:entityType" render={
          (props) => <Grid setTabContentUrl={this.props.setTabContentUrl} {...props} />
        }/>
        <Route path="/manager/:tabKey/:entityType/:rowId" render={
          (props) => <Record setTabContentUrl={this.props.setTabContentUrl} {...props} />}/>
      </TabPane>;
    }).concat([
      <TabPane key={'__add'} disabled={disabled} tab={<span className='rc-tabs__add-btn' onClick={this.props.add}/>}/>
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
