import {
  OPEN_TAB,
  CLOSE_TAB,
  GET_CURRENT_TAB_KEY,
  STORE_TAB_FORM_DATA,
  GET_TAB_FORM_DATA,
  CLEAR_TAB_FORM_DATA
} from './types';

export function openTab (tabKey) {
  return {
    type: OPEN_TAB,
    payload: {
      tabKey
    }
  };
}

export function closeTab (tabKey) {
  return {
    type: CLOSE_TAB,
    payload: {
      tabKey
    }
  };
}

export function getCurrentTabKey () {
  return {
    type: GET_CURRENT_TAB_KEY
  };
}

export function getTabFormData (tabKey) {
  return {
    type: GET_TAB_FORM_DATA
  };
}

export function storeTabFormData (tabKey, formData) {
  return {
    type: STORE_TAB_FORM_DATA,
    payload: {
      tabKey,
      formData
    }
  };
}

export function clearTabFormData (tabKey) {
  return {
    type: CLEAR_TAB_FORM_DATA,
    payload: {
      tabKey
    }
  };
}