import React, { Component } from 'react';
import { connect } from 'react-redux';
import { getFormData, getSortedData } from '../../../../actions/tabActions';
import Tabulator from 'tabulator-tables';
import './index.scss';

class GridTable extends Component {
  tabulator = null;
  tabulatorTable = React.createRef();

  rowClick = (e, row) => {
    const { currentTab, getFormData } = this.props;

    getFormData(currentTab.tabKey, row.getData().id);
  };

  render () {
    return (
      <div ref={el => (this.tabulatorTable = el)} className="tabulator"/>
    );
  }

  componentDidMount () {
    const { currentTab, getSortedData } = this.props;
    const sorting = currentTab.grid.sorting;

    this.tabulator = new Tabulator(this.tabulatorTable, {
      data: currentTab.grid.data,
      columns: currentTab.grid.columns,
      rowClick: this.rowClick,
      tooltips: true,
      movableRows: false,
      layout: 'fitDataFill',
      columnHeaderSortMulti: false,
      dataSorting: sorters => {
        if (sorters.length > 0 && (sorters[0].field !== sorting.column || sorters[0].dir !== sorting.direction)) {
          getSortedData({
            sortColumn: sorters[0].field,
            sortDirection: sorters[0].dir,
            tabKey: currentTab.tabKey,
            columns: currentTab.grid.columns,
            page: 1,
            filter: currentTab.grid.filter
          });
        }
      }
    });

    this.tabulator.setSort(sorting.column, sorting.direction);
  }

  componentDidUpdate () {
    const { currentTab } = this.props;
    this.tabulator.setColumns(currentTab.grid.columns);
    this.tabulator.setData(currentTab.grid.data);
  }
}

const mapStateToProps = state => {
  let currentTab = null;

  if (state.tabs.activeKey && state.tabs.tabsArray.length > 0) {
    currentTab = state.tabs.tabsArray.filter(t => t.tabKey === state.tabs.activeKey)[0];
  }

  return {
    sorting: currentTab.grid.sorting
  };
};

export default connect(mapStateToProps, { getFormData, getSortedData })(GridTable);