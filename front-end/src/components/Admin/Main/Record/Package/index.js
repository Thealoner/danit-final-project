import React, { Component } from 'react';
import './index.scss';

class Package extends Component {
  render () {
    // let rowId = this.props.location.state.rowId;
    // let entityType = this.props.location.state.entityType;
    let rowId = this.props.match.params.rowId;
    let entityType = this.props.match.params.entityType;

    return (
      <div className="package">
        Package component. Package ID: {rowId}. EntityType={entityType}.
      </div>
    );
  }
}

export default Package;