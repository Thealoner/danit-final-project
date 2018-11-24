import React, { Component } from 'react';
import './index.scss';
import RecordEditor from './RecordEditor';
import Client from './Client';
import { Route } from 'react-router-dom';
import GridEntities from '../GridEntities';

class Record extends Component {
  getRoutes = () => {
    let { entityType } = this.props.match.params;

    return GridEntities.map((entity) => {
      let route;

      if (entity.id === 'clients' || entity.id === 'contracts') { // This is just a temporary workaround condition
        route = (
          <Route key={entity.id} path={ '/admin/:tabKey/' + entity.id + '/:mode/:rowId?' } render={
            (props) => <Client setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
          } />
        );
      } else {
        route = (
          <Route key={entity.id} path={ '/admin/:tabKey/' + entity.id + '/:mode/:rowId?' } render={
            (props) => <RecordEditor setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
          } />
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