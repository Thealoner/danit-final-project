import React, { Component, Fragment } from 'react';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../gridEntities';
import AuthService from '../../../../helpers/authService';
import Form from 'react-jsonschema-form';
import ajaxRequest from '../../../../helpers/ajaxRequest';
import resizeInputs from '../../../../helpers/resizeInputs';
import {toastr} from 'react-redux-toastr';

const formInputs = document.getElementsByClassName('form-control');

class RecordEditor extends Component {
  constructor (props) {
    console.log('constructor');
    super(props);
    this.state = {
      authService: new AuthService(),
      loading: false,
      messageType: '',
      messageText: ''
    };
  }

  getData = () => {
    console.log('getData');
    const { rowId } = this.props.match.params;
    const { entityType } = this.props;
    const entity = getEntityByType(entityType);

    this.setState({
      loading: true
    });

    ajaxRequest(entity.apiUrl + '/' + rowId)
      .then(data => {
        this.props.setRecordData(data, false);

        this.setState({
          entityType: entity.id,
          loading: false
        });

        resizeInputs(formInputs);
      });
  };

  putData = (form) => {
    const entity = getEntityByType(this.props.entityType);

    this.setState({
      loading: true,
      messageType: ''
    });

    ajaxRequest(
      entity.apiUrl,
      'PUT',
      JSON.stringify([form.formData])
    )
      .then(json => {
        this.props.setRecordData(json[0], false);
        this.setState({
          loading: false
        });
        toastr.success('Данные успешно сохранены');

        resizeInputs(formInputs);
      })
      .catch(error => {
        this.setState({
          loading: false
        });
        toastr.error('Ошибка при сохранении', error);
      });
  };

  postData = (form) => {
    const { tabKey } = this.props.match.params;
    const { entityType, setTabContentUrl } = this.props;
    const entity = getEntityByType(entityType);

    this.setState({
      loading: true,
      messageType: ''
    });

    ajaxRequest(
      entity.apiUrl,
      'POST',
      JSON.stringify([form.formData])
    )
      .then(json => {
        this.props.setRecordData(json[0], false);
        this.setState({
          loading: false
        });
        toastr.success('Данные успешно сохранены');
        resizeInputs(formInputs);

        const editorUrl = entityType + '/edit/' + json[0].id;
        setTabContentUrl(editorUrl);
        this.props.history.push({
          pathname: '/admin/' + tabKey + '/' + editorUrl
        });
      })
      .catch(error => {
        this.setState({
          loading: false
        });
        toastr.error('Oшибка при сохранении', error);
      });
  };

  changeData = (form) => {
    this.props.setRecordData(form.formData, true);
  };

  log = (form) => console.log.bind(console, form);

  render () {
    console.log('render');
    const { mode, rowId } = this.props.match.params;
    const { entityType, setTabContentUrl, getRecordData } = this.props;

    if (mode === 'edit') {
      setTabContentUrl(entityType + '/' + mode + '/' + rowId);
    } else if (mode === 'add') {
      setTabContentUrl(entityType + '/' + mode);
    }

    const entity = getEntityByType(entityType);

    return (
      <Fragment>
        <Form
          schema={entity.schema}
          uiSchema={entity.uiSchema}
          formData={getRecordData()}
          autocomplete='off'
          onChange={this.changeData}
          onSubmit={mode === 'edit' ? this.putData : this.postData}
          onError={this.log('errors')}>
          <button disabled={this.state.loading} type='submit' className='record__button'>Сохранить</button>
        </Form>
      </Fragment>
    );
  }

  componentDidMount () {
    console.log('componentDidMount');
    const { mode } = this.props.match.params;

    if (mode === 'edit') {
      this.getData();
    }

    resizeInputs(formInputs);
  }

  componentDidUpdate () {
    console.log('componentDidUpdate');
  }
}

export default RecordEditor;