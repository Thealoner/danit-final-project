import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import { Loader } from 'semantic-ui-react';
import RenderField from '../RenderField';
import RenderCheckbox from '../RenderCheckbox';
import validateAllRequired from '../../../../helpers/validateAllRequired';
import warningTest from '../../../../helpers/warningTest';

class Service extends Component {
  render () {
    let { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    let { data } = currentTab.form;

    if (!data) {
      return <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>;
    }

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{data.id ? 'ID: ' + data.id : ''}</p>
          <Field name="title" component={RenderField} type="text" label="Название" />
          <Field name="price" component={RenderField} type="text" label="Цена" />
          <Field name="cost" component={RenderField} type="text" label="Себестоимость" />
          <Field name="unit" component={RenderField} type="text" label="Единица измерения" />
          <Field name="unitsNumber" component={RenderField} type="text" label="Кол-во единиц" />
          <Field name="active" component={RenderCheckbox} type="checkbox" label="Активен" />
          {/* <p>Включенные сервисы: {data.services.join(', ')}</p> */}

          <button type="submit" className="record__button" disabled={!currentTab.form.edited || submitting}>Сохранить</button>
          <button type="button" className="record__button" onClick={handleDelete} disabled={!data.id}>Удалить</button>
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
  validate: validateAllRequired,
  warn: warningTest
})(Service);

const mapStateToProps = state => ({
  formValues: getFormValues('service')(state)
});

export default connect(mapStateToProps, null)(reduxFormService);