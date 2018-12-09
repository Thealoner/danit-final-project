import {
  GET_CURRENT_TAB_KEY,
  GET_TAB_FORM_DATA
} from '../actionTypes';

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