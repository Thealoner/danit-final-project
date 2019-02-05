import React from 'react';

const RenderField = ({
  input,
  label,
  type,
  required,
  meta: { touched, error, warning }
}) => (
  <div className="form-group field field-string">
    <label className="control-label">{label} <span className="field_required">{required ? '*' : ''}</span></label>
    <div>
      <input {...input} type={type} className={(touched && error) ? 'field_error-input' : null || warning ? 'field_warning-input' : null} required />
      { touched &&
        ((error && <div className="field_error">{error}</div>) ||
          (warning && <div className="field_warning">{warning}</div>))}
    </div>
  </div>
);

export default RenderField;