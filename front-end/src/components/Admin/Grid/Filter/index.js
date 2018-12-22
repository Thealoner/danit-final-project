import React, { Component } from 'react';
import './index.scss';

let defaultFilter = {
  field: 'search',
  value: ''
};
class Filter extends Component {
  state = {
    ...defaultFilter
  };

  apply_filter = React.createRef();

  handleInputChange = event => {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  };

  onSubmit = event => {
    event.preventDefault();
    const filterString = '&' + this.state.field + '=' + this.state.value;
    this.props.applyFilter(filterString);
  };

  clearFilter = () => {
    this.props.clearFilter();
    this.apply_filter.classList.remove('filter__apply-btn-active');
    this.setState({
      ...defaultFilter
    });
  };

  renderFields = () => {
    const { columns } = this.props;
    
    let fields = columns.map(column => (
      <option key={column.field} value={column.field}>{column.title}</option>
    ));

    fields.unshift(<option key="search" value="search">Все</option>);
      
    return fields;
  };

  applyFilter = () => this.apply_filter.classList.add('filter__apply-btn-active');

  render () {
    return (
      <form onSubmit={this.onSubmit} className="filter" >
        <span>
          <label>Поле: </label>
          <select name="field" value={this.state.field} onChange={this.handleInputChange}>
            {this.renderFields()}
          </select>
        </span>

        <span>
          <label>Значение: </label>
          <input name="value" type="text" placeholder="" value={this.state.value} onChange={this.handleInputChange} />
        </span>

        <button name="filter" type="submit" className="filter__apply-btn" ref={el => this.apply_filter = el} onClick={() => this.applyFilter()}>Применить фильтр</button>
        <button name="clear" className="filter__clear-btn" onClick={this.clearFilter} type="button">Очистить фильтр</button>
      </form>
    );
  }
}

export default Filter;