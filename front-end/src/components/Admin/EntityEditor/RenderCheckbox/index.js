import React from 'react';

const RenderCheckbox = ({
  input,
  label,
  type,
  name,
  meta: { touched, error, warning }
}) => (
  <div className="form-group field field-checkbox">
    <label className="control-label">{label}
      <div className="checkbox">
        <input {...input} type={type} />
        {touched &&
          ((error && <div className="field_error-ch">{error}</div>) ||
            (warning && <div className="field_warning-ch">{warning}</div>))}
      </div>
    </label>
  </div>
);

export default RenderCheckbox;