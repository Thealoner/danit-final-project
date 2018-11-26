import React, { Component, Fragment } from 'react';
import './index.scss';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import { getEntityByType } from '../../GridEntities';
import AuthService from '../../../Login/AuthService';
import Form from 'react-jsonschema-form';
import { FadeLoader } from 'react-spinners';
import ajaxRequest from '../../../Helpers';
import { FadeLoader } from 'react-spinners';

class RecordEditor extends Component {
  constructor (props) {
    super(props);
    this.state = {
      data: {},
      authService: new AuthService(),
      loading: false
    };
  }

  getData = () => {
    let { rowId } = this.props.match.params;
    let { entityType } = this.props;
    let entity = getEntityByType(entityType);

    this.setState({
      loading: true
    });

    ajaxRequest(entity.apiUrl + '/' + rowId)
      .then(data => {
        setTimeout(() =>
          this.setState({
            entityType: entity.id,
            data: data,
            loading: false
          })
        , 1000);
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
        // display green 'Данные сохранены' message
        // enable 'Save' button
        // hide loader

        this.setState({
          data: json[0],
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
  };

  changeDataInState = (type) => {
    this.setState({
      data: type.formData
    });
  };

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
      <Fragment>
        {this.state.loading ? <div className="record__loader-wrapper">
          <FadeLoader
            sizeUnit={'px'}
            size={50}
            color={'#999'}
            loading={this.state.loading}
          />
        </div> : <Form
          schema={entity.schema}
          uiSchema={entity.uiSchema}
          formData={this.state.data}
          autocomplete='off'
          onChange={this.log('changed')}
          onSubmit={this.saveData}
          onError={this.log('errors')}
        >
          <button className='record__button'>Сохранить</button>
        </Form>}
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