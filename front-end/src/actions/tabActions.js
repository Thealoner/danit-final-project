import { tab } from './types';
import ajaxRequest from '../helpers/ajaxRequest';
import { toastr } from 'react-redux-toastr';

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
    type: tab.LOADING_TAB
  };
};

export const doneTab = () => {
  return {
    type: tab.DONE_TAB
  };
};

export const loadingGrid = () => {
  return {
    type: tab.LOADING_GRID
  };
};

export const doneGrid = () => {
  return {
    type: tab.DONE_GRID
  };
};

export const setGridData = (tabKey, payload) => {
  return {
    type: tab.SET_GRID_DATA,
    tabKey,
    payload
  };
};

export const setFormData = (tabKey, payload) => {
  return {
    type: tab.SET_FORM_DATA,
    tabKey,
    payload
  };
};

export const cancelEditFormData = () => {
  return {
    type: tab.CANCEL_EDIT_FORM_DATA
  };
};

export const setFilter = (filter) => {
  return {
    type: tab.SET_FILTER,
    filter: filter
  };
};

export const storeTabTmpFormData = (payload) => {
  return {
    type: tab.STORE_TMP_FORM_DATA,
    payload
  };
};

export const getGridData = ({
  tabKey, page = 1, size = 10, columns, filter = {
    isFiltered: false,
    field: '',
    value: '',
    activeFilter: '',
    isExact: false
  }
}) => {
  return (dispatch) => {
    dispatch(loadingGrid());
    ajaxRequest.get('/' + tabKey + '?page=' + page + '&size=' + size + '&' + filter.field + '=' + filter.value + '&equal=' + filter.isExact)
      .then(
        response => {
          dispatch(setGridData(tabKey, {
            data: response.data,
            meta: response.meta,
            columns: columns,
            type: 'grid'
          }));
          dispatch(setFilter(filter));
          dispatch(doneTab());
          dispatch(doneGrid());
        },
        error => {
          toastr.error(error.message);
        });
  };
};

export const getFormData = (tabKey, id) => {
  return (dispatch) => {
    dispatch(loadingTab());
    ajaxRequest.get('/' + tabKey + '/' + id)
      .then(
        response => {
          dispatch(setFormData(tabKey, {
            mode: 'edit',
            type: 'form',
            ...response
          }));
          dispatch(doneTab());
        },
        error => {
          toastr.error(error.message);
        });
  };
};

export const saveFormData = (tabKey, formData, columns, mode, page, filter) => {
  return (dispatch) => {
    dispatch(loadingTab());
    ajaxRequest.put(
      '/' + tabKey,
      JSON.stringify([formData])
    )
      .then(() => {
        dispatch(cancelEditFormData());
        dispatch(getGridData({
          tabKey: tabKey,
          columns: columns,
          page: page,
          filter: filter
        }));
        toastr.success('Данные успешно сохранены');
      },
      () => {
        dispatch(doneTab());
        toastr.error('Ошибка при сохранении');
      }
      );
  };
};

export const deleteCurrentEntityItem = (tabKey, formData, columns, page, filter) => {
  return (dispatch) => {
    dispatch(loadingTab());
    ajaxRequest.delete(
      '/' + tabKey,
      JSON.stringify([{id: formData.id}])
    )
      .then(() => {
        dispatch(cancelEditFormData());
        dispatch(getGridData({
          tabKey: tabKey,
          columns: columns,
          page: page,
          filter: filter
        }));
        toastr.success('Данные успешно удалены');
      },
      () => {
        dispatch(doneTab());
        toastr.error('Ошибка при удалении');
      }
      );
  };
};