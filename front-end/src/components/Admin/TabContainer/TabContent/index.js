import React, { Component } from 'react';
import { setTabGridData } from '../../../../actions/tabActions';
import { connect } from 'react-redux';
import Grid from '../../Grid';

class TabContent extends Component {
  state = {};

  render () {
    const { currentTab } = this.props;

    if (!currentTab) {
      return <div>Nothing here</div>;
    }

    if (currentTab.type === 'grid') {
      return (
        <div className="tabs__content">
          <Grid
            currentTab={currentTab}
          />
        </div>
      );
    }
    
    return (
      <div className="tabs__content">{JSON.stringify(currentTab)}</div>
    );
  }
}

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
    setTabGridData: (tabKey, payload) => {
      dispatch(setTabGridData(tabKey, payload));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TabContent);