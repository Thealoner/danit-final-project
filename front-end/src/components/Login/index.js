import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Login extends Component {
    componentDidMount = () => {
      $('.login__close').on('click', function () {
        $('.login').fadeOut();
      });
      $('.login__forgotPass').on('click', function () {
        $('.forgot').fadeIn(0);
        $('.login').fadeOut(0);
      });
    };

    render () {
      return (
        <div className="login">
          <div className="login__dialog">
            <span className="login__close">&times;</span>
            <div className="login__wrapper">
              <form action="#" className="login__form">
                <label htmlFor="username">Username</label>
                <input type="text" name="" id="username" placeholder="username" autoComplete="off"/>
                <label htmlFor="password">Password</label>
                <input type="password" name="" id="password" placeholder="password"/>
                <input type="submit" name="" value="Sign In"/>
                <div className="login__spans">
                  <span className="login__forgotPass">Forgot Password</span>
                  <span className="login__register">Register</span>
                </div>

              </form>
            </div>
          </div>
        </div>
      );
    }
}

export default Login;