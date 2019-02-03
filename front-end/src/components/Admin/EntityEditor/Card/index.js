import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import RenderField from '../Fields/RenderField';
import RenderCheckbox from '../Fields/RenderCheckbox';
import validateAllRequired from '../../../../helpers/validateAllRequired';
import warningTest from '../../../../helpers/warningTest';

class Client extends Component {
  render () {
    const { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    const { data } = currentTab.form;
    const editMode = !!data.id;

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{editMode ? 'ID: ' + data.id : ''}</p>
          <Field name="code" component={RenderField} type="text" label="Код" />
          <Field name="active" component={RenderCheckbox} type="checkbox" label="Активна" />
          { /* TODO: show {contractId} this card belongs to. */}

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
  validate: validateAllRequired,
  warn: warningTest
})(Client);

const mapStateToProps = state => ({
  formValues: getFormValues('client')(state)
});

export default connect(mapStateToProps, null)(reduxFormClient);