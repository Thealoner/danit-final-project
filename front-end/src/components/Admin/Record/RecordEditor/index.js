import React, { Component } from 'react';
import './index.scss';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../GridEntities';
import AuthService from '../../../Login/AuthService';
import Settings from '../../../Settings';
import Form from 'react-jsonschema-form';

class RecordEditor extends Component {
  constructor (props) {
    super(props);
    this.state = {
      entityType: '',
      data: {},
      authService: new AuthService(),
      isLoading: false
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

    this.setState({
      isLoading: true
    });

    fetch(
      Settings.apiServerUrl + entity.apiUrl + '/' + rowId,
      { headers }
    )
      .then(this.state.authService._checkStatus)
      .then(response => response.json())
      .then(data => {
        setTimeout(() =>
          this.setState({
            entityType: entity.id,
            data: data,
            isLoading: false
          })
        , 1000);
      });
  };

  saveData = (form) => {
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
      this.setState({
        isLoading: true
      });

      fetch(
        Settings.apiServerUrl + entity.apiUrl,
        {
          method: 'PUT',
          body: JSON.stringify([form.formData]),
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
          // TODO: Set state.data to PUT response
          
          console.log('form.formData', form.formData);
          console.log('this.state.data', this.state.data);
          let mergedData = {
            ...this.state.data,
            ...form.formdata
          };
          console.log('mergedData', mergedData);
          this.setState({
            data: mergedData,
            isLoading: false
          });
        });
    } else {
      console.log('Not logged in or token is expired');
    }
  };

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
            onSubmit={this.saveData}
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

export default RecordEditor;