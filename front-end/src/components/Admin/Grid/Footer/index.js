import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Pagination } from 'semantic-ui-react';
import { getEntityByType } from '../../gridEntities';
import { connect } from 'react-redux';
import { getGridData } from '../../../../actions/tabActions';

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
      columns: currentTab.grid.columns
    });
  };

  render () {
    const {currentTab, handlePaginationChange} = this.props;
    const { showEllipsis, showFirstAndLastNav, showPreviousAndNextNav } = this.state;
    const { currentPage, pagesTotal } = currentTab.grid.meta;

    return (
      <div className="grid-footer">
        <Link to={'/admin/todo'} className="grid-footer__add-btn">
          <FontAwesomeIcon className="grid-footer__plus-icon" icon="plus" size="1x"/>
          Добавить {getEntityByType(currentTab.tabKey).nameForAddBtn}</Link>
        <Pagination
          activePage={currentPage}
          boundaryRange={1}
          onPageChange={handlePaginationChange}
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

const mapDispatchToProps = dispatch => {
  return {
    getGridData: (options) => {
      dispatch(getGridData(options));
    }
  };
};

export default connect(null, mapDispatchToProps)(GridFooter);