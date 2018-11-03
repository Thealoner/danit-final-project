import React, { Component } from 'react';
import './index.scss';

class Package extends Component {


  render () {
    return (
      <div className="package">
        Package component. Package ID: {this.props.rowData.id}
      </div>
    );
  }
}

export default Package;