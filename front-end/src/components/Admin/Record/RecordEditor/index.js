import React, { Component, Fragment } from 'react';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import Form from 'react-jsonschema-form';
import {toastr} from 'react-redux-toastr';
import {connect} from 'react-redux';
import {
  cancelEditFormData,
  saveFormData,
  storeTabTmpFormData
} from '../../../../actions/tabActions';
import { getEntityByType } from '../../gridEntities';

class RecordEditor extends Component {
  changeData = form => {
    console.log(form.formData)
    const { currentTab, storeTmpFormData } = this.props;
    storeTmpFormData(currentTab.tabKey, { ...currentTab.form, data: form.formData });
  };

  render () {
    const { currentTab, saveData, cancelData } = this.props;
    const entity = getEntityByType(currentTab.tabKey);
    // const mode = currentTab.form.mode;
    console.log(currentTab.form)

    return (
      <Fragment>
        <Form
          schema={entity.schema}
          uiSchema={entity.uiSchema}
          formData={currentTab.form.data}
          autocomplete='off'
          onChange={this.changeData}
          onSubmit={(form) => saveData(currentTab.tabKey, form.formData, currentTab.grid.columns)}
          onError={() => toastr.error('Пожалуйста, проверьте введеные данные')}>
          <button type='submit' className='record__button'>Сохранить</button>
          <button type='button' className='record__button' onClick={() => cancelData()}>Отмена</button>
        </Form>
      </Fragment>
    );
  }
}

const mapDispatchToProps = dispatch => {
  return {
    storeTmpFormData: (tabKey, payload) => {
      dispatch(storeTabTmpFormData(tabKey, payload));
    },
    saveData: (tabKey, formData, columns) => {
      dispatch(saveFormData(tabKey, formData, columns));
    },
    cancelData: () => {
      dispatch(cancelEditFormData());
    }
  };
};

export default connect(null, mapDispatchToProps)(RecordEditor);