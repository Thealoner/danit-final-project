import {LOADING_STATUS} from '../actions/types';

function loadingReducer (state = false, action) {
  switch (action.type) {
    case LOADING_STATUS:
      return !state;
    default:
      return state;
  }
}

export default loadingReducer;