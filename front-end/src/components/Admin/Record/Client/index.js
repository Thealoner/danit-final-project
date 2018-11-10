import React, { Component, Fragment } from 'react';
import './index.scss';
import { ReactTabulator } from 'react-tabulator';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../GridEntities';
import AuthService from '../../../Login/AuthService';
import Settings from '../../../Settings';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';

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
        let complexFields = [];
  
        keys.forEach((key) => {
          if (Array.isArray(data[key]) || (data[key] && typeof data[key] === 'object' && data[key].constructor === Object)) {
            complexFields.push = {
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
          simpleFields,
          complexFields
          // ,columns: entity.columns
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
      <div className="client">
        <Formik
          initialValues={{ email: '' }}
          validationSchema={Yup.object().shape({
            email: Yup.string()
              .email()
              .required('Required'),
          })}
        >
          {props => {
            const {
              values,
              touched,
              errors,
              dirty,
              isSubmitting,
              handleChange,
              handleBlur,
              handleSubmit,
              handleReset,
            } = props;
            return (
              <Fragment>
                <form onSubmit={handleSubmit} style={{ float: 'left' }}>
                  <label htmlFor="email" style={{ display: 'block' }}>
                    Email
                  </label>
                  <input
                    id="email"
                    placeholder="Enter your email"
                    type="text"
                    value={values.email}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={
                      errors.email && touched.email ? 'text-input error' : 'text-input'
                    }
                  />
                  {errors.email &&
                    touched.email && <div className="input-feedback">{errors.email}</div>}

                  <button
                    type="button"
                    className="outline"
                    onClick={handleReset}
                    disabled={!dirty || isSubmitting}
                  >
                    Reset
                  </button>
                  <button type="submit" disabled={isSubmitting}>
                    Submit
                  </button>
                </form>
                  
                <div style={{float: 'right', width: '200px'}}>
                  <DisplayFormikState {...props} />
                </div>
              </Fragment>
            );
          }}
        </Formik>
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
      </div>
    );
  }

  componentDidMount () {
    this.getData();
  }
}

export const DisplayFormikState = props =>
  <div style={{ margin: '1rem 0' }}>
    <h3 style={{ fontFamily: 'monospace' }} />
    <pre
      style={{
        background: '#f6f8fa',
        fontSize: '.65rem',
        padding: '.5rem',
      }}
    >
      <strong>props</strong> ={' '}
      {JSON.stringify(props, null, 2)}
    </pre>
  </div>;

export default SimpleRecord;