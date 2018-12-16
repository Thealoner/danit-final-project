import { tab } from '../actions/types';

const initialState = {
  tabsArray: [],
  activeKey: null
};

// Purely for informational purpose:
// const initialStateExample = {
//   tabsArray: [
//     {
//       tabKey: 'packets',
//       title: 'Пакеты',
//       type: 'grid', // OR 'form'
//       grid: {
//         data: [],
//         meta: {},
//         columns: []
//       },
//       form: {
//         data: [],
//         meta: {},
//         edited: false
//       },
//       status: 'done' // OR 'loading'
//     }
//   ],
//   activeKey: 'packets'
// };

export default function tabsReducer (state = initialState, action) {
  switch (action.type) {
    case tab.OPEN: {
      const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey === action.tabKey);
      if (tabIndex === -1) {
        let newTab = {
          ...state.tabsArray[tabIndex],
          title: action.tabKey,
          tabKey: action.tabKey,
          type: action.payload.type,
          grid: {
            data: [],
            meta: {
              totalElements: 0,
              currentPage: 1,
              pagesTotal: 1,
              elementsPerPage: 1
            },
            columns: []
          }
        };

        return {
          ...state,
          tabsArray: [
            ...state.tabsArray.filter(tab => tab.tabKey !== action.tabKey),
            newTab
          ],
          activeKey: action.tabKey
        };
      } else {
        return {
          ...state,
          activeKey: action.tabKey
        };
      }
    }

    case tab.CLOSE: {
      let foundIndex = 0;
      const after = state.tabsArray.filter((t, i) => {
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
        activeKey = after.length > 0 ? after[foundIndex].tabKey : null;
      }

      return {
        ...state,
        tabsArray: after,
        activeKey: activeKey
      };
    }

    case tab.SET_GRID_CONTENT: {
      const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey === action.tabKey);
      let newTabData = {};
      if (action.payload.type) {
        newTabData.type = action.payload.type;
      }

      if (action.payload.data) {
        newTabData.grid = {
          data: action.payload.data,
          meta: action.payload.meta,
          columns: action.payload.columns
        }
      }

      return {
        ...state,
        tabsArray: [
          ...state.tabsArray.filter(tab => tab.tabKey !== action.tabKey),
          {
            ...state.tabsArray[tabIndex],
            ...newTabData
          }
        ]
      };
    }

    case tab.SET_FORM_CONTENT: {
      return state;
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