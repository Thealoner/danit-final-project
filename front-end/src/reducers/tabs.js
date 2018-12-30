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

    case tab.LOADING: {
      return updateCurrentTabAttributes(state, { status: 'loading' });
    }

    case tab.DONE: {
      return updateCurrentTabAttributes(state, { status: 'done' });
    }

    case tab.SET_GRID_DATA: {
      let newTabData = {};

      if (action.payload.type) {
        newTabData.type = action.payload.type;
      }

      if (action.payload.data) {
        newTabData.grid = {
          data: action.payload.data,
          meta: action.payload.meta,
          columns: action.payload.columns
        };
      }

      return updateCurrentTabAttributes(state, newTabData);
    }

    case tab.SET_FORM_DATA: {
      let newTabData = {};

      if (action.payload.type) {
        newTabData.type = action.payload.type;
      }

      if (action.payload) {
        newTabData.form = {
          id: action.payload.id,
          data: action.payload.data,
          meta: action.payload.meta,
          edited: false
        };
      }

      return updateCurrentTabAttributes(state, newTabData);
    }

    case tab.PERSIST_FORM_DATA: {
      let newTabData = {
        type: 'grid',
        form: null
      };

      return updateCurrentTabAttributes(state, newTabData);
    }

    case tab.STORE_TMP_FORM_DATA: {
      const newTabData = {
        form: {
          data: action.payload.data,
          edited: true
        }
      };
      return updateCurrentTabAttributes(state, newTabData);
    }

    default: {
      return state;
    }
  }

  function updateCurrentTabAttributes (state, newAttributes) {
    return updateTabAttributes(state, newAttributes, state.activeKey);
  }

  function updateTabAttributes (state, newAttributes, tabKey) {
    const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey === tabKey);
    let newTabsArray = state.tabsArray.map((value, index) => {
      if (index === tabIndex) {
        return {
          ...value,
          ...newAttributes
        };
      } else {
        return value;
      }
    });

    let newState = {
      ...state,
      tabsArray: newTabsArray
    };

    return newState;
  }
}