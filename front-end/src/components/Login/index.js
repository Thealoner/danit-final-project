import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';
import axios from 'axios';

class Login extends Component {
  constructor () {
    super();

    this.state = {
      username: '',
      password: ''
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange (e) {
    let target = e.target;
    let value = target.value;
    let name = target.name;

    this.setState({
      [name]: value
    });
  }

  handleSubmit (e) {
    e.preventDefault();

    axios.post('http://localhost:9000/api/clients/all', this.state)
      .then(res => {
        console.log(res);

        if (res) {
          $('.login').fadeOut(0);
          // some other logic
        } else {
          $('.login__data-error').show();
        }
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  render () {
    return (
      <div className="login" ref="login">
        <div className="login__dialog">
          <span className="login__close">&times;</span>

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
            <a href="/" className="login__link login__link--forgot">Забыл пароль</a>
            <a href="/" className="login__link login__link--registration">Регистрация</a>
          </div>
          <span className="login__data-error">неверный логин или пароль</span>
        </div>
      </div>
    );
  }

    componentDidMount = () => {
      let self = this;
      let loginPage = $('.login');
      $('.login__close').on('click', function () {
        loginPage.fadeOut();
      });

      $('.login__link').on('click', function () {
        loginPage.fadeOut(0);
        return false;
      });

      $('.login__link--forgot').on('click', function () {
        $('.forgot').fadeIn(0);
      });

      $('.login__link--registration').on('click', function () {
        $('.registration').fadeIn(0);
      });

      $(window).on('click', function (event) {
        if (event.target === self.refs.login) {
          loginPage.fadeOut();
        }
      });

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
        $('.login__data-error').hide();
      });
    };
}

export default Login;