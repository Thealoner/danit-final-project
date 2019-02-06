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
  'title',
  'price',
  'cost',
  'unit',
  'unitsNumber'
];

const numericFields = [
  'price',
  'cost',
  'unitsNumber'
];
class Service extends Component {
  isRequired = fieldName => {
    return requiredFields.includes(fieldName);
  };

  render () {
    let { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    let { data } = currentTab.form;
    const editMode = !!data.id;

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{editMode ? 'ID: ' + data.id : ''}</p>
          <Field name="title" component={RenderField} type="text" label="Название" isRequired={this.isRequired} />
          <Field name="price" component={RenderField} type="text" label="Цена" isRequired={this.isRequired} />
          <Field name="cost" component={RenderField} type="text" label="Себестоимость" isRequired={this.isRequired} />
          <Field name="unit" component={RenderField} type="text" label="Единица измерения" isRequired={this.isRequired} />
          <Field name="unitsNumber" component={RenderField} type="text" label="Кол-во единиц" isRequired={this.isRequired} />
          <Field name="active" component={RenderCheckbox} type="checkbox" label="Активен" isRequired={this.isRequired} />

          <button type="submit" className="record__button" disabled={!currentTab.form.edited || submitting}>Сохранить</button>
          <button type="button" className="record__button" onClick={handleDelete} disabled={!editMode}>Удалить</button>
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

let reduxFormService = reduxForm({
  form: 'service',
  validate: (fields) => validate(fields, {
    requiredFields,
    numericFields
  })
})(Service);

const mapStateToProps = state => ({
  formValues: getFormValues('service')(state)
});

export default connect(mapStateToProps, null)(reduxFormService);