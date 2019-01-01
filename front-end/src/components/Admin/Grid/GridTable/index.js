import React, { Component } from 'react';
import { connect } from 'react-redux';
import { getFormData } from '../../../../actions/tabActions';
import Tabulator from 'tabulator-tables';
// import { ReactTabulator } from 'react-tabulator';
// import 'react-tabulator/lib/styles.css';

class GridTable extends Component {
  tabulator = null;
  tabulatorTable = React.createRef();

  rowClick = (e, row) => {
    const { currentTab, getFormData } = this.props;

    getFormData(currentTab.tabKey, row.getData().id, 'edit');
  };

  render () {
    const { currentTab } = this.props;
    console.log(currentTab.grid.columns);

    /* return (
      <ReactTabulator
        data={currentTab.grid.data}
        columns={currentTab.grid.columns}
        rowClick={this.rowClick}
        tooltips={true}
        movableRows={false}
        layout={'fitDataFill'}
      />
    ); */

    return (
      <div ref={el => (this.tabulatorTable = el)} className="tabulator"/>
    );
  }

  componentDidMount () {
    const { currentTab } = this.props;

    this.tabulator = new Tabulator(this.tabulatorTable, {
      data: currentTab.grid.data,
      columns: currentTab.grid.columns,
      rowClick: this.rowClick,
      tooltips: true,
      movableRows: false,
      layout: 'fitDataFill'
    });
  }

  componentDidUpdate () {
    const { currentTab } = this.props;
    this.tabulator.setColumns(currentTab.grid.columns);
    this.tabulator.setData(currentTab.grid.data);
  }
}

const mapDispatchToProps = dispatch => {
  return {
    getFormData: (tabKey, id, mode) => {
      dispatch(getFormData(tabKey, id, mode));
    }
  };
};

export default connect(null, mapDispatchToProps)(GridTable);