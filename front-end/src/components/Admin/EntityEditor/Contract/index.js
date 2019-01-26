import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import { Loader } from 'semantic-ui-react';
import RenderField from '../RenderField';
import RenderSearchField from '../RenderSearchField';
import AuditDetails from '../AuditDetails';
import validateAllRequired from '../../../../helpers/validateAllRequired';
import warningTest from '../../../../helpers/warningTest';

class Contract extends Component {
  render () {
    let { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    let { data } = currentTab.form;

    this.changeField = (fieldName, value) => {
      // let { handleChange, currentTab } = this.props;
      // let { data } = currentTab.form;
      
      // handleChange({
      //   data: {
      //     ...data,
      //     [fieldName]: value
      //   }
      // });
      this.props.change(fieldName, value);
    }

    if (!data) {
      return <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>;
    }

    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{data.id ? 'ID: ' + data.id : ''}</p>
          <Field name="clientId" component={RenderSearchField} type="text" label="ID клиента" changeField={this.changeField}/>
          <Field name="credit" component={RenderField} type="text" label="Кредит" />
          <Field name="packageId" component={RenderField} type="text" label="ID пакета" />
          {/* <Field name="cards" component={RenderField} type="text" label="Карты" /> */}

          <button type="submit" className="record__button" disabled={!currentTab.form.edited || submitting}>Сохранить</button>
          <button type="button" className="record__button" onClick={handleDelete} disabled={!data.id}>Удалить</button>
          <button type="button" className="record__button" onClick={handleCancel}>Отмена</button>

          <AuditDetails data={data} />
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