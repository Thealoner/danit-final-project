import decodeJWT from 'jwt-decode';

function setToken (idToken) {
  localStorage.setItem('id_token', idToken);
}

export default class AuthService {
  constructor () {
    setToken.bind(this);
  };

  login = (username, password) => {
    return this.fetchMethod('/login', {
      method: 'POST',
      body: JSON.stringify({
        username,
        password
      })
    }).then(data => {
      setToken(data);
      return Promise.resolve(data);
    });
  };

  loggedIn () {
    const token = this.getToken();
    return !!token && !this.isTokenExpired(token);
  };

  isTokenExpired (token) {
    try {
      const decoded = decodeJWT(token);
      return decoded.exp < Date.now() / 1000;
    } catch (err) {
      return false;
    }
  };

  getToken () {
    return localStorage.getItem('id_token');
  };

  logout () {
    localStorage.removeItem('id_token');
  };

  getProfile = () => {
    return decodeJWT(this.getToken());
  };

  fetchMethod = (url, options) => {
    const headers = {
      'Content-Type': 'application/json'
    };

    if (this.loggedIn()) {
      headers['Authorization'] = this.getToken();
    }

    return fetch(url, {
      headers,
      ...options
    })
      .then(this._checkStatus)
      .then(response => response.json())
      .then(data => data.Authorization);
  };

  _checkStatus (response) {
    if (response.status >= 200 && response.status < 300) {
      return response;
    } else {
      let error = new Error(response.statusText);
      error.response = response;
      throw error;
    }
  };
}