import {combineReducers} from 'redux';
import loading from './loading';
import {reducer as toastrReducer} from 'react-redux-toastr';

const reducers = {
  loading: loading,
  toastr: toastrReducer
};
const rootReducer = combineReducers(reducers);

export default rootReducer;