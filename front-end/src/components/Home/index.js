import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import './index.scss';

class Home extends Component {
  render () {
    return (
      <div className="home">
        <ul>
          <li className="home__listitem"><Link to="/admin">Admin Panel</Link></li>
          <li className="home__listitem"><Link to="/manager">Manager Panel</Link></li>
        </ul>
      </div>
    );
  }
}

export default Home;