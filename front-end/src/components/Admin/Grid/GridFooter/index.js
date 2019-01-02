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
      columns: currentTab.grid.columns
    });
  };

  render () {
    const { currentTab, setFormData } = this.props;
    const { showEllipsis, showFirstAndLastNav, showPreviousAndNextNav } = this.state;
    const { currentPage, pagesTotal, totalElements } = currentTab.grid.meta;

    return (
      <div className="grid-footer">
        <button className="grid-footer__add-btn"
          onClick={() => setFormData(currentTab.tabKey, {
            mode: 'add',
            id: totalElements + 1,
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

const mapDispatchToProps = dispatch => {
  return {
    getGridData: (options) => {
      dispatch(getGridData(options));
    },
    setFormData: (tabKey, payload) => {
      dispatch(setFormData(tabKey, payload));
    }
  };
};

export default connect(null, mapDispatchToProps)(GridFooter);