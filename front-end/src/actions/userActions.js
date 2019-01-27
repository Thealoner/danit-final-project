import { user } from './types';
import ajaxRequest from '../helpers/ajaxRequest';
import { toastr } from 'react-redux-toastr';

export const getAvatar = () => {
  return dispatch => {
    ajaxRequest.get('api/storage/avatar', 'storage')
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
    ajaxRequest.post('/api/storage/avatar/upload', avatar, 'storage')
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
    ajaxRequest.delete('api/storage/avatar/delete')
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