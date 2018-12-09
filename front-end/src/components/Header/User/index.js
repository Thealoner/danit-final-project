import React, {Component} from 'react';
import './index.scss';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import photo from './user-avatar.png';

class User extends Component {
  render () {
    const {handleLogout, userName} = this.props;
    return (
      <div className="user">
        <img src={photo} className="user__avatar" title={userName} alt="user-avatar"/>
        <div className="user__info">
          <p className="user__name"> {userName}</p>
          <p className="user__position"> Менеджер продаж</p>
        </div>
        <button className="user__btn-logout" onClick={handleLogout}>
          <FontAwesomeIcon icon="sign-out-alt" size="1x"/>
        </button>
      </div>
    );
  }
}

export default User;