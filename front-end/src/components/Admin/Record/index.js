import React, { Component, Fragment } from 'react';
import './index.scss';
import RecordTabs from './RecordTabs';
import RecordEditor from './RecordEditor';
import Client from './Client';
import SimpleRecord from './SimpleRecord';
import Paket from './Paket';
import { Route } from 'react-router-dom';
import GridEntities from '../GridEntities';

class Record extends Component {
  getRoutes = () => {
    let { entityType } = this.props.match.params;

    return GridEntities.map((entity) => {
      let route;

      if (entity.id === 'users' || entity.id === 'services' || entity.id === 'service_categories' || entity.id === 'pakets') {
        route = (
          <Route key={entity.id} path={ '/admin/:tabKey/' + entity.id + '/:rowId' } render={
            (props) => <RecordEditor setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
          } />
        );
      } else if (entity.id === 'clients') {
        route = (
          <Route key={entity.id} path={ '/admin/:tabKey/' + entity.id + '/:rowId' } render={
            (props) => <Client setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
          } />
        );
      } else if (entity.recordType === 'simple') {
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
      }

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