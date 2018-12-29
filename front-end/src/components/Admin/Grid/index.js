import React, { Component, Fragment } from 'react';
import Tabulator from 'tabulator-tables';
import './index.scss';
import { getEntityByType } from '../gridEntities';
import { Link } from 'react-router-dom';
import Filter from './Filter';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Pagination } from 'semantic-ui-react';
import { getGridData, setTabFormData } from '../../../actions/tabActions';
import { connect } from 'react-redux';

class Grid extends Component {
  state = {
    showEllipsis: true,
    showFirstAndLastNav: true,
    showPreviousAndNextNav: true
  };

  tabulator = null;
  tabulatorTable = React.createRef();

  rowClick = (e, row) => {
    const { currentTab, setTabFormData } = this.props;
    setTabFormData(currentTab.tabKey, {
      id: row.getData().id,
      type: 'form'
    });
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
    const { currentPage, pagesTotal } = currentTab.grid.meta;
    const { showEllipsis, showFirstAndLastNav, showPreviousAndNextNav } = this.state;

    return (
      <Fragment>
        <Filter applyFilter={this.applyFilter} clearFilter={this.clearFilter} columns={currentTab.grid.columns}/>
        <div ref={el => (this.tabulatorTable = el)} className="tabulator"/>
        <div className="grid-footer">
          <Link to={'/admin/todo'} className="grid-footer__add-btn">
            <FontAwesomeIcon className="grid-footer__plus-icon" icon="plus" size="1x"/>
            Добавить {getEntityByType(currentTab.tabKey).nameForAddBtn}</Link>
          <Pagination
            activePage={currentPage}
            boundaryRange={1}
            onPageChange={this.handlePaginationChange}
            siblingRange={1}
            size='mini'
            totalPages={pagesTotal}
            ellipsisItem={showEllipsis ? undefined : null}
            firstItem={showFirstAndLastNav ? undefined : null}
            lastItem={showFirstAndLastNav ? undefined : null}
            prevItem={showPreviousAndNextNav ? undefined : null}
            nextItem={showPreviousAndNextNav ? undefined : null}
          />
        </div>
      </Fragment>
    );
  }

  componentDidMount () {
    const { currentTab } = this.props;

    this.tabulator = new Tabulator(this.tabulatorTable, {
      data: currentTab.grid.data,
      columns: currentTab.grid.columns,
      rowClick: this.rowClick,
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
    setTabFormData: (tabKey, payload) => {
      dispatch(setTabFormData(tabKey, payload));
    },
    getGridData: (entity) => {
      dispatch(getGridData(entity));
    }
  };
};

export default connect(null, mapDispatchToProps)(Grid);