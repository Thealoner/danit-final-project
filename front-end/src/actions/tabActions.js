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

export const setTabGridContent = (tabKey, payload) => {
  return {
    type: tab.SET_GRID_CONTENT,
    tabKey,
    payload
  };
};

export const setTabFormContent = (tabKey, payload) => {
  return {
    type: tab.SET_FORM_CONTENT,
    tabKey,
    payload
  };
};

export const storeTabFormData = (tabKey, formData) => {
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