import React, { Component } from 'react';
import { Field, reduxForm } from 'redux-form';
import { connect } from 'react-redux';
import {
  cancelEditFormData,
  saveFormData,
  deleteCurrentEntityItem,
  storeTabTmpFormData
} from '../../../../actions/tabActions';

class Service extends Component {
  handlesubmit = event => {
    event.preventDefault();
    console.log(event.target);
  }
  
  render () {
    let { currentTab } = this.props;
    let { data } = currentTab.form;

    return (
      <>
        <form onSubmit={this.handlesubmit} className="record">
          <div className="form-group field field-object">
            <p>ID:</p>
            <div className="form-group field field-string">
              <label className="control-label" htmlFor="title">Название</label>
              <Field name="title" component="input" type="text" />
            </div>
            <div className="form-group field field-string">
              <label className="control-label" htmlFor="price">Цена</label>
              <Field name="price" component="input" type="text" />
            </div>
            <div className="form-group field field-string">
              <label className="control-label" htmlFor="cost">Себестоимость</label>
              <Field name="cost" component="input" type="text" />
            </div>
            <div className="form-group field field-string">
              <label className="control-label" htmlFor="unit">Единица измерения</label>
              <Field name="unit" component="input" type="text" />
            </div>
            <div className="form-group field field-string">
              <label className="control-label" htmlFor="unitsNumber">Кол-во единиц</label>
              <Field name="unitsNumber" component="input" type="text" />
            </div>
            <div className="form-group field field-string">
              <label className="control-label" htmlFor="active">Активен</label>
              <Field name="active" component="input" type="text" />
            </div>

            <button type="submit" class="record__button">Сохранить</button>
            <button type="button" class="record__button">Удалить</button>
            <button type="button" class="record__button">Отмена</button>
          </div>
        </form>
      </>
    );
  }
};

Service = reduxForm({ form: 'service' })(Service)

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

export default connect(null, mapDispatchToProps)(Service);