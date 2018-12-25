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

export const loadingTab = () => {
  return {
    type: tab.LOADING
  };
};

export const doneTab = () => {
  return {
    type: tab.DONE
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

export const persistTabFormData = (tabKey, payload) => {
  return {
    type: tab.PERSIST_FORM_DATA,
    tabKey,
    payload
  };
};

export const storeTabTmpFormData = (tabKey, payload) => {
  return {
    type: tab.STORE_TMP_FORM_DATA,
    tabKey,
    payload
  };
};