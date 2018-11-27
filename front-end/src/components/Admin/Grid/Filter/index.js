import React, { Component } from 'react';

class Filter extends Component {
  constructor (props) {
    super(props);
    this.state = {
      field: '',
      value: ''
    };

    this.handleInputChange = this.handleInputChange.bind(this);
  }

  handleInputChange (event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  onSubmit = (event) => {
    // this.tabulator.setFilter('id', '>', 1001);
    event.preventDefault();

    this.props.applyFilter({
      field: this.state.field,
      value: this.state.value
    });
  }

  clearFilter = () => {
    this.props.clearFilter();

    this.setState({
      field: '',
      value: ''
    });
  }

  render () {
    return (
      <form onSubmit={this.onSubmit}>
        <span>
          <label>Field: </label>
          <select name="field" value={this.state.field} onChange={this.handleInputChange}>
            <option></option>
            <option value="id">id</option>
            <option value="firstName">firstName</option>
            <option value="gender">gender</option>
            <option value="birthDate">birthDate</option>
            <option value="phoneNumber">phoneNumber</option>
            <option value="email">Date Of Birth</option>
            <option value="active">Drives</option>
          </select>
        </span>

        <span>
          <label>Value: </label>
          <input name="value" type="text" placeholder="value to filter" value={this.state.value} onChange={this.handleInputChange} />
        </span>

        <button name="filter" type="submit">Apply Filter</button>
        <button name="clear" onClick={this.clearFilter} type="button">Clear Filter</button>
      </form>
    );
  };
}

export default Filter;