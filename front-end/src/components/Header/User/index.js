import React, {Component, Fragment} from 'react';
import { NavLink } from 'react-router-dom';
import './index.scss';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import { connect } from 'react-redux';
import { getAvatar } from '../../../actions/userActions';
import defaultAvatar from '../../Profile/default-avatar.png';

class User extends Component {
  render () {
    const { user } = this.props;
    return (
      <Fragment>
        <NavLink to="/profile">
          <div className="user">
            <img src={user.avatar ? 'data:image/png;base64,' + user.avatar : defaultAvatar} className="user__avatar" alt="user-avatar"/>
            <div className="user__info">
              <p className="user__name"> {this.props.userName}</p>
              <p className="user__position"> Менеджер продаж</p>
            </div>
          </div>
        </NavLink>
        <button className="user__btn-logout" onClick={this.props.handleLogout}>
          <FontAwesomeIcon icon="sign-out-alt" size="1x" title="Выйти"/>
        </button>
      </Fragment>
    );
  };

  componentDidMount () {
    this.props.getAvatar();
  }
}

const mapStateToProps = state => {
  return {
    user: state.user
  };
};

const mapDispatchToProps = dispatch => {
  return {
    getAvatar: () => {
      dispatch(getAvatar());
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(User);