import {combineReducers} from 'redux';
import loading from './loading';
import {reducer as toastrReducer} from 'react-redux-toastr';
import tabsReducer from './tabs';

const reducers = {
  loading,
  toastr: toastrReducer,
  tabs: tabsReducer
};
const rootReducer = combineReducers(reducers);

export default rootReducer;