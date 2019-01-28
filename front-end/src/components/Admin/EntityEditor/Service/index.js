import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import { Loader } from 'semantic-ui-react';
import RenderField from '../Fields/RenderField';
import RenderCheckbox from '../Fields/RenderCheckbox';
import AuditDetails from '../Fields/AuditDetails';
import validateAllRequired from '../../../../helpers/validateAllRequired';
import warningTest from '../../../../helpers/warningTest';

class Service extends Component {
  render () {
    let { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    let { data } = currentTab.form;
    const editMode = !!data.id;

    if (!data) {
      return <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>;
    }

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{editMode ? 'ID: ' + data.id : ''}</p>
          <Field name="title" component={RenderField} type="text" label="Название" />
          <Field name="price" component={RenderField} type="text" label="Цена" />
          <Field name="cost" component={RenderField} type="text" label="Себестоимость" />
          <Field name="unit" component={RenderField} type="text" label="Единица измерения" />
          <Field name="unitsNumber" component={RenderField} type="text" label="Кол-во единиц" />
          <Field name="active" component={RenderCheckbox} type="checkbox" label="Активен" />

          <button type="submit" className="record__button" disabled={!currentTab.form.edited || submitting}>Сохранить</button>
          <button type="button" className="record__button" onClick={handleDelete} disabled={!editMode}>Удалить</button>
          <button type="button" className="record__button" onClick={handleCancel}>Отмена</button>

          { editMode ? <AuditDetails data={data} /> : '' }
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
  validate: validateAllRequired,
  warn: warningTest
})(Service);

const mapStateToProps = state => ({
  formValues: getFormValues('service')(state)
});

export default connect(mapStateToProps, null)(reduxFormService);