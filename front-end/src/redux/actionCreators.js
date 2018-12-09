import { tab } from './actionTypes';

export function openTab (tabKey) {
  return {
    type: tab.OPEN,
    payload: {
      tabKey
    }
  };
}

export function closeTab (tabKey) {
  return {
    type: tab.CLOSE,
    payload: {
      tabKey
    }
  };
}

export function storeTabFormData (tabKey, formData) {
  return {
    type: tab.STORE_TMP_FORM_DATA,
    payload: {
      tabKey,
      formData
    }
  };
}

export function clearTabFormData (tabKey) {
  return {
    type: tab.CLEAR_FORM_DATA,
    payload: {
      tabKey
    }
  };
}

export function resetTabFormData (tabKey, formData) {
  return {
    type: tab.RESET_FORM_DATA,
    payload: {
      tabKey,
      formData
    }
  };
}