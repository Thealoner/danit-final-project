import React, {Component, Fragment} from 'react';
import Tabs, {TabPane} from 'rc-tabs';
import 'rc-tabs/assets/index.css';
import TabContent from 'rc-tabs/lib/TabContent';
import ScrollableInkTabBar from 'rc-tabs/lib/ScrollableInkTabBar';
import { Route, NavLink } from 'react-router-dom';
import Grid from '../Grid';
import Record from '../Record';

class TabbedArea extends Component {
  componentDidUpdate () {
    let tab = document.querySelectorAll('.rc-tabs-tab');
    let tabActive = document.querySelector('.rc-tabs-tab-active');

    for (let i = 0; i < tab.length; i++) {
      tab[i].style.borderBottomRightRadius = '0';
    }

    if (tabActive.previousElementSibling) {
      tabActive.previousElementSibling.style.borderBottomRightRadius = '10px';
    }
  }

  construct () {
    const disabled = true;

    return this.props.tabs.map((t) => {
      return <TabPane
        key={t.tabKey}
        tab={<Fragment><span className="rc-tabs__title-wrapper" title={t.title}>{t.title}</span>
          <NavLink to={'/manager/' + t.tabKey}
            className='rc-tabs__close-btn'
            onClick={this.props.remove.bind(this, t.tabKey)}/></Fragment>}>
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
