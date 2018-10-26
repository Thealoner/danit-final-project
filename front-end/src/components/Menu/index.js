import React from 'react';
import {Link} from 'react-router-dom';
import './index.scss';

const Menu = () => (
  <div className="Menu">
    <ul>
      <li>
        <Link to="/">Главная</Link>
      </li>
      <li>
        <Link to="/packages">Пакеты</Link>
      </li>
    </ul>
  </div>
);

export default Menu;