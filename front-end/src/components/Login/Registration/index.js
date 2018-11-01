import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Registration extends Component {
    componentDidMount = () => {
      $('.registration__close').on('click', function () {
        $('.registration').fadeOut();
      });
    };

    render () {
      return (
        <div className="registration">
          <div className="registration__dialog">
            <span className="registration__close">&times;</span>
            <div className="registration__wrapper">
              <form action="#" className="registration__form">
                <label htmlFor="username">Логин</label>
                <input type="text" name="" id="username" placeholder="Логин" autoComplete="off"/>
                <label htmlFor="name">Имя</label>
                <input type="text" name="" id="name" placeholder="Имя" autoComplete="off"/>
                <label htmlFor="password">Пароль</label>
                <input type="password" name="" id="password" placeholder="Пароль"/>
                <label htmlFor="repeat_password">Повторите пароль</label>
                <input type="password" name="" id="repeat_password" placeholder="Потворите пароль"/>
                <input type="submit" name="" value="Зарегистрироваться"/>
              </form>
            </div>
          </div>
        </div>
      );
    }
}

export default Registration;