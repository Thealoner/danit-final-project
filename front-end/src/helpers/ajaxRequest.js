import Settings from '../components/Settings';
import AuthService from './authService';

const ajaxRequest = (relativeUrl = '', method = 'GET', body = null) => {
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

export default ajaxRequest;