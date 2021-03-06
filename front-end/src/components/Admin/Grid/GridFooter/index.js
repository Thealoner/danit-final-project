import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Pagination } from 'semantic-ui-react';
import { getEntityByType } from '../../gridEntities';
import { connect } from 'react-redux';
import { getGridData, setFormData } from '../../../../actions/tabActions';
import './index.scss';

class GridFooter extends Component {
  state = {
    showEllipsis: true,
    showFirstAndLastNav: true,
    showPreviousAndNextNav: true
  };

  handlePaginationChange = (e, { activePage }) => {
    const { currentTab, getGridData } = this.props;

    getGridData({
      tabKey: currentTab.tabKey,
      page: activePage,
      size: currentTab.grid.meta.elementsPerPage,
      columns: currentTab.grid.columns,
      filter: currentTab.filter,
      sortColumn: currentTab.grid.sorting.column,
      sortDirection: currentTab.grid.sorting.direction
    });
  };

  render () {
    const { currentTab, setFormData } = this.props;
    const { showEllipsis, showFirstAndLastNav, showPreviousAndNextNav } = this.state;
    const { currentPage, pagesTotal } = currentTab.grid.meta;

    return (
      <div className="grid-footer">
        <button className="grid-footer__add-btn"
          onClick={() => setFormData(currentTab.tabKey, {
            mode: 'add',
            type: 'form',
            data: {},
            meta: {}
          })}>
          <FontAwesomeIcon className="grid-footer__plus-icon" icon="plus" size="1x"/>
          Добавить {getEntityByType(currentTab.tabKey).nameForAddBtn}</button>
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
    );
  }
}

export default connect(null, { getGridData, setFormData })(GridFooter);