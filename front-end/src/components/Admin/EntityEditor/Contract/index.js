import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import RenderField from '../Fields/RenderField';
import RenderSearchField from '../Fields/RenderSearchField';
import RenderCheckbox from '../Fields/RenderCheckbox';
import validate from '../../../../helpers/validate';

const dateFields = [
  'startDate',
  'endDate'
];
const requiredFields = [
  'startDate',
  'endDate',
  'credit'
];
class Contract extends Component {
  changeField = (fieldName, value) => {
    this.props.change(fieldName, value);
  };

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
          <Field name="clientId" component={RenderSearchField} type="text" label="Клиент" changeField={this.changeField}
            entity="clients" entityId={data.clientId} className='search' isRequired={this.isRequired} />
          <Field name="startDate" component={RenderField} type="date" label="Дата начала" isRequired={this.isRequired} />
          <Field name="endDate" component={RenderField} type="date" label="Дата окончания" isRequired={this.isRequired} />
          <Field name="credit" component={RenderField} type="text" label="Кредит" isRequired={this.isRequired} />
          <Field name="packageId" component={RenderSearchField} type="text" label="Пакет" changeField={this.changeField}
            entity="pakets" entityId={data.packageId} className='search' isRequired={this.isRequired} />
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
}

let reduxFormContract = reduxForm({
  form: 'contract',
  validate: (fields) => validate(fields, {
    requiredFields,
    dateFields: dateFields
  })
})(Contract);

const mapStateToProps = state => ({
  formValues: getFormValues('contract')(state)
});

export default connect(mapStateToProps, null)(reduxFormContract);