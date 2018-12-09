import {LOADING_STATUS} from '../actionTypes';

function loadingReducer (state = false, action) {
  switch (action.type) {
    case LOADING_STATUS:
      return !state;
    default:
      return state;
  }
}

export default loadingReducer;