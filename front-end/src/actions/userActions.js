import { user } from './types';
import ajaxRequestStorage from '../helpers/ajaxRequestStorage';
import { toastr } from 'react-redux-toastr';
import ajaxRequest from '../helpers/ajaxRequest';

export const getAvatar = () => {
  return dispatch => {
    ajaxRequestStorage.get('api/storage/avatar', 'storage')
      .then(
        avatar => {
          dispatch(updateAvatar({ avatar }));
        },
        error => {
          if (error.response.status === 500) {
            toastr.error('Сервер недоступен');
          } else if (error.response.status === 406) {
            toastr.error(error.response.status + ' ' + error.response.statusText);
          } else {
            error.response.json().then(data => toastr.error(data.message));
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

export const getCurrentUserProfile = payload => {
  return dispatch => {
    ajaxRequest.get('/users?page=1&size=1&username=' + payload.sub + '&equal=true&sort=id,asc')
      .then(
        response => {
          dispatch(setProfile({
            ...payload,
            data: response.data[0]
          }));
        },
        error => {
          if (error.response.status === 500) {
            toastr.error('Сервер недоступен');
          } else if (error.response.status === 406) {
            toastr.error(error.response.status + ' ' + error.response.statusText);
          } else {
            error.response.json().then(data => toastr.error(data.message));
          }
        }
      );
  };
};