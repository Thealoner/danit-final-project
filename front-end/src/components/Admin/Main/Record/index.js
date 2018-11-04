import React, { Component } from 'react';
import './index.scss';
import Tabs from './Tabs';
import SimpleRecord from './SimpleRecord';
import Package from './Package';
import { Route } from 'react-router-dom';

class Record extends Component {
  render () {
    // let rowId = this.props.match.params.rowId;
    let rowData = this.props.location.state.rowData;
    let entityType = this.props.location.state.entityType;

    return (
      <div className="record">
        <Route path={ '/admin/:tabId/packages/:id' } component={Tabs} rowData={rowData} entityType={entityType} />
        <Route path={ '/admin/:tabId/services/:id' } component={Tabs} rowData={rowData} entityType={entityType} />
        <Route path={ '/admin/:tabId/contracts/:id' } component={Tabs} rowData={rowData} entityType={entityType} />

        <Route path={ '/admin/:tabId/packages/:id' } component={Package} rowData={rowData} entityType={entityType} />
        <Route path={ '/admin/:tabId/services/:id' } component={Package} rowData={rowData} entityType={entityType} />
        <Route path={ '/admin/:tabId/contracts/:id' } component={Package} rowData={rowData} entityType={entityType} />

        <Route path={ '/admin/:tabId/service_categories/:id' } component={SimpleRecord} rowData={rowData} entityType={entityType} />
        <Route path={ '/admin/:tabId/service_rules/:id' } component={SimpleRecord} rowData={rowData} entityType={entityType} />
        <Route path={ '/admin/:tabId/organizations/:id' } component={SimpleRecord} rowData={rowData} entityType={entityType} />
      </div>
    );
  }
}

export default Record;