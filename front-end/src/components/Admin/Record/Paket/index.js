import React, { Component } from 'react';
import './index.scss';

class Package extends Component {
  render () {
    let { rowId } = this.props.match.params;
    let { entityType } = this.props;

    return (
      <div className="paket">
        Package component. Package ID: {rowId}. EntityType={entityType}.
      </div>
    );
  }
}

export default Package;