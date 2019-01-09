import {combineReducers} from 'redux';
import {reducer as toastrReducer} from 'react-redux-toastr';
import {reducer as formReducer} from 'redux-form';
import tabsReducer from './tabs';

const reducers = {
  toastr: toastrReducer,
  tabs: tabsReducer,
  form: formReducer
};
const rootReducer = combineReducers(reducers);

export default rootReducer;