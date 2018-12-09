import {combineReducers} from 'redux';
import {reducer as toastrReducer} from 'react-redux-toastr';

const reducers = {
  toastr: toastrReducer
};
const rootReducer = combineReducers(reducers);

export default rootReducer;