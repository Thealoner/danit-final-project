export const updateCurrentTabAttributes = (state, newAttributes) => {
  return updateTabAttributes(state, newAttributes, state.activeKey);
};

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
};

export const updateCurrentTabFormData = (state, formData) => {
  const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey === state.activeKey);

  const updatedTab = {
    ...state.tabsArray[tabIndex],
    form: {
      ...state.tabsArray[tabIndex].form,
      ...formData
    }
  };

  const newState = {
    ...state,
    tabsArray: [
      ...state.tabsArray.filter(tab => tab.tabKey !== state.activeKey),
      updatedTab
    ]
  };

  return newState;
};