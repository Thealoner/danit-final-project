import React from 'react';
import Tab from './Tab';
import './index.scss';

function TabPane ({ tabs, onSelect }) {
  const tabsList = tabs.tabsArray.map(
    tab => <Tab key={tab.tabKey} tabKey={tab.tabKey} title={tab.title} activeKey={tabs.activeKey} onSelect={onSelect} />
  );
  
  return <ul className='tab-pane'>{tabsList}</ul>;
}

export default TabPane;