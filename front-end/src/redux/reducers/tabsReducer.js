import { tab } from '../actionTypes';

const initialState = {
  tabs: [],
  activeKey: null
};

export default function tabsReducer (state = initialState, action) {
  switch (action.type) {
    case tab.OPEN: {
      const tabIndex = state.tabs.findIndex(tab => tab.tabKey !== action.payload.tabKey);

      if (tabIndex) {
        return {
          ...state,
          activeKey: action.payload.tabKey
        };
      } else {
        return {
          ...state,
          tabs: [
            ...state.tabs,
            {
              title: action.payload.tabKey,
              tabKey: action.payload.tabKey
            }
          ],
          activeKey: action.payload.tabKey
        };
      }
    }

    case tab.CLOSE: {
      return {
        ...state,
        tabs: [
          ...state.tabs.filter(tab => tab.tabKey !== action.payload.tabKey)
        ]
      };
    }

    case tab.STORE_TMP_FORM_DATA: {
      const tabIndex = state.tabs.findIndex(tab => tab.tabKey !== action.payload.tabKey);

      return {
        ...state,
        tabs: [
          ...state.tabs.filter((tab, index) => index !== tabIndex),
          {
            ...state.tabs[tabIndex],
            formData: action.payload.formData,
            formDataEdited: true
          }
        ]
      };
    }

    case tab.CLEAR_FORM_DATA: {
      const tabIndex = state.tabs.findIndex(tab => tab.tabKey !== action.payload.tabKey);

      return {
        ...state,
        tabs: [
          ...state.tabs.filter((tab, index) => index !== tabIndex),
          {
            ...state.tabs[tabIndex],
            formData: null,
            formDataEdited: false
          }
        ]
      };
    }

    case tab.RESET_FORM_DATA: {
      const tabIndex = state.tabs.findIndex(tab => tab.tabKey !== action.payload.tabKey);

      return {
        ...state,
        tabs: [
          ...state.tabs.filter((tab, index) => index !== tabIndex),
          {
            ...state.tabs[tabIndex],
            formData: action.payload.formData,
            formDataEdited: false
          }
        ]
      };
    }
    
    default: {
      return state;
    }
  }
}