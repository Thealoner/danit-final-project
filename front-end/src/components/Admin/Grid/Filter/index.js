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
    event.preventDefault();
    let filterString = '&' + this.state.field + '=' + this.state.value;
    this.props.applyFilter(filterString);
  }

  clearFilter = () => {
    this.props.clearFilter();

    this.setState({
      field: '',
      value: ''
    });
  }

  renderFields = () => {
    let { columns } = this.props;
    
    return columns.map(column => (
      <option value={column.field}>{column.title}</option>
    ));
  }

  render () {
    return (
      <form onSubmit={this.onSubmit}>
        <span>
          <label>Field: </label>
          <select name="field" value={this.state.field} onChange={this.handleInputChange}>
            <option></option>
            {this.renderFields()}
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