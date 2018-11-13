import React, {Component} from 'react';
import './index.scss';
import {connect} from 'react-redux';
import { setLocale } from '../../../actions/localeAction';
import PropTypes from 'prop-types';

class Locale extends Component {
  render () {
    const {setLocale} = this.props;

    return (
      <div className="locale">
        <span className={`locale__switcher${localStorage.lang === 'en' ? ' locale__switcher--active' : ''}`}
          onClick={() => {
            setLocale('en');
            this.forceUpdate();
          }}>EN</span>|
        <span className={`locale__switcher${localStorage.lang === 'ru' ? ' locale__switcher--active' : ''}`}
          onClick={() => {
            setLocale('ru');
            this.forceUpdate();
          }}>RU</span>
      </div>
    );
  }
}

Locale.propTypes = {
  setLocale: PropTypes.func.isRequired
};

export default connect(null, {setLocale})(Locale);