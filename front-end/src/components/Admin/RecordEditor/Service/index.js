import React, { Component } from 'react';
import { Field, reduxForm } from 'redux-form';

class Service extends Component {
  handlesubmit = event => {
    event.preventDefault();
    console.log(event.target);
  }
  
  render () {
    return (
      <>
        <form onSubmit={this.handlesubmit} className="record">
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
              <Field name="active" component="input" type="text" />
            </div>
            <button type="submit">Submit</button>
          </div>
        </form>
      </>
    );
  }
};

export default reduxForm({ form: 'service' })(Service);