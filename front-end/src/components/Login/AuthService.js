import decodeJWT from 'jwt-decode';

export default class AuthService {
  constructor (domain) {
    this.domain = domain || 'http://localhost:9000';
    this.fetch = this.fetch.bind(this);
    this.login = this.login.bind(this);
    this.getProfile = this.getProfile.bind(this);
  }

  login (username, password) {
    return this.fetch(`${this.domain}/login`, {
      method: 'POST',
      body: JSON.stringify({
        username,
        password
      })
    }).then(res => {
      console.log('received token: ' + res.token);
      this.setToken(res.token);
      return Promise.resolve(res);
    });
  }

  loggedIn () {
    const token = this.getToken();
    return !!token && !this.isTokenExpired(token);
  }

  isTokenExpired (token) {
    try {
      const decoded = decodeJWT(token);
      return decoded.exp < Date.now() / 1000;
    } catch (err) {
      return false;
    }
  }

  setToken (idToken) {
    console.log("setToken" + idToken);
    localStorage.setItem('id_token', idToken);
  }

  getToken () {
    console.log("getToken" + localStorage.getItem('id_token'));
    return localStorage.getItem('id_token');
  }

  logout () {
    console.log('logout')
    localStorage.removeItem('id_token');
  }

  getProfile () {
    return decodeJWT(this.getToken());
  }

  fetch (url, options) {
    const headers = {
      'Content-Type': 'application/json'
    };

    if (this.loggedIn()) {
      console.log('logged in')
      headers['Authorization'] = this.getToken();
    }

    return fetch(url, {
      headers,
      ...options
    })
      .then(this._checkStatus)
      .then(response => {
        console.log(response.headers);
        //response.json()
      });
  }

  _checkStatus (response) {
    if (response.status >= 200 && response.status < 300) {
      return response;
    } else {
      let error = new Error(response.statusText);
      error.response = response;
      throw error;
    }
  }
}