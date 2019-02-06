import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import RenderField from '../Fields/RenderField';
import RenderCheckbox from '../Fields/RenderCheckbox';
import validate from '../../../../helpers/validate';

const requiredFields = [
  'title'
];
class ServiceCategory extends Component {
  isRequired = fieldName => {
    return requiredFields.includes(fieldName);
  };

  render () {
    const { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    const { data } = currentTab.form;
    const editMode = !!data.id;

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{editMode ? 'ID: ' + data.id : ''}</p>
          <Field name="title" component={RenderField} type="text" label="Название" isRequired={this.isRequired} />
          <Field name="active" component={RenderCheckbox} type="checkbox" label="Активен" isRequired={this.isRequired} />

          <button type="submit" className="record__button" disabled={!currentTab.form.edited || submitting}>Сохранить</button>
          <button type="button" className="record__button" onClick={handleDelete} disabled={!editMode}>Удалить</button>
          <button type="button" className="record__button" onClick={handleCancel}>Отмена</button>
        </div>
      </form>
    );
  }

  componentDidMount () {
    const { currentTab, initialize } = this.props;
    const { data } = currentTab.form;
    initialize(data);
  }

  componentDidUpdate () {
    const { dirty, handleChange, formValues } = this.props;
    
    if (dirty) {
      handleChange({
        data: {
          ...formValues
        }
      });
    }
  }
};

const reduxFormServiceCategory = reduxForm({
  form: 'service_category',
  validate: (fields) => validate(fields, {
    requiredFields
  })
})(ServiceCategory);

const mapStateToProps = state => ({
  formValues: getFormValues('service_category')(state)
});

export default connect(mapStateToProps, null)(reduxFormServiceCategory);