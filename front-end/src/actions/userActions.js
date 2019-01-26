import { user } from './types';
import ajaxRequest from '../helpers/ajaxRequest';
import { toastr } from 'react-redux-toastr';

export const getAvatar = () => {
  return dispatch => {
    ajaxRequest.get('api/storage/avatar', 'storage')
      .then(avatar => {
        dispatch(updateAvatar({ avatar }));
      })
      .catch(() => {
        toastr.error('Can\'t get avatar image');
      });
  };
};

export const postAvatar = avatar => {
  return dispatch => {
    ajaxRequest.post('/api/storage/avatar/upload', avatar, 'storage')
      .then(() => {
        dispatch(getAvatar());
        toastr.success('Аватар успешно изменен!');
      })
      .catch(error => {
        error.response.json().then(data => toastr.error(data.message));
      });
  };
};

export const updateAvatar = payload => {
  return {
    type: user.UPDATE_AVATAR,
    payload
  };
};