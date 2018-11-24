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
    let { tabKey, mode } = this.props.match.params;
    let { entityType, setTabContentUrl } = this.props;
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
          method: mode === 'edit' ? 'PUT' : 'POST',
          body: JSON.stringify([form.formData]),
          headers
        }
      )
        .then(this.state.authService._checkStatus)
        .then(response => response.json())
        .then(json => {
          console.log(json);

          // display green 'Данные сохранены' message
          // enable 'Save' button
          // hide loader
          let stateData = this.state.data;
          let formData = form.formData;
          
          this.setState({
            data: {
              ...stateData,
              ...formData,
              id: json[0].id
            },
            isLoading: false
          });
          
          if (mode === 'add') {
            let editorUrl = entityType + '/edit/' + json[0].id;
            setTabContentUrl(editorUrl);
            this.props.history.push({
              pathname: '/admin/' + tabKey + '/' + editorUrl
            });
          }
        })
        .catch(error => {
          console.log(error);
          // display red 'Ошибка при сохранении' message
          // enable 'Save' button
          // hide loader
          this.setState({
            isLoading: false
          });
        });
    } else {
      console.log('Not logged in or token is expired');
    }
  };

  changeDataInState = (type) => {
    this.setState({
      data: type.formData
    });
  }

  log = (type) => console.log.bind(console, type);

  render () {
    let { mode, rowId } = this.props.match.params;
    let { entityType, setTabContentUrl } = this.props;
    
    if (mode === 'edit') {
      setTabContentUrl(entityType + '/' + mode + '/' + rowId);
    } else if (mode === 'add') {
      setTabContentUrl(entityType + '/' + mode);
    }
    
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
            onChange={this.changeDataInState}
            onSubmit={this.saveData}
            onError={this.log('errors')}
          />
        )}
      </div>
    );
  }

  componentDidMount () {
    let { mode } = this.props.match.params;
    if (mode === 'edit') {
      this.getData();
    }
  }
}

export default RecordEditor;