import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import RenderField from '../Fields/RenderField';
import RenderSelect from '../Fields/RenderSelect';
import RenderCheckbox from '../Fields/RenderCheckbox';
import validate from '../../../../helpers/validate';

const genderOptions = [
  {
    text: 'Мужской',
    value: 'M'
  },
  {
    text: 'Женский',
    value: 'F'
  }
];

const dateValues = [
  'birthDate'
];

const requiredFields = [
  'firstName',
  'lastName'
];


class Client extends Component {
  changeField = (e, data, fieldName) => {
    this.props.change(fieldName, data.value);
  };

  render () {
    const { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    const { data } = currentTab.form;
    const editMode = !!data.id;

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{editMode ? 'ID: ' + data.id : ''}</p>
          <Field name="firstName" component={RenderField} type="text" label="Имя" isRequired={this.isRequired} />
          <Field name="lastName" component={RenderField} type="text" label="Фамилия" isRequired={this.isRequired} />
          <Field name="gender" component={RenderSelect} type="text" label="Пол" options={genderOptions} changeField={(e, data) => this.changeField(e, data, 'gender')} isRequired={this.isRequired} />
          <Field name="birthDate" component={RenderField} type="date" label="Дата рождения" isRequired={this.isRequired} />
          <Field name="phoneNumber" component={RenderField} type="text" label="Телефон" isRequired={this.isRequired} />
          <Field name="email" component={RenderField} type="text" label="Email" isRequired={this.isRequired} />
          <Field name="active" component={RenderCheckbox} type="checkbox" label="Активен" isRequired={this.isRequired} />
          {/* TODO: Show list of {contracts}, allow assign and unassign */}

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
}

const reduxFormClient = reduxForm({
  form: 'client',
  validate: (fields) => validate(fields, {
    requiredFields,
    dateValues
  })
})(Client);

const mapStateToProps = state => ({
  formValues: getFormValues('client')(state)
});

export default connect(mapStateToProps, null)(reduxFormClient);