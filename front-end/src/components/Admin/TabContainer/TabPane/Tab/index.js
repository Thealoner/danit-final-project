import React, { Component } from 'react';
import {connect} from 'react-redux';
import { closeTab } from '../../../../../actions/tabActions';
import { toastr } from 'react-redux-toastr';

class Tab extends Component {
  closeCurrentTab = (e, tabKey) => {
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
    const { tabKey, title, activeKey, onSelect, edited } = this.props;
    const tabClass = 'tabs__head' + (tabKey === activeKey ? ' tabs__head--active' : '');

    return (
      <span key={tabKey} className={tabClass} onClick={() => onSelect(tabKey)}
        title={`${title} ${edited ? '(несохранённые данные)' : ''}`}>
        <span className={!edited ? 'tabs__title' : 'tabs__title--edited'}>
          {title}
        </span>
        <span className="tabs__btn-wrapper">
          <button className="tabs__close-btn" onClick={(e) => this.closeCurrentTab(e, tabKey)}/>
        </span>
      </span>
    );
  }
}

export default connect(null, { closeTab })(Tab);