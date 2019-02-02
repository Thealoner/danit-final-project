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

export const updateCurrentTabGridData = (state, gridData) => {
  const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey === state.activeKey);

  const updatedTab = {
    ...state.tabsArray[tabIndex],
    grid: {
      ...state.tabsArray[tabIndex].grid,
      ...gridData
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

export const updateAllFormCollisions = (state, collisionRecords, collisionStatus) => {
  const tabIndexes = state.tabsArray.findIndex(tab => {
    return -1 !== collisionRecords.findIndex(record => {
      return (
        tab.tabKey === record.entity &&
        tab.type === 'form' &&
        tab.form &&
        tab.form.data &&
        tab.form.data.id &&
        tab.form.data.id === record.id
      );
    });
  });

  tabIndexes.forEach(tabIndex => {
    updateFormCollision(state, tabIndex, collisionStatus);
  });
}

const updateFormCollision = (state, tabIndex, collisionStatus) => {
  const updatedTab = {
    ...state.tabsArray[tabIndex],
    form: {
      ...state.tabsArray[tabIndex].form,
      data: {
        ...state.form.data,
        editCollision: collisionStatus
      }
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
}