import {combineReducers} from 'redux';
import {reducer as toastrReducer} from 'react-redux-toastr';
import {reducer as formReducer} from 'redux-form';
import tabsReducer from './tabs';
import userReducer from './user';

const reducers = {
  toastr: toastrReducer,
  tabs: tabsReducer,
  form: formReducer,
  user: userReducer
};
const rootReducer = combineReducers(reducers);

export default rootReducer;