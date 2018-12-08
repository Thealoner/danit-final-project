import {
  OPEN_TAB,
  CLOSE_TAB,
  GET_CURRENT_TAB_KEY,
  STORE_TAB_FORM_DATA,
  SUBMIT_TAB_FORM_DATA,
  GET_TAB_FORM_DATA,
  CLEAR_TAB_FORM_DATA
} from '../actions/types';

const initialState = {
  tabs: [],
  activeKey: null
};

export default function tabReducer (state = initialState, action) {
  switch (action.type) {
    case OPEN_TAB: {
      return {
        ...state,
        activeKey: action.payload.tabKey
      };
    }
    case CLOSE_TAB: {
      return {
        ...state,
        tabs: [
          ...state.tabs.filter(tab => tab.tabKey !== action.tabKey)
        ]
      };
    }
    case STORE_TAB_FORM_DATA: {
      const tabIndex = state.tabs.findIndex(tab => tab.tabKey !== action.tabKey);

      return {
        ...state,
        tabs: [
          ...state.tabs.filter((tab, index) => index !== tabIndex),
          {
            ...state.tabs[tabIndex],
            formData: action.formData,
            formDataEdited: true
          }
        ]
      };
    }
    case SUBMIT_TAB_FORM_DATA: {
      const tabIndex = state.tabs.findIndex(tab => tab.tabKey !== action.tabKey);

      return {
        ...state,
        tabs: [
          ...state.tabs.filter((tab, index) => index !== tabIndex),
          {
            ...state.tabs[tabIndex],
            formData: action.formData,
            formDataEdited: true
          }
        ]
      };
    }
    default: {
      return state;
    }
  }
}