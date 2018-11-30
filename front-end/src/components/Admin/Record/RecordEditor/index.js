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
      loading: false
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

  saveData = (form) => {
    let { tabKey, mode } = this.props.match.params;
    let { entityType, setTabContentUrl } = this.props;
    let entity = getEntityByType(entityType);

    this.setState({
      loading: true
    });

    ajaxRequest(
      entity.apiUrl,
      mode === 'edit' ? 'PUT' : 'POST',
      JSON.stringify([form.formData])
    )
      .then(json => {
        this.props.setRecordData(json[0], false);
        this.successMessage.classList.add('visible');
        setTimeout(() => this.successMessage.classList.remove('visible'), 1000);

        this.setState({
          loading: false
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
        this.errorMessage.classList.add('visible');
        setTimeout(() => this.errorMessage.classList.remove('visible'), 1000);

        this.setState({
          loading: false
        });
      });
  };

  changeData = (type) => {
    console.log('change data');
    this.props.setRecordData(type.formData, true);
  };

  log = (type) => console.log.bind(console, type);

  successMessage = React.createRef();
  errorMessage = React.createRef();

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
          onSubmit={this.saveData}
          onError={this.log('errors')}>
          <button disabled={this.state.loading} type='submit' className='record__button'>Сохранить</button>
        </Form>
        <span ref={success => (this.successMessage = success)} className="record__save-message record__save-message--success">Данные успешно сохранены</span>
        <span ref={error => (this.errorMessage = error)} className="record__save-message record__save-message--error">Ошибка при сохранении</span>
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