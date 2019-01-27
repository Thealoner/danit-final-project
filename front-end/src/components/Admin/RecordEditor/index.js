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

  cancelDataEditing = () => {
    const { currentTab, cancelEditFormData } = this.props;

    const toastrConfirmOptions = {
      onOk: () => cancelEditFormData(),
      okText: 'Да',
      cancelText: 'Нет'
    };

    if (currentTab.form.edited) {
      toastr.confirm('Изменения не сохранены, продолжить?', toastrConfirmOptions);
    } else {
      cancelEditFormData();
    }
  }

  render () {
    const { currentTab, saveData, deleteData } = this.props;
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
          onSubmit={(form) => saveData(currentTab.tabKey, form.formData, currentTab.grid.columns, mode, currentPage, currentTab.filter)}
          onError={() => toastr.error('Пожалуйста, проверьте введеные данные')}>
          <button type='submit' className='record__button'>Сохранить</button>
          <button type='button' className='record__button' onClick={
            () => deleteData(currentTab.tabKey, currentTab.form.data, currentTab.grid.columns, currentPage)
          }>Удалить</button>
          <button type='button' className='record__button' onClick={() => this.cancelDataEditing()}>Отмена</button>
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
    saveData: (tabKey, formData, columns, mode, page, filter) => {
      dispatch(saveFormData(tabKey, formData, columns, mode, page, filter));
    },
    deleteData: (tabKey, formData, columns, page) => {
      dispatch(deleteCurrentEntityItem(tabKey, formData, columns, page));
    },
    cancelEditFormData: () => {
      dispatch(cancelEditFormData());
    }
  };
};

export default connect(null, mapDispatchToProps)(RecordEditor);