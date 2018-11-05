import React, { Component, Fragment } from 'react';
import './index.scss';
import RecordTabs from './RecordTabs';
import SimpleRecord from './SimpleRecord';
import Package from './Package';
import { Route } from 'react-router-dom';
import GridEntities from '../GridEntities';

class Record extends Component {

  getRoutes = () => {
    let rowId = this.props.match.params.rowId;
    let entityType = this.props.match.params.entityType;

    return GridEntities.map((entity) => {
      let route;

      if (entity.recordType === 'simple') {
        route = (
        <Route key={entity.id} path={ '/admin/:tabKey/' + entity.id + '/:rowId' } render={
            (props) => <SimpleRecord setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
        } />
      )} else if (entity.recordType === 'tabbed') {
        route = (
          <Fragment key={entity.id}>
            <Route path={ '/admin/:tabKey/' + entity.id + '/:rowId' } component={RecordTabs} rowId={rowId} entityType={entityType} />
            <Route path={ '/admin/:tabKey/' + entity.id + '/:rowId' } component={Package} rowId={rowId} entityType={entityType} />
          </Fragment>
        )
      }
      return route;
    });
  }

  render () {
    let routes = this.getRoutes();

    return (
      <div className="record">
        {routes}
      </div>
    );
  }
}

export default Record;