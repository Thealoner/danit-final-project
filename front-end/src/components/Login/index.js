import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';
import AuthService from './AuthService';

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
        console.log(err.message);
        $('.login__data-error').fadeIn();
      });
  }

  componentWillMount () {
    if (this.Auth.loggedIn()) {
      this.props.history.replace('/');
    }
  }

  render () {
    return (
      <div className="login" ref="login">
        <div className="login__dialog">
          <span className="login__data-error">пока запускаем JWTserver_test.js и тестим</span>
          <form action="#" className="login__form" onSubmit={this.handleSubmit}>
            <label htmlFor="username">Логин</label>
            <input type="text" name="username" id="username" placeholder="введите имя пользователя (User)"
              value={this.state.username} onChange={this.handleChange} required/>
            <label htmlFor="password">Пароль</label>
            <input type="password" name="password" id="password" placeholder="введите пароль (1234)"
              value={this.state.password} onChange={this.handleChange} required/>
            <input type="submit" name="" value="Войти"/>
          </form>
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