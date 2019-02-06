import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  Field,
  reduxForm,
  getFormValues
} from 'redux-form';
import RenderField from '../Fields/RenderField';
import RenderCheckbox from '../Fields/RenderCheckbox';
import validate from './validate';

const numericFields = [
  'credit',
  'price',
  'cost',
  'unitsNumber',
  'term',
  'freezeTimes',
  'freezeDays',
  'accessWithoutCardTimesLimit',
  'freezeMinTerm',
  'autoActivateAfterDays',
  'guestVisits',
  'usersMin',
  'visitTime'
];
const requiredFields = [
  'title',
  'term',
  'price',
  'freezeTimes',
  'freezeDays',
  'freezeMinTerm',
  'accessWithoutCardTimesLimit',
  'autoActivateAfterDays',
  'guestVisits',
  'usersMin',
  'visitTime'
];

class Paket extends Component {
  isRequired = fieldName => {
    return requiredFields.includes(fieldName);
  }
  
  render () {
    let { currentTab, handleDelete, handleCancel, handleSubmit, submitting } = this.props;
    let { data } = currentTab.form;
    const editMode = !!data.id;
    
    return (
      <form onSubmit={handleSubmit} className="record">
        <div className="form-group field field-object">
          <p>{editMode ? 'ID: ' + data.id : ''}</p>
          <Field name="title" component={RenderField} type="text" label="Название" isRequired={this.isRequired} />
          <Field name="price" component={RenderField} type="text" label="Цена" isRequired={this.isRequired} />
          <Field name="term" component={RenderField} type="text" label="Срок действия, месяцев" isRequired={this.isRequired} />
          <Field name="accessWithoutCardTimesLimit" component={RenderField} type="text" label="Ограничения на вход без карты, раз" isRequired={this.isRequired} />
          <Field name="autoActivateAfterDays" component={RenderField} type="text" label="Автостарт через, дней" isRequired={this.isRequired} />
          <Field name="freezeDays" component={RenderField} type="text" label="Заморозка на общую длительность, дней" isRequired={this.isRequired} />
          <Field name="freezeMinTerm" component={RenderField} type="text" label="Минимальная длительность заморозки, дней" isRequired={this.isRequired} />
          <Field name="freezeTimes" component={RenderField} type="text" label="Количество возможных заморозок" isRequired={this.isRequired} />
          <Field name="guestVisits" component={RenderField} type="text" label="Гостевые посещения" isRequired={this.isRequired} />
          <Field name="limitAdditionalServices" component={RenderCheckbox} type="checkbox" label="Ограничение по количеству доп. услуг" isRequired={this.isRequired} />
          <Field name="limitUsageByPaymentPercentage" component={RenderCheckbox} type="checkbox" label="Ограничение использования по проценту оплаты" isRequired={this.isRequired} />
          <Field name="limitVisitTime" component={RenderCheckbox} type="checkbox" label="Ограничить времени пребывания" isRequired={this.isRequired} />
          <Field name="openDateAllowed" component={RenderCheckbox} type="checkbox" label="Продажа с открытой датой" isRequired={this.isRequired} />
          <Field name="purchasable" component={RenderCheckbox} type="checkbox" label="Доступен для покупки" isRequired={this.isRequired} />
          <Field name="usersMin" component={RenderField} type="text" label="Минимальное количество пользователей" isRequired={this.isRequired} />
          <Field name="visitTime" component={RenderField} type="text" label="Ограничение времени пребывания, минут" isRequired={this.isRequired} />
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
};

let reduxFormPaket = reduxForm({
  form: 'paket',
  validate: (fields) => validate(fields, {
    requiredFields,
    numericFields
  })
})(Paket);

const mapStateToProps = state => ({
  formValues: getFormValues('paket')(state)
});

export default connect(mapStateToProps, null)(reduxFormPaket);