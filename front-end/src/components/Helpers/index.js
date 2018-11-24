import Settings from '../Settings';
import AuthService from '../Login/AuthService';

const ajaxRequest = (relativeUrl = '', method = 'GET', body = null) => {
  let authService = new AuthService();

  if (authService.loggedIn() && !authService.isTokenExpired()) {
    const headers = {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    };

    let token = authService.getToken();
    headers['Authorization'] = token;

    let options = {
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