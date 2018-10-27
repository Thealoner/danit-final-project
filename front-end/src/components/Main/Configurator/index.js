import React, {Component} from 'react';
import './index.scss';
import Packages from './Packages';

class Body extends Component {
  render () {
    return (
      <div className="configurator">
        <div className="configurator__left">
        </div>
        <div className="configurator__right">
          <Packages/>
        </div>
      </div>
    );
  }
}

export default Body;