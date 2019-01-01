import React from 'react';
import {connect} from 'react-redux';
import {closeTab} from '../../../../../actions/tabActions';
import {getEntityByType} from '../../../gridEntities';
import { NavLink } from 'react-router-dom';

const Tab = ({tabKey, title, activeKey, onSelect, closeTab, edited}) => {
  const tabClass = 'tabs__head' + (tabKey === activeKey ? ' tabs__head--active' : '');

  const closeTabHandler = (e, tabKey) => {
    e.preventDefault();
    e.stopPropagation();
    closeTab(tabKey);
  };

  return (
    <NavLink to={'/admin/' + tabKey} key={tabKey} className={tabClass} onClick={() => onSelect(tabKey)}
      title={`${getEntityByType(tabKey).name} ${edited ? '(несохранённые данные)' : ''}`}>
      <span className={!edited ? 'tabs__title' : 'tabs__title--edited'}>
        {getEntityByType(tabKey).name}
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