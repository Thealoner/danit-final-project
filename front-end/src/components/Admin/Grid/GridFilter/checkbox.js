import React from 'react';

const Checkbox = ({ type = 'checkbox', name, checked = false, onChange, title, className }) => (
  <input type={type} name={name} checked={checked} onChange={onChange} title={title} className={className} />
);

export default Checkbox;