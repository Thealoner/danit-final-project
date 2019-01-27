import { user } from '../actions/types';

const initialState = {
  user: null,
  avatar: ''
};

export default function userReducer (state = initialState, action) {
  switch (action.type) {
    case user.UPDATE_AVATAR: {
      return {
        ...state,
        avatar: action.payload.avatar
      };
    }

    default: {
      return state;
    }
  }
}