import React from 'react';
import gridEntities from '../gridEntities';
import { connect } from 'react-redux';
import { openTab, getGridData, loadingTab } from '../../../actions/tabActions';

function EntitiesMenu ({openTab, loadingTab, getGridData}) {
  const links = [];

  gridEntities.forEach((entity) => {
    links.push(
      <span
        key={entity.id}
        className="configurator__link"
        onClick={() => {
          openTab(entity.id, {type: 'grid'});
          loadingTab();
          getGridData({ tabKey: entity.id, columns: entity.columns });
        }}
      >
        {entity.name}
      </span>
    );
  });

  return links;
}

const mapStateToProps = state => {
  return {
    tabs: state.tabs
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