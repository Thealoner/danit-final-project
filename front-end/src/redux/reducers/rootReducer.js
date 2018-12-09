import {combineReducers} from 'redux';
import loading from './loading';
import {reducer as toastrReducer} from 'react-redux-toastr';
import tabsReducer from './tabsReducer';

const reducers = {
  loading,
  toastr: toastrReducer,
  tabsReducer
};
const rootReducer = combineReducers(reducers);

export default rootReducer;