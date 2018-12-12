import React from 'react';
import { connect } from 'react-redux';
import { closeTab } from '../../../../../actions/tabActions';
import './index.scss';

const Tab = ({ tabKey, title, activeKey, onSelect, closeTab }) => {
  const tabClass = 'tab' + (tabKey === activeKey ? ' tab--active' : '');

  const closeTabHandler = (e, tabKey) => {
    e.stopPropagation();
    closeTab(tabKey);
  };

  return (
    <li key={tabKey} className={tabClass} onClick={(e) => onSelect(tabKey)}>
      {title}
      <button onClick={(e) => closeTabHandler(e, tabKey)}>X</button>
    </li>
  );
};

const mapDispatchToProps = dispatch => {
  return {
    closeTab: tabKey => {
      dispatch(closeTab(tabKey));
    }
  };
};

export default connect(null, mapDispatchToProps)(Tab);