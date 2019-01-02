import React, { Component } from 'react';
import {connect} from 'react-redux';
import {closeTab} from '../../../../../actions/tabActions';
import { NavLink } from 'react-router-dom';
import { toastr } from 'react-redux-toastr';

class Tab extends Component {
  closeTabHandler = (e, tabKey) => {
    const {edited, closeTab} = this.props;

    e.preventDefault();
    e.stopPropagation();
    const toastrConfirmOptions = {
      onOk: () => closeTab(tabKey),
      okText: 'Да',
      cancelText: 'Нет'
    };

    if (edited) {
      toastr.confirm('Изменения не сохранены, продолжить?', toastrConfirmOptions);
    } else {
      closeTab(tabKey);
    }
  };

  render () {
    const {tabKey, title, activeKey, onSelect, edited} = this.props;
    const tabClass = 'tabs__head' + (tabKey === activeKey ? ' tabs__head--active' : '');

    return (
      <NavLink to={'/admin/' + tabKey} key={tabKey} className={tabClass} onClick={() => onSelect(tabKey)}
        title={`${title} ${edited ? '(несохранённые данные)' : ''}`}>
        <span className={!edited ? 'tabs__title' : 'tabs__title--edited'}>
          {title}
        </span>
        <span className="tabs__btn-wrapper">
          <button className="tabs__close-btn" onClick={(e) => this.closeTabHandler(e, tabKey)}/>
        </span>
      </NavLink>
    );
  }
}

const mapDispatchToProps = (dispatch) => ({
  closeTab: tabKey => {
    dispatch(closeTab(tabKey));
  }
});

export default connect(null, mapDispatchToProps)(Tab);