import React, { Component } from 'react';
import './index.scss';
import RecordEditor from './RecordEditor';
import Client from './Client';
import { Route } from 'react-router-dom';
import GridEntities from '../GridEntities';
import autoSize from 'autosize';

class Record extends Component {
  getRoutes = () => {
    let {entityType} = this.props.match.params;

    return GridEntities.map((entity) => {
      let route;

      if (entity.id === 'clients' || entity.id === 'contracts') { // This is just a temporary workaround condition
        route = (
          <Route key={entity.id} path={'/admin/:tabKey/' + entity.id + '/:mode/:rowId?'} render={
            (props) => <Client setTabContentUrl={this.props.setTabContentUrl} entityType={entityType} {...props} />
          }/>
        );
      } else {
        route = (
          <Route key={entity.id} path={'/admin/:tabKey/' + entity.id + '/:mode/:rowId?'} render={
            (props) => <div className={entity.id}><RecordEditor setTabContentUrl={this.props.setTabContentUrl}
              entityType={entityType} {...props} /></div>
          }/>
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

  componentDidMount () {
    let textareas = document.getElementsByTagName('textarea');
    autoSize(textareas);

    for (let i = 0; i < textareas.length; i++) {
      textareas[i].style.height = textareas[i].scrollHeight + 50 + 'px';
    }
  }
}

export default Record;