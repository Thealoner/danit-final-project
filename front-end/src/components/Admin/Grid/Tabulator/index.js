import React, { Component } from 'react';
import { ReactTabulator } from 'react-tabulator';
import { connect } from 'react-redux';
import { getFormData } from '../../../../actions/tabActions';
import 'react-tabulator/lib/styles.css';

class Tabulator extends Component {
  rowClick = (e, row) => {
    const { currentTab, getFormData } = this.props;

    getFormData(currentTab.tabKey, row.getData().id, 'edit');
  };

  componentWillReceiveProps () {
    this.forceUpdate();
  }

  render () {
    const { currentTab } = this.props;
    console.log(currentTab.grid.columns);

    return (
      <ReactTabulator
        data={currentTab.grid.data}
        columns={currentTab.grid.columns}
        rowClick={this.rowClick}
        tooltips={true}
        movableRows={false}
        layout={'fitDataFill'}
      />
    );
  }
}

const mapDispatchToProps = dispatch => {
  return {
    getFormData: (tabKey, id, mode) => {
      dispatch(getFormData(tabKey, id, mode));
    }
  };
};

export default connect(null, mapDispatchToProps)(Tabulator);