import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import { Loader } from 'semantic-ui-react';
import RenderField from '../Fields/RenderField';
import RenderSearchField from '../Fields/RenderSearchField';
import AuditDetails from '../Fields/AuditDetails';
import validateAllRequired from '../../../../helpers/validateAllRequired';
import warningTest from '../../../../helpers/warningTest';

class Contract extends Component {
  render () {
    let { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    let { data } = currentTab.form;
    const editMode = !!data.id;

    this.changeField = (fieldName, value) => {
      this.props.change(fieldName, value);
    };

    if (!data) {
      return <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>;
    }

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{editMode ? 'ID: ' + data.id : ''}</p>
          <Field name="clientId" component={RenderSearchField} type="text" label="Клиент" changeField={this.changeField}
            parentEntity="contracts" childEntity="clients" parentId={data.id} childId={data.clientId} />
          <Field name="credit" component={RenderField} type="text" label="Кредит" />
          <Field name="packageId" component={RenderSearchField} type="text" label="Пакет" changeField={this.changeField}
            parentEntity="contracts" childEntity="pakets" parentId={data.id} childId={data.packageId} />

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

let reduxFormContract = reduxForm({
  form: 'contract',
  validate: validateAllRequired,
  warn: warningTest
})(Contract);

const mapStateToProps = state => ({
  formValues: getFormValues('contract')(state)
});

export default connect(mapStateToProps, null)(reduxFormContract);