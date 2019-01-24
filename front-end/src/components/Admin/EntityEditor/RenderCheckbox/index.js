import React from 'react';

const RenderCheckbox = ({
  input,
  label,
  type,
  name,
  meta: { touched, error, warning }
}) => (
  <div className="form-group field field-boolean">
    <div className="checkbox">
      <label>
        <input {...input} type={type} />
        {label}
        {touched &&
          ((error && <div className="field_error-ch">{error}</div>) ||
            (warning && <div className="field_warning-ch">{warning}</div>))}
      </label>
    </div>
  </div>
);

export default RenderCheckbox;