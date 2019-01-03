import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import { Loader } from 'semantic-ui-react';
import { getGridData } from '../../../actions/tabActions';
import GridFilter from './GridFilter';
import GridFooter from './GridFooter';
import GridTable from './GridTable';
import 'react-tabulator/lib/styles.css';
import './index.scss';

class Grid extends Component {
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

  render () {
    const { currentTab } = this.props;

    return (
      <Fragment>
        <GridFilter applyFilter={this.applyFilter} clearFilter={this.clearFilter} columns={currentTab.grid.columns}/>
        {currentTab.gridStatus === 'loading'
          ? <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>
          : <GridTable currentTab={currentTab}/>
        }
        <GridFooter currentTab={currentTab}/>
      </Fragment>
    );
  }
}

const mapDispatchToProps = dispatch => {
  return {
    getGridData: (options) => {
      dispatch(getGridData(options));
    }
  };
};

export default connect(null, mapDispatchToProps)(Grid);