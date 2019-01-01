import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Pagination } from 'semantic-ui-react';
import { getEntityByType } from '../../gridEntities';

class GridFooter extends Component {
  state = {
    showEllipsis: true,
    showFirstAndLastNav: true,
    showPreviousAndNextNav: true
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

export default GridFooter;