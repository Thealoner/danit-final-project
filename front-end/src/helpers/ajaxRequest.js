import Settings from '../components/Settings';
import AuthService from './authService';

const _ajaxRequest = (relativeUrl = '', method = 'GET', body = null) => {
  const authService = new AuthService();

  if (authService.loggedIn() && !authService.isTokenExpired()) {
    const headers = {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    };

    headers['Authorization'] = authService.getToken();

    const options = {
      methods,
      headers
    };

    if (body !== null) {
      options.body = body;
    }

    return fetch(
      Settings.apiServerUrl + relativeUrl,
      options
    )
      .then(authService._checkStatus)
      .then(response => response.json());
  } else {
    console.log('Not logged in or token is expired');
  }
};

const ajaxRequest = {
  get: (url = '') => {
    return _ajaxRequest (url = '', method = 'GET');
  },
  put: (url = '', body = null) => {
    return _ajaxRequest (url = '', method = 'PUT', body = null);
  },
  post: (url = '', body = null) => {
    return _ajaxRequest (url = '', method = 'POST', body = null);
  },
  delete: (url = '', body = null) => {
    return _ajaxRequest(url = '', method = 'DELETE', body = null);
  }
}

export default ajaxRequest;