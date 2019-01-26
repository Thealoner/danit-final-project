import React, { Component } from 'react';
import './index.scss';
import { getGridData, setFilter } from '../../../../actions/tabActions';
import { connect } from 'react-redux';
import DatePicker from 'react-datepicker';
import Checkbox from './checkbox';
import moment from 'moment';

import 'react-datepicker/dist/react-datepicker.css';

class GridFilter extends Component {
  clearFilter = () => {
    const { currentTab, getGridData } = this.props;

    getGridData({
      tabKey: currentTab.tabKey,
      columns: currentTab.grid.columns
    });
  };

  handleInputChange = event => {
    const { setFilter, currentTab } = this.props;
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    setFilter({
      ...currentTab.filter,
      [name]: value,
      isFiltered: false
    });
  };

  handleInputChangeDate = (date) => {
    const { setFilter, currentTab } = this.props;
    const formattedDate = moment(date).format('DD-MM-YYYY');

    setFilter({
      ...currentTab.filter,
      value: formattedDate,
      isFiltered: false,
      date: date
    });
  }

  handleInputChangeFilter = event => {
    const { setFilter, currentTab } = this.props;
    const target = event.target;
    const value = target.value;
    const name = target.name;

    let index = event.nativeEvent.target.selectedIndex;
    let optionName = event.nativeEvent.target[index].text;

    setFilter({
      ...currentTab.filter,
      value: '',
      [name]: value,
      activeFilter: optionName,
      isFiltered: false
    });
  };

  handleSubmit = event => {
    const { currentTab, getGridData } = this.props;
    let { field, value, isExact } = currentTab.filter;

    event.preventDefault();

    getGridData({
      tabKey: currentTab.tabKey,
      columns: currentTab.grid.columns,
      filter: {
        ...currentTab.filter,
        field: field,
        value: value,
        isExact: isExact,
        isFiltered: true
      }
    });
  };

  renderFields = () => {
    const { currentTab } = this.props;

    let fields = currentTab.grid.columns.map(column => (
      <option key={column.field} value={column.field}>{column.title}</option>
    ));

    fields.unshift(<option key="search" value="search">Все</option>);

    return fields;
  };

  showFilterStatus = () => {
    const { filter } = this.props.currentTab;

    if (filter.activeFilter) {
      return 'Фильтр: ' + filter.activeFilter + (filter.value ? (' = ' + filter.value) : '');
    } else {
      return filter.value ? ('Фильтр: ' + filter.value) : null;
    }
  }

  render () {
    const { currentTab } = this.props;
    const filter = currentTab.filter;

    let valueField = <input name="value" type="text" value={filter.value} onChange={this.handleInputChange} autoComplete="off"/>;
    if (filter.activeFilter === 'Пол') {
      valueField = <select name="value" value={filter.value} onChange={this.handleInputChange}>
        <option value='' defaultValue="selected">Все</option>
        <option value='F'>Женский</option>
        <option value='M'>Мужской</option>
      </select>;
    }

    if (filter.activeFilter === 'Дата Рождения') {
      valueField = <DatePicker selected={filter.date} dateFormat="dd-MM-yyyy"
        scrollableYearDropdown
        scrollableMonthDropdown
        showYearDropdown
        showMonthDropdown
        yearDropdownItemNumber={60} onChange={this.handleInputChangeDate} />;
    }

    if (filter.activeFilter === 'Дата начала') {
      valueField = <DatePicker selected={filter.date} dateFormat="dd-MM-yyyy"
        scrollableYearDropdown
        scrollableMonthDropdown
        showYearDropdown
        showMonthDropdown
        yearDropdownItemNumber={60} onChange={this.handleInputChangeDate} />;
    }

    if (filter.activeFilter === 'Дата окончания') {
      valueField = <DatePicker selected={filter.date} dateFormat="dd-MM-yyyy"
        scrollableYearDropdown
        scrollableMonthDropdown
        showYearDropdown
        showMonthDropdown
        yearDropdownItemNumber={60} onChange={this.handleInputChangeDate} />;
    }

    if (filter.activeFilter === 'Активен') {
      valueField = <select name="value" value={filter.value} onChange={this.handleInputChange}>
        <option value='' defaultValue="selected">Все</option>
        <option value='true'>Да</option>
        <option value='false'>Нет</option>
      </select>;
    }

    if (filter.activeFilter === 'Mожно купить?') {
      valueField = <select name="value" value={filter.value} onChange={this.handleInputChange}>
        <option value='' defaultValue="selected">Все</option>
        <option value='true'>Да</option>
        <option value='false'>Нет</option>
      </select>;
    }
    return (
      <form onSubmit={this.handleSubmit} className="filter">
        <div className="filter__wrapper">
          <label className="filter__label">Поле: </label>
          <select className="filter__select" name="field" value={filter.field} onChange={this.handleInputChangeFilter}>
            {this.renderFields()}
          </select>
          <label className="filter__label">Значение: </label>
          {valueField}
        </div>
        <div className="filter__wrapper">
          <Checkbox name="isExact" checked={filter.isExact} onChange={this.handleInputChange} className="filter__checkbox" title="Точный поиск" />
          <button type="submit" disabled={filter.isFiltered} className="filter__button filter__button--apply">Применить фильтр</button>
        </div>
        <div className="filter__wrapper">
          <button onClick={this.clearFilter} type="button" className="filter__button filter__button--clear">Очистить фильтр</button>
        </div>
        <div className="filter__wrapper">
          <span className='filter__status'>
            {this.showFilterStatus()}
          </span>
        </div>
      </form>
    );
  }
}

const mapDispatchToProps = dispatch => {
  return {
    getGridData: (options) => {
      dispatch(getGridData(options));
    },
    setFilter: (filter) => {
      dispatch(setFilter(filter));
    }
  };
};

export default connect(null, mapDispatchToProps)(GridFilter);