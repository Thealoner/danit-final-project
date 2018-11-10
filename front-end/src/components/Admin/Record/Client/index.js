import React, { Component, Fragment } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../GridEntities';
import AuthService from '../../../Login/AuthService';
import Settings from '../../../Settings';

class SimpleRecord extends Component {
  state = {
    entityType: '',
    name: '',
    simpleFields: [],
    complexFields: [],
    columns: [
      { title: 'Key', field: 'key', width: 150 },
      { title: 'Value', field: 'value', align: 'left', editor: true }
    ],
    authService: new AuthService()
  };

  getData = () => {
    let { rowId } = this.props.match.params;
    let { entityType } = this.props;
    let entity = getEntityByType(entityType);

    if (this.state.authService.loggedIn() && !this.state.authService.isTokenExpired()) {
      this.fetchEntity(entity, rowId);
    } else {
      console.log('Not logged in or token is expired');
    }
  };

  fetchEntity = (entity, entityType) => {
    const headers = {
      'Content-Type': 'application/json'
    };

    let token = this.state.authService.getToken();
    headers['Authorization'] = token;

    fetch(
      Settings.apiServerUrl + entity.apiUrl + '/' + entityType,
      { headers }
    )
      .then(this.state.authService._checkStatus)
      .then(response => response.json())
      .then(data => {
        let keys = Object.keys(data);
        let simpleFields = [];
        let complexFieldsToFetch = [];
  
        keys.forEach((key) => {
          if (Array.isArray(data[key]) || (data[key] && typeof data[key] === 'object' && data[key].constructor === Object)) {
            complexFieldsToFetch.push = {
              key: data[key]
            };
          }

          simpleFields.push({
            key: key,
            value: data[key]
          });
        });

        this.setState({
          entityType: entityType,
          simpleFields
          // ,columns: entity.columns
        });

        return complexFieldsToFetch;
      })
      .then((complexFieldsToFetch) => {
        // fetch complexFields
        complexFieldsToFetch.forEach((entityIds, entity) => {
          let entityType = getEntityByType(entity);

          fetch(
            Settings.apiServerUrl + entity.apiUrl + '/' + entityType,
            { headers }
          )
            .then(this.state.authService._checkStatus)
            .then(response => response.json())
            .then(data => {
              let complexFields = [];

              data.filter((contract) => {
                return complexFieldsToFetch.includes(contract.id);
              });

              this.setState({
                entityType: entityType,
                complexFields
                // ,columns: entity.columns
              });
            });
        });
      });
  };

  saveData = () => {
    let { entityType } = this.props;
    let entity = getEntityByType(entityType);

    if (this.state.authService.loggedIn() && !this.state.authService.isTokenExpired()) {
      const headers = {
        'Content-Type': 'application/json'
      };

      let token = this.state.authService.getToken();
      headers['Authorization'] = token;
      let array = this.state.simpleFields;
      let dataToSave = array.reduce((obj, {key, value}) => ({ ...obj, [key]: value }), {});

      fetch(
        Settings.apiServerUrl + entity.apiUrl,
        {
          method: 'PUT',
          body: JSON.stringify(dataToSave),
          headers
        }
      )
        .then(this.state.authService._checkStatus)
        .then(response => response.json())
        .then(data => {
          let keys = Object.keys(data);
          let dataArray = [];
    
          keys.forEach((key) => {
            dataArray.push({
              key: key,
              value: data[key]
            });
          });

          this.setState({
            entityType: entityType,
            data: dataArray
          });
        });
    } else {
      console.log('Not logged in or token is expired');
    }
  };

  render () {
    let { rowId } = this.props.match.params;
    let { entityType, setTabContentUrl } = this.props;
    setTabContentUrl(entityType + '/' + rowId);

    const options = {
      movableRows: true
    };

    return (
      <Fragment>
        <ReactTabulator
          ref={ref => (this.ref = ref)}
          columns={this.state.columns}
          data={this.state.simpleFields}
          rowClick={this.rowClick}
          options={options}
          data-custom-attr="test-custom-attribute"
          className="custom-css-class"
        />
        <button onClick={this.saveData}>Save</button>
      </Fragment>
    );
  }

  componentDidMount () {
    this.getData();
  }
}

export default SimpleRecord;