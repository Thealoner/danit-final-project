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
    data: [],
    columns: [
      { title: 'Key', field: 'key', width: 150 },
      { title: 'Value', field: 'value', align: 'left', editor: true }
    ]
  };

  getData = () => {
    let { rowId } = this.props.match.params;
    let { entityType } = this.props;
    let entity = getEntityByType(entityType);
    let authService = new AuthService();

    if (authService.loggedIn() && !authService.isTokenExpired()) {
      const headers = {
        'Content-Type': 'application/json'
      };

      let token = authService.getToken();
      headers['Authorization'] = token;

      fetch(
        Settings.apiServerUrl + entity.apiUrl + '/' + rowId,
        { headers }
      )
        .then(authService._checkStatus)
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
            // ,columns: entity.columns
          });
        });
    } else {
      console.log('Not logged in or token is expired');
    }
  };

  saveData = () => {
    let { rowId } = this.props.match.params;
    let { entityType } = this.props;
    let entity = getEntityByType(entityType);
    let authService = new AuthService();

    if (authService.loggedIn() && !authService.isTokenExpired()) {
      const headers = {
        'Content-Type': 'application/json'
      };

      let token = authService.getToken();
      headers['Authorization'] = token;
      let array = this.state.data;
      let dataToSave = array.reduce((obj, {key, value}) => ({ ...obj, [key]: value }), {});

      fetch(
        Settings.apiServerUrl + entity.apiUrl,
        { 
          method: 'PUT',
          body: JSON.stringify(dataToSave),
          headers
        }
      )
        .then(authService._checkStatus)
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
          data={this.state.data}
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