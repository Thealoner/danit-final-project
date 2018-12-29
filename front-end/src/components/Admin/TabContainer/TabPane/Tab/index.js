import React from 'react';
import {connect} from 'react-redux';
import {closeTab} from '../../../../../actions/tabActions';
import {getEntityByType} from '../../../gridEntities';

const Tab = ({tabKey, title, activeKey, onSelect, closeTab, edited}) => {
  const tabClass = 'tabs__head' + (tabKey === activeKey ? ' tabs__head--active' : '');

  const closeTabHandler = (e, tabKey) => {
    e.stopPropagation();
    closeTab(tabKey);
  };

  return (
    <li key={tabKey} className={tabClass} onClick={() => onSelect(tabKey)}>
      <span className='tabs__title-wrapper' title={`${getEntityByType(tabKey).name} ${edited ? '*' : ''}`}>
        {getEntityByType(tabKey).name}
        <span>{edited ? '*' : ''}</span>
      </span>
      <span className='tabs__btn-wrapper'>
        <button className='tabs__close-btn' onClick={(e) => closeTabHandler(e, tabKey)}/>
      </span>
    </li>
  );
};

const mapDispatchToProps = (dispatch) => ({
  closeTab: tabKey => {
    dispatch(closeTab(tabKey));
  }
});

export default connect(null, mapDispatchToProps)(Tab);