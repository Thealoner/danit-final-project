import React from 'react';
import { Dropdown } from 'semantic-ui-react';

const RenderSelect = ({
  input,
  label,
  type,
  name,
  options,
  meta: { touched, error, warning }
}) => (
  <div className="form-group field field-string">
    <label className="control-label">{label}</label>
    <div>
      <Dropdown fluid selection options={options} className={error ? 'field_error-input' : null || warning ? 'field_warning-input' : null} />
      {touched &&
        ((error && <div className="field_error">{error}</div>) ||
          (warning && <div className="field_warning">{warning}</div>))}
    </div>
  </div>
);

export default RenderSelect;