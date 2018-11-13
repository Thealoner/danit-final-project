import React, { Component } from 'react';
import './index.scss';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../GridEntities';
import AuthService from '../../../Login/AuthService';
import Settings from '../../../Settings';
import Form from 'react-jsonschema-form';

class SimpleRecord extends Component {
  constructor (props) {
    super(props);
    this.state = {
      entityType: '',
      data: {},
      authService: new AuthService(),
      isLoading: true
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
        this.setState({
          entityType: entity.id,
          data: data,
          isLoading: false
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
      data: {
        ...prevState.data,
        [name]: value
      }
    }));
  }

  log = (type) => console.log.bind(console, type);

  render () {
    let { rowId } = this.props.match.params;
    let { entityType, setTabContentUrl } = this.props;
    setTabContentUrl(entityType + '/' + rowId);
    let entity = getEntityByType(entityType);
    
    return (
      <div className="client">
        {this.state.isLoading ? (
          <p>Loading...</p>
        ) : (
          <Form
            schema={entity.schema}
            uiSchema={entity.uiSchema}
            formData={this.state.data}
            onChange={this.log('changed')}
            onSubmit={this.log('submitted')}
            onError={this.log('errors')}
          />
        )}
      </div>
    );
  }

  componentDidMount () {
    this.getData();
  }
}

export default SimpleRecord;