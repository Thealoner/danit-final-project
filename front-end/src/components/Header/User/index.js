import React, { Fragment } from 'react';
import { NavLink } from 'react-router-dom';
import './index.scss';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import photo from './user-avatar.png';

const User = ({handleLogout, userName}) =>

  <Fragment>
    <NavLink to="/profile">
      <div className="user">
        <img src={photo} className="user__avatar" alt="user-avatar"/>
        <div className="user__info">
          <p className="user__name"> {userName}</p>
          <p className="user__position"> Менеджер продаж</p>
        </div>
      </div>
    </NavLink>
    <button className="user__btn-logout" onClick={handleLogout}>
      <FontAwesomeIcon icon="sign-out-alt" size="1x" title="Выйти"/>
    </button>
  </Fragment>;

export default User;