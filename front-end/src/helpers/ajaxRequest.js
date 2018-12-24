import Settings from '../components/Settings';
import AuthService from './authService';

const _ajaxRequest = (url, method, body) => {
  const authService = new AuthService();

  if (authService.loggedIn() && !authService.isTokenExpired()) {
    const headers = {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    };

    headers['Authorization'] = authService.getToken();

    const options = {
      method,
      headers
    };

    if (body) {
      options.body = body;
    }

    return fetch(
      Settings.apiServerUrl + url,
      options
    )
      .then(authService._checkStatus)
      .then(response => response.json());
  } else {
    console.log('Not logged in or token is expired');
  }
};

const ajaxRequest = {
  get: (url) => {
    return _ajaxRequest(url, 'GET');
  },
  put: (url, body) => {
    return _ajaxRequest(url, 'PUT', body);
  },
  post: (url, body) => {
    return _ajaxRequest(url, 'POST', body);
  },
  delete: (url, body) => {
    return _ajaxRequest(url, 'DELETE', body);
  }
}

export default ajaxRequest;