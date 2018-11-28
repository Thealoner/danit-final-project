import React, {Component, Fragment} from 'react';
import './index.scss';
import Tabs, {TabPane} from 'rc-tabs';
import 'rc-tabs/assets/index.css';
import TabContent from 'rc-tabs/lib/TabContent';
import ScrollableInkTabBar from 'rc-tabs/lib/ScrollableInkTabBar';
import { Route, NavLink } from 'react-router-dom';
import Grid from '../Grid';
import Record from '../Record';

class TabbedArea extends Component {
  componentDidUpdate () {
    let tabs = document.getElementsByClassName('rc-tabs-tab');
    let tabActive = document.querySelector('.rc-tabs-tab-active');

    for (let i = 0; i < tabs.length; i++) {
      tabs[i].style.borderBottomRightRadius = '0';
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
          <NavLink to={'/admin/' + t.tabKey}
            className={`rc-tabs__close-btn${(this.props.tabs.length === 1) ? ' rc-tabs__close-btn--disabled' : ''}`}
            onClick={this.props.remove.bind(this, t.tabKey)}/></Fragment>}>
        <Route exact path="/admin/:tabKey/:entityType" render={
          (props) => <Grid
            setTabContentUrl={this.props.setTabContentUrl}
            getRecordData={this.props.getRecordData}
            setRecordData={this.props.setRecordData}
            currentTab={this.props.currentTab}
            {...props}
          />
        }/>
        <Route path="/admin/:tabKey/:entityType/:mode/:rowId?" render={
          (props) => <Record
            setTabContentUrl={this.props.setTabContentUrl}
            getRecordData={this.props.getRecordData}
            setRecordData={this.props.setRecordData}
            currentTab={this.props.currentTab}
            {...props}
          />
        }/>
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
