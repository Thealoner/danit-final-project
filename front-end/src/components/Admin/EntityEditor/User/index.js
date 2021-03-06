import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import RenderField from '../Fields/RenderField';
import RenderMultiSelect from '../Fields/RenderMultiSelect';
import validate from '../../../../helpers/validate';
import ajaxRequest from '../../../../helpers/ajaxRequest';
import { toastr } from 'react-redux-toastr';
import { Loader } from 'semantic-ui-react';

const requiredFields = [
  'username',
  'password',
  'email'
];

const passwordFields = [
  'passwordFields'
];

class User extends Component {
  state = {
    rolesOptions: [],
    rolesLoaded: false
  }
  
  changeField = (e, data, fieldName) => {
    this.props.change(fieldName, data.value);
  };

  isRequired = fieldName => {
    return requiredFields.includes(fieldName);
  };

  initRoles = () => {
    ajaxRequest.get('/roles')
      .then(response => {
        const rolesOptions = response.data.map(role => ({
          key: role.role,
          text: role.role,
          value: '' + role.id
        }));
        
        this.setState({
          rolesOptions: rolesOptions,
          rolesLoaded: true
        });
      },
      error => {
        toastr.error(error.message);
      });
  }

  render () {
    const { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    const { data } = currentTab.form;
    const editMode = !!data.id;

    if (!this.state.rolesLoaded) {
      return <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'isRequired={this.isRequired} /></div>;
    }

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{editMode ? 'ID: ' + data.id : ''}</p>
          <Field name="username" component={RenderField} type="text" label="Логин" isRequired={this.isRequired} />
          <Field name="password" component={RenderField} type="password" label="Пароль" isRequired={this.isRequired} />
          <Field name="roles" component={RenderMultiSelect} type="text" label="Роль" options={this.state.rolesOptions} changeField={(e, data) => this.changeField(e, data, 'roles')} isRequired={this.isRequired} />
          <Field name="email" component={RenderField} type="text" label="Email" isRequired={this.isRequired} />

          <button type="submit" className="record__button" disabled={!currentTab.form.edited || submitting}>Сохранить</button>
          <button type="button" className="record__button" onClick={handleDelete} disabled={!editMode}>Удалить</button>
          <button type="button" className="record__button" onClick={handleCancel}>Отмена</button>
        </div>
      </form>
    );
  }

  componentDidMount () {
    this.initRoles();
  }

  componentDidUpdate (prevProps, prevState) {
    const { currentTab, initialize, dirty, handleChange, formValues } = this.props;
    const { data } = currentTab.form;

    if (!prevState.rolesLoaded && this.state.rolesLoaded) {
      initialize(data);
    }
    
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
  validate: (fields) => validate(fields, {
    requiredFields,
    passwordFields
  })
})(User);

const mapStateToProps = state => ({
  formValues: getFormValues('user')(state)
});

export default connect(mapStateToProps, null)(reduxFormUser);