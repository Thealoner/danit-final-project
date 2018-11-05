import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './index.scss';

class Home extends Component {
  render () {
    return (
      <div className="home">
        <Link to="/login" className="home__link">Авторизация</Link>
        <Link to="/admin" className="home__link">Админ</Link>
        <Link to="/manager" className="home__link">Менеджер</Link>
      </div>
    );
  }
}

export default Home;