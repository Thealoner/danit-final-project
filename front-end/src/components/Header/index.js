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
          <span className="header__link">Авторизация</span>
        </header>
      );
    }
}

export default Header;