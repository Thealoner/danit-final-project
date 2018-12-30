import React from 'react';
import {connect} from 'react-redux';
import {closeTab} from '../../../../../actions/tabActions';
import {getEntityByType} from '../../../gridEntities';
import { NavLink } from 'react-router-dom';

const Tab = ({tabKey, title, activeKey, onSelect, closeTab, edited}) => {
  const closeTabHandler = (e, tabKey) => {
    e.stopPropagation();
    closeTab(tabKey);
  };

  return (
    <NavLink to={'/admin/' + tabKey} key={tabKey} className="tabs__head" activeClassName="tabs__head--active"
      onClick={() => onSelect(tabKey)}>
      <span className="tabs__title-wrapper" title={`${getEntityByType(tabKey).name} ${edited ? '*' : ''}`}>
        {getEntityByType(tabKey).name}
        <span>{edited ? '*' : ''}</span>
      </span>
      <span className="tabs__btn-wrapper">
        <button className="tabs__close-btn" onClick={(e) => closeTabHandler(e, tabKey)}/>
      </span>
    </NavLink>
  );
};

const mapDispatchToProps = (dispatch) => ({
  closeTab: tabKey => {
    dispatch(closeTab(tabKey));
  }
});

export default connect(null, mapDispatchToProps)(Tab);