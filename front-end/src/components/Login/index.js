import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Login extends Component {
    componentDidMount = () => {
      $('.login__close').on('click', function () {
        $('.login').fadeOut();
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
                <span>Forgot Password</span>
              </form>
            </div>
          </div>
        </div>
      );
    }
}

export default Login;