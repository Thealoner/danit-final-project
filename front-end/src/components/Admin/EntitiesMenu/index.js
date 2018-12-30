import React from 'react';
import { NavLink } from 'react-router-dom';
import gridEntities from '../gridEntities';
import { connect } from 'react-redux';
import { openTab, getGridData } from '../../../actions/tabActions';

function EntitiesMenu (props) {
  const links = [];

  gridEntities.forEach((entity) => {
    let url = '/admin/' + entity.id;

    links.push(
      <NavLink
        to={url}
        key={entity.id}
        className="configurator__link"
        activeClassName="configurator__link--active"
        onClick={() => {
          props.openTab(entity.id, {type: 'grid'});
          props.getGridData({ tabKey: entity.id, columns: entity.columns });
        }}
      >
        {entity.name}
      </NavLink>
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
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(EntitiesMenu);