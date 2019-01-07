import React, { Component, Fragment } from 'react';
import 'react-tabulator/lib/styles.css';
import 'tabulator-tables/dist/css/tabulator.min.css';
import Form from 'react-jsonschema-form';
import {toastr} from 'react-redux-toastr';
import {connect} from 'react-redux';
import './index.scss';
import {
  cancelEditFormData,
  saveFormData,
  deleteCurrentEntityItem,
  storeTabTmpFormData
} from '../../../actions/tabActions';
import { getEntityByType } from '../gridEntities';

class RecordEditor extends Component {
  changeData = form => {
    const { currentTab, storeTmpFormData } = this.props;

    storeTmpFormData({ ...currentTab.form, data: form.formData });
  };

  render () {
    const { currentTab, saveData, deleteData, cancelData } = this.props;
    const {currentPage} = currentTab.grid.meta;
    const entity = getEntityByType(currentTab.tabKey);
    const mode = currentTab.form.mode;

    return (
      <Fragment>
        <Form className='record'
          schema={entity.schema}
          uiSchema={entity.uiSchema}
          formData={currentTab.form.data}
          autocomplete='off'
          onChange={this.changeData}
          onSubmit={(form) => saveData(currentTab.tabKey, form.formData, currentTab.grid.columns, mode, currentPage)}
          onError={() => toastr.error('Пожалуйста, проверьте введеные данные')}>
          <button type='submit' className='record__button'>Сохранить</button>
          <button type='button' className='record__button' onClick={
            () => deleteData(currentTab.tabKey, currentTab.form.data, currentTab.grid.columns, currentPage)
          }>Удалить</button>
          <button type='button' className='record__button' onClick={() => cancelData()}>Отмена</button>
        </Form>
      </Fragment>
    );
  }
}

const mapDispatchToProps = dispatch => {
  return {
    storeTmpFormData: (payload) => {
      dispatch(storeTabTmpFormData(payload));
    },
    saveData: (tabKey, formData, columns, mode, page) => {
      dispatch(saveFormData(tabKey, formData, columns, mode, page));
    },
    deleteData: (tabKey, formData, columns, page) => {
      dispatch(deleteCurrentEntityItem(tabKey, formData, columns, page));
    },
    cancelData: () => {
      dispatch(cancelEditFormData());
    }
  };
};

export default connect(null, mapDispatchToProps)(RecordEditor);