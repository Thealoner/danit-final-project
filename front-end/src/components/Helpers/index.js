import Settings from '../Settings';
import AuthService from '../Login/AuthService';

const ajaxRequest = (relativeUrl = '', method = 'GET', body = null) => {
  const authService = new AuthService();

  if (authService.loggedIn() && !authService.isTokenExpired()) {
    const headers = {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    };

    const token = authService.getToken();
    headers['Authorization'] = token;

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

export const resizeInput = (el) => {
  const events = 'keyup,keypress,focus,blur,change,input'.split(',');
  const spanEl = document.createElement('span');
  spanEl.className = 'span-helper';
  spanEl.innerHTML = el.value;
  el.after(spanEl);
  el.style.width = spanEl.clientWidth + 1 + 'px';
  spanEl.remove();

  events.forEach(function (item) {
    el.addEventListener(item, function () {
      const spanEl = document.createElement('span');
      spanEl.className = 'span-helper';
      spanEl.innerHTML = el.value;
      el.after(spanEl);
      el.style.width = spanEl.clientWidth + 1 + 'px';
      spanEl.remove();
    });
  });
};

export default ajaxRequest;