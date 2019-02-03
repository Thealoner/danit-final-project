import React, { Component } from 'react';
import './index.scss';
import { getGridData, setFilter } from '../../../../actions/tabActions';
import { connect } from 'react-redux';
import Checkbox from './checkbox';

class GridFilter extends Component {
  clearFilter = () => {
    const { currentTab, getGridData } = this.props;

    getGridData({
      tabKey: currentTab.tabKey,
      columns: currentTab.grid.columns
    });
  }

  handleInputChange = event => {
    const { setFilter, currentTab } = this.props;
    const { filter } = currentTab;
    const target = event.target;
    let value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    if (filter.field.toLowerCase().includes('date')) {
      setFilter({
        ...filter,
        [name]: value,
        value: value,
        isFiltered: false,
        filterStatus: null
      });

      return;
    }

    setFilter({
      ...filter,
      [name]: value,
      isFiltered: false,
      filterStatus: null
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
      isFiltered: false,
      filterStatus: null
    });
  }

  handleSubmit = event => {
    const { currentTab, getGridData } = this.props;
    let { field, value, isExact, fromDate, toDate, activeFilter } = currentTab.filter;

    if (activeFilter === 'Дата Рождения') {
      value = toDate ? (fromDate + '/' + toDate) : fromDate;

      if (fromDate === toDate) {
        value = fromDate;
      }
    }

    event.preventDefault();

    getGridData({
      tabKey: currentTab.tabKey,
      columns: currentTab.grid.columns,
      filter: {
        ...currentTab.filter,
        field: field,
        value: value,
        isExact: isExact,
        isFiltered: true,
        filterStatus: this.showFilterStatus(activeFilter, value)
      }
    });
  }

  renderFields = () => {
    const { currentTab } = this.props;

    let fields = currentTab.grid.columns.map(column => (
      <option key={column.field} value={column.field}>{column.title}</option>
    ));

    fields.unshift(<option key="search" value="search">Все</option>);

    return fields;
  }

  showFilterStatus = (activeFilter, value) => {
    if (value === 'F') {
      value = 'Женский';
    }

    if (value === 'M') {
      value = 'Мужской';
    }

    if (activeFilter && value) {
      return (
        <><span className="filter__status-label">Фильтр: </span>{activeFilter || null} = {value}</>
      );
    } else {
      return null;
    }
  }

  render () {
    const { currentTab } = this.props;
    const filter = currentTab.filter;

    let valueField = <input className="filter__input" name="value" type="text" value={filter.value}
      onChange={this.handleInputChange} autoComplete="off"/>;
    if (filter.activeFilter === 'Пол') {
      valueField = <select name="value" value={filter.value} onChange={this.handleInputChange}>
        <option value='' defaultValue="Все">Все</option>
        <option value='F'>Женский</option>
        <option value='M'>Мужской</option>
      </select>;
    }

    if (filter.activeFilter === 'Дата Рождения') {
      valueField = <div>
        <label className="filter__label filter__label--range">с:</label>
        <input className="filter__input filter__input--range" name="fromDate" type="date" value={filter.fromDate || ''}
          onChange={this.handleInputChange}/>
        <label className="filter__label filter__label--range">по:</label>
        <input className="filter__input filter__input--range" name="toDate" type="date"
          value={filter.toDate || filter.fromDate || ''} onChange={this.handleInputChange}/>
      </div>;
    }

    if (filter.activeFilter === 'Дата начала') {
      valueField = <input className="filter__input" name="date" type="date" value={filter.date || ''}
        onChange={this.handleInputChange}/>;
    }

    if (filter.activeFilter === 'Дата окончания') {
      valueField = <input className="filter__input" name="date" type="date" value={filter.date || ''}
        onChange={this.handleInputChange}/>;
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
          <Checkbox name="isExact" checked={filter.isExact} onChange={this.handleInputChange}
            className="filter__checkbox" title="Точный поиск"/>
          <button type="submit" disabled={filter.isFiltered} className="filter__button filter__button--apply">Применить
            фильтр
          </button>
        </div>
        <div className="filter__wrapper">
          <button onClick={this.clearFilter} type="button" className="filter__button filter__button--clear">Очистить
            фильтр
          </button>
        </div>
        <div className="filter__wrapper">
          <span className='filter__status'>
            {filter.filterStatus || null}
          </span>
        </div>
      </form>
    );
  }
}

export default connect(null, { getGridData, setFilter })(GridFilter);