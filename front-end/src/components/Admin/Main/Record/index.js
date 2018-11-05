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

    return (
      <div className="record">
        <Route path={ '/admin/:tabKey/packages/:rowId' } component={Tabs} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabKey/services/:rowId' } component={Tabs} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabKey/contracts/:rowId' } component={Tabs} rowId={rowId} entityType={entityType} />

        <Route path={ '/admin/:tabKey/packages/:rowId' } component={Package} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabKey/services/:rowId' } component={Package} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabKey/contracts/:rowId' } component={Package} rowId={rowId} entityType={entityType} />

        <Route path={ '/admin/:tabKey/service_categories/:rowId' } component={SimpleRecord} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabKey/service_rules/:rowId' } component={SimpleRecord} rowId={rowId} entityType={entityType} />
        <Route path={ '/admin/:tabKey/organizations/:rowId' } render={
            (props) => <SimpleRecord setActiveModule={this.props.setActiveModule} entityType={entityType} {...props} />
        } />
      </div>
    );
  }
}

export default Record;