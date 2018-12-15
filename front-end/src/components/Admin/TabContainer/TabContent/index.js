import React from 'react';
import './index.scss';

const TabContent = ({ currentTab }) => {
  const content = currentTab ? currentTab.tabKey : 'empty';
  
  return (
    <div className="tab-content">{content}</div>
  );
};

export default TabContent;