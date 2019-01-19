import React, { Component } from 'react';
import gridEntities from '../gridEntities';
import { connect } from 'react-redux';
import { openTab, getGridData, loadingTab } from '../../../actions/tabActions';

class EntitiesMenu extends Component {
  openEntitiesMenuItem = (entity) => {
    const { tabs, openTab, loadingTab, getGridData } = this.props;
    let isOpened = tabs.tabsArray.some((tab) => tab.tabKey === entity.id);

    if (tabs.tabsArray.length > 0 && isOpened) {
      openTab(entity.id);
    } else {
      openTab(entity.id, {type: 'grid'});
      loadingTab();
      getGridData({ tabKey: entity.id, columns: entity.columns });
    }
  }

  render () {
    const { currentTab } = this.props;

    return gridEntities.map((entity) =>
      <span
        key={entity.id}
        className={`configurator__link${currentTab && entity.id === currentTab.tabKey ? ' configurator__link--active' : ''}`}
        onClick={() => this.openEntitiesMenuItem(entity)}
      >
        {entity.name}
      </span>
    );
  }
}

const mapStateToProps = state => {
  let currentTab = null;

  if (state.tabs.activeKey && state.tabs.tabsArray.length > 0) {
    currentTab = state.tabs.tabsArray.filter(t => t.tabKey === state.tabs.activeKey)[0];
  }

  return {
    tabs: state.tabs,
    currentTab
  };
};

const mapDispatchToProps = dispatch => {
  return {
    openTab: (tabKey, payload) => {
      dispatch(openTab(tabKey, payload));
    },
    getGridData: (options) => {
      dispatch(getGridData(options));
    },
    loadingTab: () => {
      dispatch(loadingTab());
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(EntitiesMenu);