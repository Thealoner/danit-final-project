import React from 'react';
import Tab from './Tab';

function TabPane ({tabs, onSelect}) {
  const tabsList = tabs.tabsArray.map(tab =>
    <Tab
      key={tab.tabKey}
      tabKey={tab.tabKey}
      title={tab.title}
      activeKey={tabs.activeKey}
      onSelect={onSelect}
      edited={tab.form && tab.form.edited}
    />
  );

  return <div className='tabs__pane'><span className='tabs__pane-skirt'>{tabsList}</span></div>;
}

export default TabPane;