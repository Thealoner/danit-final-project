import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Login extends Component {
  componentDidMount = () => {
    let self = this;
    $('.login__close').on('click', function () {
      $('.login').fadeOut();
    });

    $('.login__link').on('click', function () {
      $('.login').fadeOut(0);
      return false;
    });

    $('.login__link--forgot').on('click', function () {
      $('.forgot').fadeIn(0);
    });

    $(window).on('click', function (event) {
      if (event.target === self.refs.login) {
        $('.login').fadeOut();
      }
    });
  };

  render () {
    return (
      <div className="login" ref="login">
        <div className="login__dialog">
          <span className="login__close">&times;</span>
          <form action="#" className="login__form">
            <label htmlFor="username">Username</label>
            <input type="text" name="" id="username" placeholder="username"/>
            <label htmlFor="password">Password</label>
            <input type="password" name="" id="password" placeholder="password"/>
            <input type="submit" name="" value="Sign In"/>
          </form>
          <div className="login__links-wrapper">
            <a href="/" className="login__link login__link--forgot">Forgot Password</a>
            <a href="/" className="login__link login__link--register">Register</a>
          </div>
        </div>
      </div>
    );
  }
}

export default Login;