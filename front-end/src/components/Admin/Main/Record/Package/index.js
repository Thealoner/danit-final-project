import React, { Component } from 'react';
import './index.scss';

class Package extends Component {
  render () {
    let data = this.props.location.state.rowData;
    let entityType = this.props.location.state.entityType;

    return (
      <div className="package">
        Package component. Package ID: {data.id}. EntityType={entityType}.
      </div>
    );
  }
}

export default Package;