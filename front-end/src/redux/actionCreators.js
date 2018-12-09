import { tab } from './actionTypes';

export const openTab = tabKey => {
  return {
    type: tab.OPEN,
    tabKey
  };
};

export const closeTab = tabKey => {
  return {
    type: tab.CLOSE,
    tabKey
  };
};

export const setTabContent = (tabKey, payload) => {
  return {
    type: tab.SET_CONTENT,
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