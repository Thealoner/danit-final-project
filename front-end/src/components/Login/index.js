import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';
import {Link} from 'react-router-dom';
import AuthService from './AuthService';

class Login extends Component {
  constructor () {
    super();

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.auth = new AuthService();
  }

  handleChange (e) {
    this.setState({
      [e.target.name]: e.target.value
    });
  }

  handleSubmit (e) {
    e.preventDefault();
    this.auth.login(this.state.username, this.state.password)
      .then(res => {
        this.props.history.replace('/');
      })
      .catch(err => {
        console.log(err.message);
        ('.login__data-error').fadeIn();
      });
  }

  componentWillMount () {
    if (this.auth.loggedIn()) {
      this.props.history.replace('/');
    }
  }

  render () {
    return (
      <div className="login" ref="login">
        <div className="login__dialog">
          <form action="#" className="login__form" onSubmit={this.handleSubmit}>
            <label htmlFor="username">Логин</label>
            <input type="text" name="username" id="username" placeholder="введите имя пользователя"
              value={this.state.username} onChange={this.handleChange} required/>
            <label htmlFor="password">Пароль</label>
            <input type="password" name="password" id="password" placeholder="введите пароль"
              value={this.state.password} onChange={this.handleChange} required/>
            <input type="submit" name="" value="Войти"/>
          </form>
          <div className="login__links-wrapper">
            <Link to="/forgot-password" className="login__link login__link--forgot">Забыл пароль</Link>
            <Link to="/registration" className="login__link login__link--registration">Регистрация</Link>
          </div>
          <span className="login__data-error">неверный логин или пароль</span>
        </div>
      </div>
    );
  }

    componentDidMount = () => {
      let form = $('form');
      let submitButton = $('input[type="submit"]');

      form.on('keydown', function (event) {
        if (event.keyCode === 13) {
          submitButton.css('transform', 'scale(.99)');
        }
      });

      form.on('keyup', function (event) {
        if (event.keyCode === 13) {
          submitButton.css('transform', 'none');
        }
      });

      submitButton.on('mousedown', function () {
        this.style.transform = 'scale(.99)';
      });

      submitButton.on('mouseup', function () {
        this.style.transform = 'none';
      });

      $('input:not(input:last-child)').on('focus', function () {
        $('.login__data-error').fadeOut();
      });
    };
}

export default Login;