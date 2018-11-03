import React, {Component, Fragment} from 'react';
import { Link } from 'react-router-dom';
import './index.scss';
import $ from 'jquery';
import Login from '../Login';

class Home extends Component {
  componentDidMount = () => {
    $('.header__link').on('click', function () {
      $('.login').fadeIn();
      return false;
    });
  };

  render () {
    return (
      <Fragment>
        <Login/>
        <div className="home">
          <ul>
            <li className="home__listitem"><a href="/" className="header__link">Авторизация</a></li>
            <li className="home__listitem"><Link to="/admin">Admin Panel</Link></li>
            <li className="home__listitem"><Link to="/manager">Manager Panel</Link></li>
          </ul>
        </div>
      </Fragment>
    );
  }
}

export default Home;