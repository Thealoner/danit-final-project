import React, { Component, Fragment } from 'react';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import Form from 'react-jsonschema-form';
import ajaxRequest from '../../../../helpers/ajaxRequest';
import {toastr} from 'react-redux-toastr';
import {connect} from 'react-redux';
import { Loader } from 'semantic-ui-react';
import {
  persistTabFormData,
  storeTabTmpFormData
} from '../../../../actions/tabActions';
import { getEntityByType } from '../../gridEntities';

class RecordEditor extends Component {
  putData = form => {
    const { currentTab, persistFormData } = this.props;

    ajaxRequest.put(
      '/' + currentTab.tabKey,
      JSON.stringify([form.formData])
    )
      .then(json => {
        persistFormData(currentTab.tabKey, json[0]);
        toastr.success('Данные успешно сохранены');
      })
      .catch(error => {
        toastr.error('Ошибка при сохранении', error);
      });
  };

  postData = form => {
    const { currentTab, persistFormData } = this.props;

    ajaxRequest.post(
      '/' + currentTab.tabKey,
      JSON.stringify([form.formData])
    )
      .then(json => {
        persistFormData(currentTab.tabKey, json[0]);
        toastr.success('Данные успешно сохранены');
      })
      .catch(error => {
        toastr.error('Ошибка при сохранении', error);
      });
  };

  changeData = form => {
    const { currentTab, storeTmpFormData } = this.props;
    storeTmpFormData(currentTab.tabKey, { data: form.formData });
  };

  render () {
    const { currentTab } = this.props;
    const entity = getEntityByType(currentTab.tabKey);
    const mode = currentTab.form.id ? 'edit' : 'add';

    if (!currentTab.form || !currentTab.form.data) {
      return (<div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>);
    }

    return (
      <Fragment>
        <Form
          schema={entity.schema}
          uiSchema={entity.uiSchema}
          formData={currentTab.form.data}
          autocomplete='off'
          onChange={this.changeData}
          onSubmit={mode === 'edit' ? this.putData : this.postData}
          onError={error => toastr.error('Пожалуйста, проверьте введеные данные', error)}>
          <button type='submit' className='record__button'>Сохранить</button>
          <button type='button' className='record__button'>Отмена</button>
        </Form>
      </Fragment>
    );
  }
}

const mapDispatchToProps = dispatch => {
  return {
    persistFormData: (tabKey, payload) => {
      dispatch(persistTabFormData(tabKey, payload));
    },
    storeTmpFormData: (tabKey, payload) => {
      dispatch(storeTabTmpFormData(tabKey, payload));
    }
  };
};

export default connect(null, mapDispatchToProps)(RecordEditor);