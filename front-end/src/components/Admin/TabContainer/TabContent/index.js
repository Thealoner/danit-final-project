import React, { Component } from 'react';
import './index.scss';
import { setTabGridContent } from '../../../../actions/tabActions';
import { connect } from 'react-redux';
import Grid from '../../Grid';

class TabContent extends Component {
  state = {};

  render () {
    const { currentTab } = this.props;

    if (!currentTab) {
      return <div>Nothing here</div>;
    }

    if (currentTab.tabKey === 'grid') {
      return (
        <Grid
          currentTab={currentTab}
        />
      );
    }
    
    return (
      <div className="tab-content">{JSON.stringify(currentTab)}</div>
    );
  }
};

const mapStateToProps = state => {
  let currentTab = null;
  
  if (state.tabs.activeKey && state.tabs.tabsArray.length > 0) {
    currentTab = state.tabs.tabsArray.filter(t => t.tabKey === state.tabs.activeKey)[0];
  }

  return {
    currentTab
  };
};

const mapDispatchToProps = dispatch => {
  return {
    setTabGridContent: (tabKey, payload) => {
      dispatch(setTabGridContent(tabKey, payload));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TabContent);