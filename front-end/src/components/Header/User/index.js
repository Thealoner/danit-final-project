import React, {Component} from 'react';
import './index.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { FormattedMessage } from 'react-intl';
import {connect} from 'react-redux';
import { setLocale } from '../../../actions/localeAction';
import PropTypes from 'prop-types';

class User extends Component {
  render () {
    const {handleLogout, userName} = this.props;

    return (
      <div className="user">
        <FontAwesomeIcon className="user__avatar" icon="user-circle" size="2x" title={userName}/>
        <div className="user__wrapper">
          <div className="user__intl">
            <span className={`user__intl-switcher${localStorage.lang === 'en' ? ' user__intl-switcher--active' : ''}`}
              onClick={() => {
                this.props.setLocale('en');
                this.forceUpdate();
              }}>en</span>|
            <span className={`user__intl-switcher${localStorage.lang === 'ru' ? ' user__intl-switcher--active' : ''}`}
              onClick={() => {
                this.props.setLocale('ru');
                this.forceUpdate();
              }}>ru</span>
          </div>
          <button type="button" className="user__btn-logout" onClick={handleLogout}>
            <FormattedMessage id="user_logout" defaultMessage="Выйти"/>
          </button>
        </div>
      </div>
    );
  }
}

User.propTypes = {
  setLocale: PropTypes.func.isRequired
};

export default connect(null, {setLocale})(User);