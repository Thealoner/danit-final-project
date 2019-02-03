import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  cancelEditFormData,
  saveFormData,
  addRecord,
  deleteCurrentEntityItem,
  storeTabTmpFormData
} from '../../../actions/tabActions';
import { Loader } from 'semantic-ui-react';
import Paket from './Paket';
import Service from './Service';
import ServiceCategory from './ServiceCategory';
import Contract from './Contract';
import Client from './Client';
import User from './User';
import Role from './Role';
import Card from './Card';
import AuditDetails from './Fields/AuditDetails';
import { toastr } from 'react-redux-toastr';
import { getEntityByType } from '../gridEntities';
import './index.scss';

class EntityEditor extends Component {
  onSubmit = values => {
    const { currentTab, saveFormData, addRecord } = this.props;

    if (currentTab.form.data && currentTab.form.data.id) {
      saveFormData(
        currentTab.tabKey,
        values,
        currentTab.grid.columns,
        'edit',
        currentTab.grid.meta.currentPage,
        currentTab.filter,
        currentTab.grid.sorting
      );
    } else {
      addRecord(currentTab.tabKey, values, currentTab.grid.columns, 'edit', 1);
    }
  }

  onDelete = () => {
    const { deleteCurrentEntityItem, currentTab } = this.props;

    const toastrConfirmOptions = {
      onOk: () => deleteCurrentEntityItem(
        currentTab.tabKey,
        currentTab.form.data,
        currentTab.grid.columns,
        currentTab.grid.meta.currentPage,
        currentTab.filter,
        currentTab.grid.sorting
      ),
      okText: 'Да',
      cancelText: 'Нет'
    };

    toastr.confirm(`Вы уверены, что хотите удалить ${getEntityByType(currentTab.tabKey).nameForAddBtn} ?`, toastrConfirmOptions);
  }

  onCancel = () => {
    const { currentTab, cancelEditFormData } = this.props;
    const edited = currentTab.form && currentTab.form.edited;

    const toastrConfirmOptions = {
      onOk: () => cancelEditFormData(),
      okText: 'Да',
      cancelText: 'Нет'
    };

    edited
      ? toastr.confirm('Изменения не сохранены, продолжить?', toastrConfirmOptions)
      : cancelEditFormData();
  }

  render () {
    const { currentTab, storeTabTmpFormData } = this.props;
    const editMode = !!currentTab.form.data.id;
    let content;

    if (!currentTab.form) {
      return <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>;
    }

    switch (currentTab.tabKey) {
      case 'pakets':
        content = <Paket onSubmit={this.onSubmit} handleChange={storeTabTmpFormData} handleDelete={this.onDelete} handleCancel={this.onCancel} currentTab={currentTab}/>;
        break;
      case 'services':
        content = <Service onSubmit={this.onSubmit} handleChange={storeTabTmpFormData} handleDelete={this.onDelete} handleCancel={this.onCancel} currentTab={currentTab}/>;
        break;
      case 'service_categories':
        content = <ServiceCategory onSubmit={this.onSubmit} handleChange={storeTabTmpFormData} handleDelete={this.onDelete} handleCancel={this.onCancel} currentTab={currentTab}/>;
        break;
      case 'contracts':
        content = <Contract onSubmit={this.onSubmit} handleChange={storeTabTmpFormData} handleDelete={this.onDelete} handleCancel={this.onCancel} currentTab={currentTab}/>;
        break;
      case 'clients':
        content = <Client onSubmit={this.onSubmit} handleChange={storeTabTmpFormData} handleDelete={this.onDelete} handleCancel={this.onCancel} currentTab={currentTab}/>;
        break;
      case 'users':
        content = <User onSubmit={this.onSubmit} handleChange={storeTabTmpFormData} handleDelete={this.onDelete} handleCancel={this.onCancel} currentTab={currentTab}/>;
        break;
      case 'roles':
        content = <Role onSubmit={this.onSubmit} handleChange={storeTabTmpFormData} handleDelete={this.onDelete} handleCancel={this.onCancel} currentTab={currentTab}/>;
        break;
      case 'cards':
        content = <Card onSubmit={this.onSubmit} handleChange={storeTabTmpFormData} handleDelete={this.onDelete} handleCancel={this.onCancel} currentTab={currentTab}/>;
        break;
      default:
        content = <h1>Form component for this entity is not defined.</h1>;
    }

    return (
      <>
        {content}
        {editMode ? <AuditDetails data={currentTab.form.data} /> : ''}
      </>
    );
  }
}

export default connect(null, {
  storeTabTmpFormData,
  saveFormData,
  addRecord,
  deleteCurrentEntityItem,
  cancelEditFormData
})(EntityEditor);