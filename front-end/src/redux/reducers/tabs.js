import { tab } from '../actionTypes';

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
      return {
        ...state,
        tabsArray: [
          ...state.tabsArray.filter(tab => tab.tabKey !== action.tabKey)
        ],
        activeKey: state.tabsArray[0].tabKey
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