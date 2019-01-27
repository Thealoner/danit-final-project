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
          error.response.json().then(data => toastr.error(data.message));
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