import React, {Component} from 'react';
import {openTab} from '../../../actions/tabActions';
import {connect} from 'react-redux';
import {Loader} from 'semantic-ui-react';
import TabPane from './TabPane';
import TabContent from './TabContent';
import './index.scss';
import logo from './logo.svg'

class TabContainer extends Component {
  render () {
    let {tabs, openTab, currentTab} = this.props;

    if (!currentTab) {
      return <div className="tabs__logo-wrapper"><img src={logo} alt=""/></div>;
    }

    if (tabs.activeKey && tabs.tabsArray[tabs.activeKey] && tabs.tabsArray[tabs.activeKey].status === 'loading') {
      return (
        <div className="tabs">
          <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>
        </div>
      );
    }

    return (
      <div className="tabs">
        <TabPane tabs={tabs} onSelect={openTab} />
        <TabContent currentTab={currentTab} />
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

const mapDispatchToProps = (dispatch) => ({
  openTab: tabKey => {
    dispatch(openTab(tabKey));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(TabContainer);