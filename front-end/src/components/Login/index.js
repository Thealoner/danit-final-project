import React, {Component} from 'react';
import './index.scss';
import AuthService from './AuthService';
import {connect} from 'react-redux';
import {LOADING_STATUS} from '../../actions/types';
import {toastr} from 'react-redux-toastr';

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

    this.props.dispatch({
      type: LOADING_STATUS
    });

    this.Auth.login(this.state.username, this.state.password)
      .then(res => {
        this.props.history.replace('/');

        setTimeout(() => {
          this.props.dispatch({
            type: LOADING_STATUS
          });
        }, 1000);
      })
      .catch(err => {
        toastr.error(err);
        this.error.classList.add('login__data-error--visible');
      });
  }

  UNSAFE_componentWillMount () {
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
    return (
      <div className="login">
        <div className="login__dialog">
          <form action="#" className="login__form" ref={form => (this.form = form)} onSubmit={this.handleSubmit}>
            <label htmlFor="username" className="login__label">Логин</label>
            <input type="text" className="login__input" ref={username => (this.username = username)} name="username"
              id="username" value={this.state.username} onChange={this.handleChange}
              placeholder="insert username (Admin)"
              required/>
            <label htmlFor="password" className="login__label">Пароль</label>
            <input type="password" className="login__input" ref={password => (this.password = password)} name="password"
              id="password" value={this.state.password} onChange={this.handleChange}
              placeholder="введите пароль (1234)"
              required/>
            <div className="login__remember">
              <input type="checkbox"/>
              <label>Запомнить меня</label>
            </div>
            <input type="submit" className="login__input" ref={submitBtn => (this.submitBtn = submitBtn)}
              name="" value="Войти"/>
          </form>
          <span className="login__data-error" ref={error => (this.error = error)}>неверный логин или пароль</span>
        </div>
      </div>
    );
  }

  componentDidMount = () => {
    const form = this.form;
    const username = this.username;
    const password = this.password;
    const submitBtn = this.submitBtn;
    const error = this.error;

    form.onkeydown = function (event) {
      if (event.keyCode === 13) {
        submitBtn.classList.add('login__input--active');
      }
    };

    form.onkeyup = function (event) {
      if (event.keyCode === 13) {
        submitBtn.classList.remove('login__input--active');
      }
    };

    submitBtn.onmousedown = function () {
      this.classList.add('login__input--active');
    };

    submitBtn.onmouseup = function () {
      this.classList.remove('login__input--active');
    };

    username.onfocus = function () {
      error.classList.remove('login__data-error--visible');
    };

    password.onfocus = function () {
      error.classList.remove('login__data-error--visible');
    };
  };
}

export default connect()(Login);