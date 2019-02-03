import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import RenderField from '../Fields/RenderField';
import RenderMultiSelect from '../Fields/RenderMultiSelect';
import validateAllRequired from '../../../../helpers/validateAllRequired';
import warningTest from '../../../../helpers/warningTest';

const rolesOptions = [
  {
    key: 'ADMIN',
    text: 'ADMIN',
    value: '1'
  },
  {
    key: 'USER',
    text: 'USER',
    value: '2'
  }
];

class User extends Component {
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
          <Field name="username" component={RenderField} type="text" label="Логин" />
          <Field name="roles" component={RenderMultiSelect} type="text" label="Роль" options={rolesOptions} changeField={(e, data) => this.changeField(e, data, 'roles')} />
          <Field name="email" component={RenderField} type="text" label="Email" />

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

const reduxFormUser = reduxForm({
  form: 'user',
  validate: validateAllRequired,
  warn: warningTest
})(User);

const mapStateToProps = state => ({
  formValues: getFormValues('user')(state)
});

export default connect(mapStateToProps, null)(reduxFormUser);