import { tab } from './types';
import ajaxRequest from '../helpers/ajaxRequest';
import {toastr} from 'react-redux-toastr';

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

export const getGridData = ({tabKey, page = 1, size = 3, filterString = '', columns} = {}) => {
  return (dispatch) => {
    dispatch(loadingTab());
    ajaxRequest.get('/' + tabKey + '?page=' + page + '&size=' + size + filterString)
      .then(response => {
        dispatch(setTabGridData(tabKey, {
          data: response.data,
          meta: response.meta,
          columns: columns
        }));
        dispatch(doneTab());
      })
      .catch(error => {
        dispatch(doneTab());
        toastr.error(error.message);
        dispatch(setTabGridData(tabKey, {
          data: [],
          meta: {},
          columns: columns
        }));
      });
  };
};
