import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import { ReactTabulator } from 'react-tabulator';
import { Loader } from 'semantic-ui-react';
import { getGridData, getFormData } from '../../../actions/tabActions';
import Filter from './Filter';
import GridFooter from './Footer';
import 'react-tabulator/lib/styles.css';
import './index.scss';

class Grid extends Component {
  rowClick = (e, row) => {
    const { currentTab, getFormData } = this.props;

    getFormData(currentTab.tabKey, row.getData().id, 'edit');
  };

  applyFilter = (filterString) => {
    const { currentTab, getGridData } = this.props;

    getGridData({
      tabKey: currentTab.tabKey,
      page: 0,
      size: 30,
      filterString: filterString,
      columns: currentTab.grid.columns
    });
  };

  clearFilter = () => {
    const { currentTab, getGridData } = this.props;

    getGridData({
      tabKey: currentTab.tabKey,
      columns: currentTab.grid.columns
    });
  };

  handlePaginationChange = (e, { activePage }) => {
    const { currentTab, getGridData } = this.props;

    getGridData({
      tabKey: currentTab.tabKey,
      page: activePage,
      size: currentTab.grid.meta.elementsPerPage,
      columns: currentTab.grid.columns
    });
  };

  render () {
    const { currentTab } = this.props;

    console.log(currentTab.grid);

    return (
      <Fragment>
        <Filter applyFilter={this.applyFilter} clearFilter={this.clearFilter} columns={currentTab.grid.columns}/>
        {currentTab.gridStatus === 'loading'
          ? <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>
          : <ReactTabulator
            data={currentTab.grid.data}
            columns={currentTab.grid.columns}
            rowClick={this.rowClick}
            tooltips={true}
            movableRows={false}
            layout={'fitDataFill'}
          />
        }
        <GridFooter handlePaginationChange={this.handlePaginationChange} currentTab={currentTab}/>
      </Fragment>
    );
  }
}

const mapDispatchToProps = dispatch => {
  return {
    getGridData: (options) => {
      dispatch(getGridData(options));
    },
    getFormData: (tabKey, id, mode) => {
      dispatch(getFormData(tabKey, id, mode));
    }
  };
};

export default connect(null, mapDispatchToProps)(Grid);