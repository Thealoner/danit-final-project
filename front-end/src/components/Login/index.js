import React, {Component} from 'react';
import './index.scss';
import AuthService from './AuthService';
import Locale from '../Header/Locale';
import { IntlProvider, FormattedMessage } from 'react-intl';
import messages from '../../messages';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

class Login extends Component {
  constructor () {
    super();
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.Auth = new AuthService();

    this.state = {
      username: '',
      password: ''
    };
  }

  handleChange (e) {
    this.setState({
      [e.target.name]: e.target.value
    });
  }

  handleSubmit (e) {
    e.preventDefault();
    this.Auth.login(this.state.username, this.state.password)
      .then(res => {
        this.props.history.replace('/');
      })
      .catch(err => {
        console.log(err);
        this.error.style.display = 'initial';
      });
  }

  componentWillMount () {
    if (this.Auth.loggedIn()) {
      this.props.history.replace('/');
    }
  }

  form = React.createRef();
  submitBtn = React.createRef();
  username = React.createRef();
  password = React.createRef();
  error = React.createRef();

  render () {
    const {lang} = this.props;
    return (
      <IntlProvider locale={lang} messages={messages[lang]}>
        <div className="login" ref="login">
          <div className="login__locale"><Locale/></div>
          <div className="login__dialog">
            <form action="#" className="login__form" ref={form => (this.form = form)} onSubmit={this.handleSubmit}>
              <label htmlFor="username" className="login__label">
                <FormattedMessage id="login_login" defaultMessage="Логин"/>
              </label>
              <input type="text" className="login__input" ref={username => (this.username = username)}
                name="username" id="username" value={this.state.username} onChange={this.handleChange} required/>
              <label htmlFor="password" className="login__label">
                <FormattedMessage id="login_password" defaultMessage="Пароль"/>
              </label>
              <input type="password" className="login__input" ref={password => (this.password = password)}
                name="password" id="password" value={this.state.password} onChange={this.handleChange} required/>
              <input type="submit" className="login__input" ref={submitBtn => (this.submitBtn = submitBtn)}
                name="" value="Войти"/>
            </form>
            <span className="login__data-error" ref={error => (this.error = error)}>
              <FormattedMessage id="login_wrong-data" defaultMessage="неверный логин или пароль"/>
            </span>
          </div>
        </div>
      </IntlProvider>
    );
  }

  componentDidMount = () => {
    let form = this.form;
    let username = this.username;
    let password = this.password;
    let submitBtn = this.submitBtn;
    let error = this.error;

    if (localStorage.lang === 'en') {
      submitBtn.value = 'Sign in';
      username.setAttribute('placeholder', 'insert username (Admin)');
      password.setAttribute('placeholder', 'insert password (1234)');
    } else {
      submitBtn.value = 'Войти';
      username.setAttribute('placeholder', 'введите имя пользователя (Admin)');
      password.setAttribute('placeholder', 'введите пароль (1234)');
    }

    form.onkeydown = function (event) {
      if (event.keyCode === 13) {
        submitBtn.style.transform = 'scale(.99)';
      }
    };

    form.onkeyup = function (event) {
      if (event.keyCode === 13) {
        submitBtn.style.transform = 'none';
      }
    };

    submitBtn.onmousedown = function () {
      this.style.transform = 'scale(.99)';
    };

    submitBtn.onmouseup = function () {
      this.style.transform = 'none';
    };

    username.onfocus = function () {
      error.style.display = 'none';
    };

    password.onfocus = function () {
      error.style.display = 'none';
    };
  };

  componentDidUpdate () {
    let username = this.username;
    let password = this.password;
    let submitBtn = this.submitBtn;

    if (localStorage.lang === 'en') {
      submitBtn.value = 'Sign in';
      username.setAttribute('placeholder', 'insert username (Admin)');
      password.setAttribute('placeholder', 'insert password (1234)');
    } else {
      submitBtn.value = 'Войти';
      username.setAttribute('placeholder', 'введите имя пользователя (Admin)');
      password.setAttribute('placeholder', 'введите пароль (1234)');
    }
  }
}

Login.propTypes = {
  lang: PropTypes.string.isRequired
};

function mapStateToProps (state) {
  return {
    lang: state.locale.lang
  };
}

export default connect(mapStateToProps)(Login);