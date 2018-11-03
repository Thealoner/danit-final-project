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

    return (
      <div className="record">
        <Route path={ '/admin/packages/:id' } component={Tabs} rowData={rowData} />
        <Route path={ '/admin/packages/:id' } component={Package} rowData={rowData} />
        <Route path={ '/admin/organizations/:id' } component={SimpleRecord} rowData={rowData} />
      </div>
    );
  }
}

export default Record;