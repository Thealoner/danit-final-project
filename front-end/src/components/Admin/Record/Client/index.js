import React, { Component } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../GridEntities';
import AuthService from '../../../Login/AuthService';
import Settings from '../../../Settings';
import ClientEditor from './ClientEditor';

class SimpleRecord extends Component {
  constructor (props) {
    super(props);
    this.state = {
      entityType: '',
      name: '',
      editableFields: {
        id: '',
        firstName: '',
        lastName: '',
        gender: '',
        birthDate: '',
        phoneNumber: '',
        email: '',
        active: false
      },
      readonlyFields: {
        contracts: []
      },
      authService: new AuthService()
    };
  }

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

  fetchEntity = (entity, rowId) => {
    const headers = {
      'Content-Type': 'application/json'
    };

    let token = this.state.authService.getToken();
    headers['Authorization'] = token;

    fetch(
      Settings.apiServerUrl + entity.apiUrl + '/' + rowId,
      { headers }
    )
      .then(this.state.authService._checkStatus)
      .then(response => response.json())
      .then(data => {
        let editableDataKeys = Object.keys(this.state.editableFields);
        let editableData = {};

        editableDataKeys.forEach(key => {
          editableData[key] = data[key];
        });

        let readonlyDataKeys = Object.keys(this.state.readonlyFields);
        let readonlyData = {};
        
        readonlyDataKeys.forEach(key => {
          readonlyData[key] = data[key];
        });

        this.setState({
          entityType: entity.id,
          editableFields: editableData,
          readonlyFields: readonlyData
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

      // TODO:
      // disable 'Save' button
      // show loader

      fetch(
        Settings.apiServerUrl + entity.apiUrl,
        {
          method: 'PUT',
          body: JSON.stringify([this.state.editableFields]),
          headers
        }
      )
        .then(this.state.authService._checkStatus)
        .then(response => {
          console.log(response.status);
          // display green 'Данные сохранены' message
        })
        .catch(error => {
          console.log(error);
          // display red 'Ошибка при сохранении' message
        })
        .finally(() => {
          console.log('Finally');
          // TODO:
          // enable 'Save' button
          // hide loader
        });
    } else {
      console.log('Not logged in or token is expired');
    }
  };

  handleInputChange = (event) => {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState(prevState => ({
      editableFields: {
        ...prevState.editableFields,
        [name]: value
      }
    }));
  }

  render () {
    let { rowId } = this.props.match.params;
    let { entityType, setTabContentUrl } = this.props;
    setTabContentUrl(entityType + '/' + rowId);

    const options = {
      movableRows: true
    };

    let columns = [
      { title: 'ID', field: 'id' },
      { title: 'Пакет', field: 'packageId' },
      { title: 'startDate', field: 'startDate' },
      { title: 'endDate', field: 'endDate' },
      { title: 'Активен', field: 'active' }
    ];

    return (
      <div className="client">
        <p>Client ID: {this.state.editableFields.id}</p>
        <ClientEditor data={this.state.editableFields} handleInputChange={this.handleInputChange} />
        <h1>Контракты</h1>
        <ReactTabulator
          ref={ref => (this.ref = ref)}
          columns={columns}
          data={this.state.readonlyFields.contracts}
          rowClick={this.rowClick}
          options={options}
          data-custom-attr="test-custom-attribute"
          className="custom-css-class"
        />
        <button onClick={this.saveData}>Save</button>
      </div>
    );
  }

  componentDidMount () {
    this.getData();
  }
}

export default SimpleRecord;