import React, { Component } from 'react';
import './index.scss';
// import RecordTabs from './RecordTabs';
import RecordEditor from './RecordEditor';
import Client from './Client';
// import SimpleRecord from './SimpleRecord';
// import Paket from './Paket';
import { Route } from 'react-router-dom';
import GridEntities from '../GridEntities';

class Record extends Component {
  getRoutes = () => {
    let { entityType } = this.props.match.params;

    return GridEntities.map((entity) => {
      let route;

      if (entity.id === 'clients' || entity.id === 'contracts') { // This is just a temporary workaround condition
        route = (
          <Route key={entity.id} path={ '/admin/:tabKey/' + entity.id + '/:rowId' } render={
            (props) => <Client setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
          } />
        );
      } else {
        route = (
          <Route key={entity.id} path={ '/admin/:tabKey/' + entity.id + '/:rowId' } render={
            (props) => <RecordEditor setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
          } />
        );
      }
      // TODO: I will remove this in the next branch, once we're sure we won't need this.
      // It is simply getting too complicated to remove/fix it right now.

      /* if (entity.recordType === 'simple') {
        route = (
          <Route key={entity.id} path={ '/admin/:tabKey/' + entity.id + '/:rowId' } render={
            (props) => <SimpleRecord setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
          } />
        );
      } else if (entity.recordType === 'tabbed') {
        route = (
          <Fragment key={entity.id}>
            <Route path={ '/admin/:tabKey/' + entity.id + '/:rowId' } render={
              (props) => <RecordTabs entityType={entityType} {...props} />
            } />
            <Route path={ '/admin/:tabKey/' + entity.id + '/:rowId' } render={
              (props) => <Paket entityType={entityType} {...props} />
            } />
          </Fragment>
        );
      } */

      return route;
    });
  };

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