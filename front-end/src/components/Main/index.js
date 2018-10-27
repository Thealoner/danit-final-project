import React, {Component} from 'react';
import './index.scss';
import {Route} from 'react-router-dom';
import Home from './Home';
import Configurator from './Configurator';

class Main extends Component {
  render () {
    return (
      <main className="main">
        <Route exact path="/" component={Home} />
        <Route exact path="/configurator" component={Configurator} />
      </main>
    );
  }
}

export default Main;