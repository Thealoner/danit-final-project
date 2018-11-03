import React, {Component, Fragment} from 'react';
import './index.scss';
import Main from './Main';
import Header from './Header';

class Menu extends Component {
  render () {
    return (
      <Fragment>
        <Header/>
        <Main/>
      </Fragment>
    );
  }
}

export default Menu;