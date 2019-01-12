export const updateCurrentTabAttributes = (state, newAttributes) => {
  return updateTabAttributes(state, newAttributes, state.activeKey);
}

export const updateTabAttributes = (state, newAttributes, tabKey) => {
  const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey === tabKey);
  const newTabsArray = state.tabsArray.map((value, index) => {
    if (index === tabIndex) {
      return {
        ...value,
        ...newAttributes
      };
    } else {
      return value;
    }
  });

  const newState = {
    ...state,
    tabsArray: newTabsArray
  };

  return newState;
}