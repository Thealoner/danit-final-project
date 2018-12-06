import React, { Component } from 'react';
import './index.scss';
import RecordEditor from './RecordEditor';
import Client from './Client';
import { Route } from 'react-router-dom';
import gridEntities from '../gridEntities';

class Record extends Component {
  getRoutes = () => {
    const {entityType} = this.props.match.params;

    return gridEntities.map((entity) => {
      let route;

      if (entity.id === 'clients') { // This is just a temporary workaround condition
        route = (
          <Route key={entity.id} path={'/admin/:tabKey/' + entity.id + '/:mode/:rowId?'} render={
            (props) => <Client
              setTabContentUrl={this.props.setTabContentUrl}
              entityType={entityType}
              currentTab={this.props.currentTab}
              getRecordData={this.props.getRecordData}
              setRecordData={this.props.setRecordData}
              {...props}
            />
          }/>
        );
      } else {
        route = (
          <Route key={entity.id} path={'/admin/:tabKey/' + entity.id + '/:mode/:rowId?'} render={
            (props) => <div className={entity.id}>
              <RecordEditor
                setTabContentUrl={this.props.setTabContentUrl}
                entityType={entityType}
                currentTab={this.props.currentTab}
                getRecordData={this.props.getRecordData}
                setRecordData={this.props.setRecordData}
                {...props}
              />
            </div>
          }/>
        );
      }

      return route;
    });
  };

  render () {
    return (
      <div className="record">
        {this.getRoutes()}
      </div>
    );
  }
}

export default Record;