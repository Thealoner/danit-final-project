import React, { Component } from 'react';
import './index.scss';
import Tabs from './Tabs';
import SimpleRecord from './SimpleRecord';
import Package from './Package';
import { Route } from 'react-router-dom';

class Record extends Component {
  render () {
    let rowId = this.props.match.params.rowId;
    let entityType = this.props.match.params.entityType;
    // let entityType = this.props.location.state.entityType;

    return (
      <div className="record">
        <Route path={ '/admin/:tabId/packages/:id' } component={Tabs} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabId/services/:id' } component={Tabs} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabId/contracts/:id' } component={Tabs} rowId={rowId} entityType={entityType} />

        <Route path={ '/admin/:tabId/packages/:id' } component={Package} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabId/services/:id' } component={Package} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabId/contracts/:id' } component={Package} rowId={rowId} entityType={entityType} />

        <Route path={ '/admin/:tabId/service_categories/:id' } component={SimpleRecord} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabId/service_rules/:id' } component={SimpleRecord} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabId/organizations/:id' } component={SimpleRecord} rowId={rowId} entityType={entityType} />
      </div>
    );
  }
}

export default Record;