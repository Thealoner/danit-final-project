import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './index.scss';
import { FormattedMessage } from 'react-intl';

class Home extends Component {
  render () {
    return (
      <div className="home">
        <Link to="/admin" className="home__link"><FormattedMessage id="home_admin" defaultMessage="Админ"/></Link>
        <Link to="/manager" className="home__link"><FormattedMessage id="home_manager" defaultMessage="Менеджер"/></Link>
      </div>
    );
  }
}

export default Home;