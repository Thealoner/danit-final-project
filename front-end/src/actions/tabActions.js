import { tab } from './types';

export const openTab = (tabKey, payload) => {
  return {
    type: tab.OPEN,
    tabKey,
    payload
  };
};

export const closeTab = tabKey => {
  return {
    type: tab.CLOSE,
    tabKey
  };
};

export const setTabGridData = (tabKey, payload) => {
  return {
    type: tab.SET_GRID_DATA,
    tabKey,
    payload
  };
};

export const setTabFormData = (tabKey, payload) => {
  return {
    type: tab.SET_FORM_DATA,
    tabKey,
    payload
  };
};

export const storeTabTmpFormData = (tabKey, formData) => {
  return {
    type: tab.STORE_TMP_FORM_DATA,
    tabKey,
    payload: {
      formData
    }
  };
};

export const clearTabFormData = tabKey => {
  return {
    type: tab.CLEAR_FORM_DATA,
    tabKey
  };
};

export const resetTabFormData = (tabKey, formData) => {
  return {
    type: tab.RESET_FORM_DATA,
    tabKey,
    payload: {
      formData
    }
  };
};