import React, { Component } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../GridEntities';
import AuthService from '../../Login/AuthService';
import Settings from '../../Settings';

class Grid extends Component {
  state = {
    id: '',
    name: '',
    data: [],
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Дата Рождения', field: 'birthDate' },
      { title: 'Имя', field: 'firstName' },
      { title: 'Фамилия', field: 'lastName' },
      { title: 'Пол', field: 'gender' },
      { title: 'Активен', field: 'active' }
    ]
  };
  ref = null;

  rowClick = (e, row) => {
    let { entityType, tabKey } = this.props.match.params;
    
    this.props.setTabContentUrl(entityType + '/' + row.getData().id);
    
    this.props.history.push({
      pathname: '/manager/' + tabKey + '/' + entityType + '/' + row.getData().id,
      state: {
        rowData: row.getData(),
        entityType: entityType
      }
    });
  };

  getData = () => {
    let { entityType } = this.props.match.params;
    let entity = getEntityByType(entityType);
    let authService = new AuthService();

    if (authService.loggedIn() && !authService.isTokenExpired()) {
      const headers = {
        'Content-Type': 'application/json'
      };

      let token = authService.getToken();
      headers['Authorization'] = token;

      fetch(
        Settings.apiServerUrl + entity.apiUrl,
        { headers }
      )
        .then(authService._checkStatus)
        .then(response => response.json())
        .then(data => {
          this.props.setTabContentUrl(entity.id);
          
          this.setState({
            id: entityType,
            data: data
            // ,columns: entity.columns
          });
        })
        .catch(error => {
          console.log('' + error);
        });
    } else {
      console.log('Not logged in or token is expired');
    }
  };

  clearData = () => {
    this.setState({ data: [] });
  };

  render () {
    const options = {
      movableRows: true,
      layout: 'fitColumns'
    };
    
    return (
      <ReactTabulator
        ref={ref => (this.ref = ref)}
        columns={this.state.columns}
        data={this.state.data}
        rowClick={this.rowClick}
        options={options}
        data-custom-attr="test-custom-attribute"
        className="custom-css-class"
      />
    );
  }

  componentDidMount () {
    this.getData();
  }

  componentDidUpdate () {
    let { entityType } = this.props.match.params;
    
    if (entityType !== this.state.id) {
      this.getData();
    }
  }
}

export default Grid;