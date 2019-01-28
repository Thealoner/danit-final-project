import { tab } from '../actions/types';
import { getEntityByType } from '../components/Admin/gridEntities';
import { updateCurrentTabAttributes } from '../helpers/reducerHelper';

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
//       tabStatus: 'done', // OR 'loading'
//       gridStatus: 'done' // OR 'loading'
//     }
//   ],
//   activeKey: 'packets'
// };

export default function tabsReducer (state = initialState, action) {
  switch (action.type) {
    case tab.OPEN: {
      const tabIndex = state.tabsArray.findIndex(tab => tab.tabKey === action.tabKey);
      if (tabIndex === -1) {
        const newTab = {
          ...state.tabsArray[tabIndex],
          title: getEntityByType(action.tabKey).name,
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

    case tab.LOADING_TAB: {
      return updateCurrentTabAttributes(state, { tabStatus: 'loading' });
    }

    case tab.DONE_TAB: {
      return updateCurrentTabAttributes(state, { tabStatus: 'done' });
    }

    case tab.LOADING_GRID: {
      return updateCurrentTabAttributes(state, { gridStatus: 'loading' });
    }

    case tab.DONE_GRID: {
      return updateCurrentTabAttributes(state, { gridStatus: 'done' });
    }

    case tab.SET_GRID_DATA: {
      const newTabData = {};

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
      const newTabData = {};

      if (action.payload.type) {
        newTabData.type = action.payload.type;
      }

      if (action.payload) {
        newTabData.form = {
          mode: action.payload.mode,
          data: action.payload.data,
          meta: action.payload.meta,
          edited: false
        };
      }

      return updateCurrentTabAttributes(state, newTabData);
    }

    case tab.CANCEL_EDIT_FORM_DATA: {
      const newTabData = {
        type: 'grid',
        form: {
          edited: false
        }
      };

      return updateCurrentTabAttributes(state, newTabData);
    }

    case tab.SET_FILTER: {
      const newTabData = {
        filter: action.filter
      };

      return updateCurrentTabAttributes(state, newTabData);
    }

    case tab.STORE_TMP_FORM_DATA: {
      const newTabData = {
        form: {
          ...action.payload,
          edited: true
        }
      };
      return updateCurrentTabAttributes(state, newTabData);
    }

    default: {
      return state;
    }
  }
}