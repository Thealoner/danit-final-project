import React, { Component } from 'react';
import './index.scss';
import { getGridData, changeFilterStatus } from '../../../../actions/tabActions';
import { connect } from 'react-redux';

let defaultFilter = {
  field: 'search',
  value: ''
};

class GridFilter extends Component {
  state = {
    ...defaultFilter
  };

  applyFilter = (filterString) => {
    const { currentTab, getGridData } = this.props;

    getGridData({
      tabKey: currentTab.tabKey,
      page: 0,
      size: currentTab.grid.meta.totalElements,
      filterString: filterString,
      columns: currentTab.grid.columns,
      filtered: true
    });
  };

  clearFilter = () => {
    const { currentTab, getGridData } = this.props;

    getGridData({
      tabKey: currentTab.tabKey,
      columns: currentTab.grid.columns
    });

    this.setState({
      ...defaultFilter
    });
  };

  handleInputChange = event => {
    const { changeFilterStatus } = this.props;
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });

    changeFilterStatus(false);
  };

  handleSubmit = event => {
    const filterString = '&' + this.state.field + '=' + this.state.value;

    event.preventDefault();
    this.applyFilter(filterString);
  };

  renderFields = () => {
    const { currentTab } = this.props;

    let fields = currentTab.grid.columns.map(column => (
      <option key={column.field} value={column.field}>{column.title}</option>
    ));

    fields.unshift(<option key="search" value="search">Все</option>);

    return fields;
  };

  render () {
    const { currentTab } = this.props;

    return (
      <form onSubmit={this.handleSubmit} className="filter">
        <div>
          <span>
            <label>Поле: </label>
            <select name="field" value={this.state.field} onChange={this.handleInputChange}>
              {this.renderFields()}
            </select>
          </span>
          <span>
            <label>Значение: </label>
            <input name="value" type="text" value={this.state.value} onChange={this.handleInputChange}/>
          </span>
        </div>
        <button name="filter" type="submit" disabled={currentTab.filtered}>Применить фильтр</button>
        <button name="clear" onClick={this.clearFilter} type="button">Очистить фильтр</button>
      </form>
    );
  }
}

const mapDispatchToProps = dispatch => {
  return {
    getGridData: (options) => {
      dispatch(getGridData(options));
    },
    changeFilterStatus: (filtered) => {
      dispatch(changeFilterStatus(filtered));
    }
  };
};

export default connect(null, mapDispatchToProps)(GridFilter);