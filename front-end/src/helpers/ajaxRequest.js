import AuthService from './authService';

const _ajaxRequest = (url, method, body, params) => {
  const authService = new AuthService();

  if (authService.loggedIn() && !authService.isTokenExpired()) {
    const headers = {};

    if (typeof params === 'undefined') {
      headers['Content-type'] = 'application/json';
      headers['Accept'] = 'application/json';
    } else if (method === 'GET' && params === 'storage') {
      headers['Accept'] = 'image/png';
    } else if (params === 'storage') {
      headers['Accept'] = 'application/json';
    }

    headers['Authorization'] = authService.getToken();

    const options = {
      method,
      headers
    };

    if (body) {
      options.body = body;
    }

    return fetch(
      url,
      options
    )
      .then(authService._checkStatus)
      .then(response => {
        if (typeof params === 'undefined') {
          return (method !== 'DELETE') ? response.json() : Promise.resolve();
        } else if (params === 'storage' && method === 'GET') {
          return response.text();
        } else if (params === 'storage') {
          return Promise.resolve();
        }
      });
  } else {
    console.log('Not logged in or token is expired');
  }
};

const ajaxRequest = {
  get: (url, params) => {
    return _ajaxRequest(url, 'GET', undefined, params);
  },
  put: (url, body) => {
    return _ajaxRequest(url, 'PUT', body);
  },
  post: (url, body, params) => {
    return _ajaxRequest(url, 'POST', body, params);
  },
  delete: (url, body) => {
    return _ajaxRequest(url, 'DELETE', body);
  }
};

export default ajaxRequest;