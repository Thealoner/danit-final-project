import React, { Component } from 'react';
import { openTab } from '../../../actions/tabActions';
import { connect } from 'react-redux';
import TabPane from './TabPane';
import TabContent from './TabContent';

class TabContainer extends Component {
  render () {
    let {tabs, currentTab, openTab} = this.props;
    if (tabs.activeKey && tabs.tabsArray[tabs.activeKey] && tabs.tabsArray[tabs.activeKey].status === 'loading') {
      return (
        <div className="tab-container">loading...</div>
      );
    }

    return (
      <div className="tab-container">
        <TabPane
          tabs={tabs}
          onSelect={openTab}
        />
        <TabContent />
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    tabs: state.tabs
  };
};

const mapDispatchToProps = dispatch => {
  return {
    openTab: tabKey => {
      dispatch(openTab(tabKey));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TabContainer);