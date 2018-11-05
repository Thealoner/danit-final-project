import React, {Component} from 'react';
import './index.scss';

class Registration extends Component {
  render () {
    return (
      <div className="registration">
        <div className="registration__dialog">
          <form action="#" className="registration__form">
            <label htmlFor="username_reg">Логин</label>
            <input type="text" name="" id="username_reg" placeholder="Логин" autoComplete="off"/>
            <label htmlFor="name">Имя</label>
            <input type="text" name="" id="name" placeholder="Имя" autoComplete="off"/>
            <label htmlFor="password_reg">Пароль</label>
            <input type="password" name="" id="password_reg" placeholder="Пароль"/>
            <label htmlFor="repeat_password">Повторите пароль</label>
            <input type="password" name="" id="repeat_password" placeholder="Потворите пароль"/>
            <input type="submit" name="" value="Зарегистрироваться"/>
          </form>
        </div>
      </div>
    );
  }
}

export default Registration;