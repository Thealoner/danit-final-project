import React, { Component, Fragment } from 'react';
import './index.scss';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../GridEntities';
import AuthService from '../../../Login/AuthService';
import Form from 'react-jsonschema-form';
import ajaxRequest, {resizeInput} from '../../../Helpers';

class RecordEditor extends Component {
  constructor (props) {
    super(props);
    this.state = {
      authService: new AuthService(),
      loading: false,
      messageType: '',
      messageText: ''
    };
  }

  getData = () => {
    let { rowId } = this.props.match.params;
    let { entityType } = this.props;
    let entity = getEntityByType(entityType);
    let formInputs = document.getElementsByClassName('form-control');

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

        for (let i = 0; i < formInputs.length; i++) {
          resizeInput(formInputs[i]);
        }
      });
  };

  putData = (form) => {
    let entity = getEntityByType(this.props.entityType);

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
        this.showMessage('success', 'Данные успешно сохранены');
        this.hideMessageAfterTimeout();
      })
      .catch(error => {
        console.log(error);
        this.showMessage('error', 'Ошибка при сохранении');
        this.hideMessageAfterTimeout();
      });
  };

  postData = (form) => {
    let { tabKey } = this.props.match.params;
    let { entityType, setTabContentUrl } = this.props;
    let entity = getEntityByType(entityType);

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
        this.showMessage('success', 'Данные успешно сохранены');
        this.hideMessageAfterTimeout();
        
        let editorUrl = entityType + '/edit/' + json[0].id;
        setTabContentUrl(editorUrl);
        this.props.history.push({
          pathname: '/admin/' + tabKey + '/' + editorUrl
        });
      })
      .catch(error => {
        console.log(error);
        this.showMessage('error', 'Ошибка при сохранении');
        this.hideMessageAfterTimeout();
      });
  };

  changeData = (form) => {
    this.props.setRecordData(form.formData, true);
  };

  log = (form) => console.log.bind(console, form);

  showMessage = (type, text) => {
    this.setState({
      loading: false,
      messageText: text,
      messageType: type
    });
  };

  renderMessage = () => this.state.messageType !== ''
    ? <span className={'record__save-message record__save-message--' + this.state.messageType}>{this.state.messageText}</span>
    : '';

  hideMessageAfterTimeout = (timeout = 1000) => {
    setTimeout(() => this.setState({
      messageText: '',
      messageType: ''
    }), timeout);
  };

  render () {
    let { mode, rowId } = this.props.match.params;
    let { entityType, setTabContentUrl, getRecordData } = this.props;

    if (mode === 'edit') {
      setTabContentUrl(entityType + '/' + mode + '/' + rowId);
    } else if (mode === 'add') {
      setTabContentUrl(entityType + '/' + mode);
    }

    let entity = getEntityByType(entityType);

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
        {this.renderMessage()}
      </Fragment>
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