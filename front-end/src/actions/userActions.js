import { user } from './types';
import ajaxRequestStorage from '../helpers/ajaxRequestStorage';
import { toastr } from 'react-redux-toastr';

export const getAvatar = () => {
  return dispatch => {
    ajaxRequestStorage.get('api/storage/avatar', 'storage')
      .then(
        avatar => {
          dispatch(updateAvatar({ avatar }));
        },
        error => {
          if (error.response.status !== 406) {
            error.response.json().then(data => toastr.error(data.message));
          } else {
            toastr.error(error.response.status + ' ' + error.response.statusText);
          }
        }
      );
  };
};

export const postAvatar = avatar => {
  return dispatch => {
    ajaxRequestStorage.post('/api/storage/avatar/upload', avatar, 'storage')
      .then(
        response => {
          dispatch(getAvatar());
          toastr.success('Аватар успешно изменен!');
        },
        error => {
          error.response.json().then(data => toastr.error(data.message));
        }
      );
  };
};

export const deleteAvatar = () => {
  return dispatch => {
    ajaxRequestStorage.delete('api/storage/avatar/delete')
      .then(
        avatar => {
          dispatch(getAvatar());
          toastr.success('Аватар успешно удален!');
        },
        error => {
          error.response.json().then(data => toastr.error(data.message));
        }
      );
  };
};

export const updateAvatar = payload => {
  return {
    type: user.UPDATE_AVATAR,
    payload
  };
};

export const setProfile = payload => ({
  type: user.SET_PROFILE,
  payload
});