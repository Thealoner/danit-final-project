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

    this.tabulator = new Tabulator(this.tabulatorTable, {
      data: currentTab.grid.data,
      columns: currentTab.grid.columns,
      rowClick: this.rowClick,
      tooltips: true,
      movableRows: false,
      layout: 'fitDataFill',
      columnHeaderSortMulti: false,
      dataSorting: sorters => {
        if (sorters.length > 0) {
          getSortedData(
            sorters[0].field,
            sorters[0].dir,
            currentTab.tabKey,
            currentTab.grid.columns,
            1,
            currentTab.grid.filter
          );
        }
      }
    });
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

const mapDispatchToProps = dispatch => {
  return {
    getFormData: (tabKey, id, mode) => {
      dispatch(getFormData(tabKey, id, mode));
    },
    getSortedData: (sortColumn, sortDirection, tabKey, columns, page, filter) => {
      dispatch(getSortedData({
        sortColumn,
        sortDirection,
        tabKey,
        columns,
        page,
        filter
      }));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(GridTable);