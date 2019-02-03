import { user } from '../actions/types';

const initialState = {
  profile: null,
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

    case user.SET_PROFILE: {
      return {
        ...state,
        profile: action.payload
      };
    }

    default: {
      return state;
    }
  }
}