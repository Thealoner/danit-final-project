import React, { Component } from 'react';
import { openTab } from '../../../actions/tabActions';
import { connect } from 'react-redux';
import TabPane from './TabPane';
import TabContent from './TabContent';
import './index.scss';

class TabContainer extends Component {
  render () {
    let { tabs, openTab, currentTab } = this.props;

    return (
      <div className="tabs">
        {currentTab
          ? <>
            <TabPane tabs={tabs} onSelect={openTab} />
            <TabContent currentTab={currentTab} />
            </>
          : <></>
        }
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  let currentTab = null;

  if (state.tabs.activeKey && state.tabs.tabsArray.length > 0) {
    currentTab = state.tabs.tabsArray.filter(t => t.tabKey === state.tabs.activeKey)[0];
  }

  return {
    tabs: state.tabs,
    currentTab
  };
};

export default connect(mapStateToProps, { openTab })(TabContainer);