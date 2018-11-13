import { LOCALE_SET } from '../actions/types';

export default function locale (state = {lang: 'ru'}, action = {}) {
  switch (action.type) {
    case LOCALE_SET:
      return {lang: action.lang};

    default:
      return state;
  }
}