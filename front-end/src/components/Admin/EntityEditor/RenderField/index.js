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
      <input {...input} placeholder={label} type={type} />
      {touched &&
        ((error && <span>{error}</span>) ||
          (warning && <span>{warning}</span>))}
    </div>
  </div>
);

export default RenderField;