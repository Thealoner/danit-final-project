import React, {Component} from 'react';
import './index.scss';
import $ from 'jquery';

class Header extends Component {
    componentDidMount = () => {
      $('.header__link').on('click', function () {
        $('.login').fadeIn();
      });
    };

    render () {
      return (
        <header className="header">
          <a className="header__link">Авторизация</a>
        </header>
      );
    }
}

export default Header;