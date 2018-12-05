import React, { Component, Fragment } from 'react';
import './index.scss';
import RecordTabs from './RecordTabs';
import SimpleRecord from './SimpleRecord';
import { Route } from 'react-router-dom';
import gridEntities from '../gridEntities';

class Record extends Component {
  getRoutes = () => {
    const { entityType } = this.props.match.params;

    return gridEntities.map((entity) => {
      let route;

      if (entity.recordType === 'simple') {
        route = (
          <Route key={entity.id} path={ '/manager/:tabKey/' + entity.id + '/:rowId' } render={
            (props) => <SimpleRecord setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
          } />
        );
      } else if (entity.recordType === 'tabbed') {
        route = (
          <Fragment key={entity.id}>
            <Route path={ '/manager/:tabKey/' + entity.id + '/:rowId' } render={
              (props) => <RecordTabs entityType={entityType} {...props} />
            } />
          </Fragment>
        );
      }

      return route;
    });
  };

  render () {
    const routes = this.getRoutes();

    return (
      <div className="record">
        {routes}
      </div>
    );
  }
}

export default Record;