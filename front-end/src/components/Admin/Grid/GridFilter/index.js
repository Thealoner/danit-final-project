import React, { Component } from 'react';
import './index.scss';
import { getGridData, changeFilterStatus } from '../../../../actions/tabActions';
import { connect } from 'react-redux';
import DatePicker from 'react-datepicker';
import { formatDateString } from '../../../../helpers/common';

import 'react-datepicker/dist/react-datepicker.css';

let defaultFilter = {
  field: 'search',
  value: ''
};

class GridFilter extends Component {
  state = {
    ...defaultFilter,
    activeFilter: ''
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
      ...defaultFilter,
      activeFilter: ''
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

  handleInputChangeDate = (date) => {
    this.setState({
      value: date
    });
  }

  handleInputChangeFilter = event => {
    const { changeFilterStatus } = this.props;
    const target = event.target;
    const value = target.value;
    const name = target.name;

    let index = event.nativeEvent.target.selectedIndex;
    let optionName = event.nativeEvent.target[index].text;

    this.setState({
      [name]: value,
      activeFilter: optionName
    });

    changeFilterStatus(false);
  };

  handleSubmit = event => {
    let { field, value } = this.state;
    let filterString;

    if (field.toLowerCase().includes('date')) {
      value = formatDateString(value);
    }

    filterString = '&' + field + '=' + value;

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

    let valueField = <input name="value" type="text" value={this.state.value} onChange={this.handleInputChange}/>;
    if (this.state.activeFilter === 'Пол') {
      valueField = <select name="value" value={this.state.value} onChange={this.handleInputChange}>
        <option value='' selected="selected">Все</option>
        <option value='F'>Женский</option>
        <option value='M'>Мужской</option>
      </select>;
    }

    if (this.state.activeFilter === 'Дата Рождения') {
      valueField = <DatePicker selected={this.state.value} dateFormat="dd-MM-yyyy"
        scrollableYearDropdown
        scrollableMonthDropdown
        showYearDropdown
        showMonthDropdown
        yearDropdownItemNumber={60} onChange={this.handleInputChangeDate} />;
    }

    if (this.state.activeFilter === 'Дата начала') {
      valueField = <DatePicker selected={this.state.value} dateFormat="dd-MM-yyyy"
        scrollableYearDropdown
        scrollableMonthDropdown
        showYearDropdown
        showMonthDropdown
        yearDropdownItemNumber={60} onChange={this.handleInputChangeDate} />;
    }

    if (this.state.activeFilter === 'Дата окончания') {
      valueField = <DatePicker selected={this.state.value} dateFormat="dd-MM-yyyy"
        scrollableYearDropdown
        scrollableMonthDropdown
        showYearDropdown
        showMonthDropdown
        yearDropdownItemNumber={60} onChange={this.handleInputChangeDate} />;
    }

    if (this.state.activeFilter === 'Активен') {
      valueField = <select name="value" value={this.state.value} onChange={this.handleInputChange}>
        <option value='' selected="selected">Все</option>
        <option value='true'>Да</option>
        <option value='false'>Нет</option>
      </select>;
    }

    if (this.state.activeFilter === 'Mожно купить?') {
      valueField = <select name="value" value={this.state.value} onChange={this.handleInputChange}>
        <option value='' selected="selected">Все</option>
        <option value='true'>Да</option>
        <option value='false'>Нет</option>
      </select>;
    }
    return (
      <form onSubmit={this.handleSubmit} className="filter">
        <div className="filter__value">
          <span className="filter__wrapper">
            <label className="filter__label">Поле: </label>
            <select className="filter__select" name="field" value={this.state.field} onChange={this.handleInputChangeFilter}>
              {this.renderFields()}
            </select>
          </span>
          <span className="filter__wrapper">
            <label className="filter__label">Значение: </label>
            {valueField}
          </span>
        </div>
        <button name="filter" type="submit" disabled={currentTab.filtered} className="filter__button">Применить фильтр</button>
        <button name="clear" onClick={this.clearFilter} type="button" className="filter__button">Очистить фильтр</button>
        <span className='filter__status'>
          {this.state.activeFilter ? 'Фильтр: ' : null}
          {this.state.activeFilter ? this.state.activeFilter : null}
        </span>
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