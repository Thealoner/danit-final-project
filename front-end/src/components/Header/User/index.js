import React, {Component, Fragment} from 'react';
import { NavLink } from 'react-router-dom';
import './index.scss';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import ajaxRequest from "../../../helpers/ajaxRequest";
import {toastr} from "react-redux-toastr";

class User extends Component {
  state = {
    binaryData: ''
  };

  componentDidMount () {
    ajaxRequest.get('api/storage/avatar', 'storage')
      .then(data => {
        this.setState({
          binaryData: data
        });
      })
      .catch(() => {
        toastr.error('Cant get avatar image');
      });
  }

  render () {
    return (
      <Fragment>
        <NavLink to="/profile">
          <div className="user">
            <img src={`data:image/png;base64,${this.state.binaryData}`} className="user__avatar" alt="user-avatar"/>
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
}

export default User;