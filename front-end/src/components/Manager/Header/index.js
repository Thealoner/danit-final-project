import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Header extends Component {
    componentDidMount = () => {
      $('.header__link').on('click', function () {
        $('.login').fadeIn();
        return false;
      });
    };

    render () {
      return (
        <header className="header">
          <div className="header__container">
            <a href="/" className="header__logo">LOGO</a>
            <span>Tabs will be here</span>
            <a href="/" className="header__link">Авторизация</a>
          </div>
        </header>
      );
    }
}

export default Header;