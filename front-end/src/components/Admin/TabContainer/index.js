import React, { Component } from 'react';
import { openTab, setTabContent } from '../../../actions/tabActions';
import { connect } from 'react-redux';
import ajaxRequest from '../../../helpers/ajaxRequest';
import TabPane from './TabPane';
import TabContent from './TabContent';

class TabContainer extends Component {
  render () {
    let {tabs, openTab} = this.props;
    if (tabs.activeKey && tabs.tabsArray[tabs.activeKey] && tabs.tabsArray[tabs.activeKey].status === 'loading') {
      return (
        <div className="tab-container">loading...</div>
      );
    }

    let currentTab = null;
    
    if (tabs.activeKey && tabs.tabsArray.length > 0) {
      currentTab = tabs.tabsArray.filter(t => t.tabKey === tabs.activeKey);
    }

    return (
      <div className="tab-container">
        <TabPane
          tabs={tabs}
          onSelect={openTab}
        />
        <TabContent currentTab={currentTab} />
      </div>
    );
  }

  componentDidMount () {
    const { tabs } = this.props;
    const { activeKey } = tabs;
    
    if (activeKey) {
      ajaxRequest('/' + activeKey)
        .then(response => {
          console.log(response);
          this.props.setTabContent(activeKey, {
            type: 'grid',
            data: response.data,
            meta: response.meta,
            status: 'done'
          });
        })
        .catch(error => {
          console.log(error);
        });
    }
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
    },
    setTabContent: (tabKey, payload) => {
      dispatch(setTabContent(tabKey, payload));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TabContainer);