import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import { Loader } from 'semantic-ui-react';

class Service extends Component {
  render () {
    let { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    let { data } = currentTab.form;
    let editingMode;
    if (currentTab.form.data.id) {
      editingMode = true;
    }

    if (!data) {
      return <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>;
    }

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{editingMode ? 'ID: ' + currentTab.form.data.id : ''}</p>
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
            <Field name="active" component="input" type="checkbox" />
          </div>

          <button type="submit" className="record__button" disabled={!currentTab.form.edited || submitting}>Сохранить</button>
          <button type="button" className="record__button" onClick={handleDelete} disabled={!editingMode}>Удалить</button>
          <button type="button" className="record__button" onClick={handleCancel}>Отмена</button>
        </div>
      </form>
    );
  }

  componentDidMount () {
    let { currentTab, initialize } = this.props;
    let { data } = currentTab.form;
    initialize(data);
  }

  componentDidUpdate () {
    let { dirty, handleChange, formValues } = this.props;
    
    if (dirty) {
      handleChange({
        data: {
          ...formValues
        }
      });
    }
  }
};

let reduxFormService = reduxForm({ form: 'service' })(Service);

const mapStateToProps = state => ({
  formValues: getFormValues('service')(state)
});

export default connect(mapStateToProps, null)(reduxFormService);