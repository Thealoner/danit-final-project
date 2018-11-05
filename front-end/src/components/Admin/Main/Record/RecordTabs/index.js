import React, { Component } from 'react';
import './index.scss';

class Tabs extends Component {
  render () {
    return (
      <div className="tabs">
        <div className="tabs__item">Параметры</div>
        <div className="tabs__item">График использования</div>
        <div className="tabs__item">Доп. услуги</div>
        <div className="tabs__item">Лимиты счетов</div>
      </div>
    );
  }
}

export default Tabs;