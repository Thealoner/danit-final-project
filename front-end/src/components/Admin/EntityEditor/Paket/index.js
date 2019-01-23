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
import AuditDetails from '../AuditDetails';
import validateAllRequired from '../../../../helpers/validateAllRequired';
import warningTest from '../../../../helpers/warningTest';

class Paket extends Component {
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
          <Field name="term" component={RenderField} type="text" label="Срок действия, месяцев" />
          <Field name="accessWithoutCardTimesLimit" component={RenderField} type="text" label="Ограничения на вход без карты, раз" />
          <Field name="autoActivateAfterDays" component={RenderField} type="text" label="Автостарт через, дней" />
          <Field name="freezeDays" component={RenderField} type="text" label="Заморозка на общую длительность, дней" />
          <Field name="freezeMinTerm" component={RenderField} type="text" label="Минимальная длительность заморозки, дней" />
          <Field name="freezeTimes" component={RenderField} type="text" label="Количество возможных заморозок" />
          <Field name="guestVisits" component={RenderField} type="text" label="Гостевые посещения" />
          <Field name="limitAdditionalServices" component={RenderCheckbox} type="checkbox" label="Ограничение по количеству доп. услуг" />
          <Field name="limitUsageByPaymentPercentage" component={RenderCheckbox} type="checkbox" label="Ограничение использования по проценту оплаты" />
          <Field name="limitVisitTime" component={RenderCheckbox} type="checkbox" label="Ограничить времени пребывания" />
          <Field name="openDateAllowed" component={RenderCheckbox} type="checkbox" label="Продажа с открытой датой" />
          <Field name="purchasable" component={RenderCheckbox} type="checkbox" label="Доступен для покупки" />
          <Field name="usersMin" component={RenderField} type="text" label="Минимальное количество пользователей" />
          <Field name="visitTime" component={RenderField} type="text" label="Ограничение времени пребывания, минут" />
          <Field name="active" component={RenderCheckbox} type="checkbox" label="Активен" />

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

let reduxFormPaket = reduxForm({
  form: 'paket',
  validate: validateAllRequired,
  warn: warningTest
})(Paket);

const mapStateToProps = state => ({
  formValues: getFormValues('paket')(state)
});

export default connect(mapStateToProps, null)(reduxFormPaket);