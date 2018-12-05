import React, { Component } from 'react';
import './index.scss';

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
  };

  clearFilter = () => {
    this.props.clearFilter();

    this.setState({
      field: '',
      value: ''
    });
  };

  renderFields = () => {
    let { columns } = this.props;
    
    return columns.map(column => (
      <option key={column.field} value={column.field}>{column.title}</option>
    ));
  };

  render () {
    return (
      <form onSubmit={this.onSubmit} className="filter">
        <span>
          <label>Поле: </label>
          <select name="field" value={this.state.field} onChange={this.handleInputChange}>
            <option>Все</option>
            {this.renderFields()}
          </select>
        </span>

        <span>
          <label>Значение: </label>
          <input name="value" type="text" placeholder="" value={this.state.value} onChange={this.handleInputChange} />
        </span>

        <button name="filter" type="submit">Применить фильтр</button>
        <button name="clear" onClick={this.clearFilter} type="button">Очистить фильтр</button>
      </form>
    );
  }
}

export default Filter;