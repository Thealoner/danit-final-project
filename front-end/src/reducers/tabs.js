import { tab } from '../actions/types';

const initialState = {
  tabsArray: [],
  activeKey: null
};

export default function tabsReducer (state = initialState, action) {
  switch (action.type) {
    case tab.OPEN: {
      const tabIndex = state.tabsArray.some(tab => tab.tabKey === action.tabKey);
      
      if (tabIndex) {
        return {
          ...state,
          activeKey: action.tabKey
        };
      } else {
        return {
          ...state,
          tabsArray: [
            ...state.tabsArray,
            {
              title: action.tabKey,
              tabKey: action.tabKey
            }
          ],
          activeKey: action.tabKey
        };
      }
    }

    case tab.CLOSE: {
      let foundIndex = 0;
      const newTabsArray = state.tabsArray.filter((t, i) => {
        if (t.tabKey !== action.tabKey) {
          return true;
        }
        foundIndex = i;
        return false;
      });

      let activeKey = state.activeKey;
      if (activeKey === action.tabKey) {
        if (foundIndex) {
          foundIndex--;
        }
        activeKey = newTabsArray.length > 0 ? newTabsArray[foundIndex].tabKey : null;
      }

      return {
        ...state,
        tabsArray: newTabsArray,
        activeKey: activeKey
      };
    }

    case tab.SET_CONTENT: {
      const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey !== action.tabKey);

      return {
        ...state,
        tabsArray: [
          ...state.tabsArray,
          {
            ...state.tabsArray[tabIndex],
            type: action.payload.type,
            data: action.payload.data,
            meta: action.payload.meta
          }
        ]
      };
    }

    case tab.STORE_TMP_FORM_DATA: {
      const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey !== action.tabKey);

      return {
        ...state,
        tabsArray: [
          ...state.tabsArray.filter((tab, index) => index !== tabIndex),
          {
            ...state.tabsArray[tabIndex],
            formData: action.payload.formData,
            formDataEdited: true
          }
        ]
      };
    }

    case tab.CLEAR_FORM_DATA: {
      const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey !== action.tabKey);

      return {
        ...state,
        tabsArray: [
          ...state.tabsArray.filter((tab, index) => index !== tabIndex),
          {
            ...state.tabsArray[tabIndex],
            formData: null,
            formDataEdited: false
          }
        ]
      };
    }

    case tab.RESET_FORM_DATA: {
      const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey !== action.tabKey);

      return {
        ...state,
        tabsArray: [
          ...state.tabsArray.filter((tab, index) => index !== tabIndex),
          {
            ...state.tabsArray[tabIndex],
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