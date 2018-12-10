import React, {Component} from 'react';
import './index.scss';
import AuthService from '../../helpers/authService';
import {toastr} from 'react-redux-toastr';

class Login extends Component {
  constructor (props) {
    super(props);
    this.Auth = new AuthService();

    this.state = {
      username: '',
      password: ''
    };
  }

  handleChange = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();

    this.Auth.login(this.state.username, this.state.password)
      .then(res => {
        this.props.history.replace('/');
      })
      .catch(() => {
        toastr.error('Неверный логин или пароль');
      });
  };

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
              placeholder="введите логин (Admin)"
              required/>
            <label htmlFor="password" className="login__label">Пароль</label>
            <input type="password" className="login__input" ref={password => (this.password = password)} name="password"
              id="password" value={this.state.password} onChange={this.handleChange}
              placeholder="введите пароль (1234)"
              required/>
            <input type="submit" className="login__input" ref={submitBtn => (this.submitBtn = submitBtn)}
              name="" value="Войти"/>
          </form>
        </div>
      </div>
    );
  };

  componentDidMount = () => {
    const form = this.form;
    const submitBtn = this.submitBtn;

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
  };
}

export default Login;