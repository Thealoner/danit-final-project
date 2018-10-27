import React, {Component} from 'react';
import './index.scss';
import Menu from './Menu';

class Footer extends Component {
  render () {
    return (
      <footer className="footer">
        <Menu/>
      </footer>
    );
  }
}

export default Footer;