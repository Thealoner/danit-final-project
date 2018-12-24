import {combineReducers} from 'redux';
import {reducer as toastrReducer} from 'react-redux-toastr';
import tabsReducer from './tabs';

const reducers = {
  toastr: toastrReducer,
  tabs: tabsReducer
};
const rootReducer = combineReducers(reducers);

export default rootReducer;