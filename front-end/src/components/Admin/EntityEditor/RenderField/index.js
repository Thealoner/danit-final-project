import React from 'react';

const RenderField = ({
  input,
  label,
  type,
  name,
  meta: { touched, error, warning }
}) => (
  <div className="form-group field field-string">
    <label className="control-label" htmlFor={name}>{label}</label>
    <div>
      <input {...input} placeholder={label} type={type} className={error ? 'field_error-input' : null || warning ? 'field_warning-input' : null} />
      {touched &&
        ((error && <div className="field_error">{error}</div>) ||
          (warning && <div className="field_warning">{warning}</div>))}
    </div>
  </div>
);

export default RenderField;