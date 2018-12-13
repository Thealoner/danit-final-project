import React from 'react';
import {Link} from 'react-router-dom';
import './index.scss';

const UserNavigation = () =>
  <div className='user-navigation'>
    <Link to='/admin' className='user-navigation__link'>Админ</Link>
    <Link to='/manager' className='user-navigation__link'>Менеджер</Link>
  </div>;

export default UserNavigation;