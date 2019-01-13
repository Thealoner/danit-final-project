import React, { Component } from 'react';
import { Field, reduxForm } from 'redux-form';
import { Loader } from 'semantic-ui-react';

class Service extends Component {
  render () {
    let { currentTab, handlesubmit, pristine, submitting } = this.props;
    let { data } = currentTab.form;

    if (!data) {
      return <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>;
    }

    return (
      <form onSubmit={handlesubmit} className="record">
        <div className="form-group field field-object">
          <p>ID:</p>
          <div className="form-group field field-string">
            <label className="control-label" htmlFor="title">Название</label>
            <Field name="title" component="input" type="text" />
          </div>
          <div className="form-group field field-string">
            <label className="control-label" htmlFor="price">Цена</label>
            <Field name="price" component="input" type="text" />
          </div>
          <div className="form-group field field-string">
            <label className="control-label" htmlFor="cost">Себестоимость</label>
            <Field name="cost" component="input" type="text" />
          </div>
          <div className="form-group field field-string">
            <label className="control-label" htmlFor="unit">Единица измерения</label>
            <Field name="unit" component="input" type="text" />
          </div>
          <div className="form-group field field-string">
            <label className="control-label" htmlFor="unitsNumber">Кол-во единиц</label>
            <Field name="unitsNumber" component="input" type="text" />
          </div>
          <div className="form-group field field-string">
            <label className="control-label" htmlFor="active">Активен</label>
            <Field name="active" component="input" type="checkbox" />
          </div>

          <button type="submit" className="record__button" disabled={pristine || submitting}>Сохранить</button>
          <button type="button" className="record__button">Удалить</button>
          <button type="button" className="record__button">Отмена</button>
        </div>
      </form>
    );
  }

  componentDidMount () {
    let { currentTab, initialize } = this.props;
    let { data } = currentTab.form;
    initialize(data);
  }
};

let reduxFormService = reduxForm({ form: 'service' })(Service);

export default reduxFormService;