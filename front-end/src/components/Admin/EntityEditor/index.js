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
import './index.scss';

class EntityEditor extends Component {
  onSubmit = values => {
    const { currentTab, saveData, addRecord } = this.props;

    if (currentTab.form.data && currentTab.form.data.id) {
      saveData(currentTab.tabKey, values, currentTab.grid.columns, 'edit', 1);
    } else {
      addRecord(currentTab.tabKey, values, currentTab.grid.columns, 'edit', 1);
    }
  }

  onDelete = () => {
    let { deleteData, currentTab } = this.props;
    deleteData(currentTab.tabKey, currentTab.form.data, currentTab.grid.columns, 1);
  }
  
  render () {
    const { currentTab, storeTmpFormData, cancelData } = this.props;
    const editMode = !!currentTab.form.data.id;
    const { editCollision } = currentTab.form;
    let content;

    if (!currentTab.form) {
      return <div className="tabs__loader-wrapper"><Loader active inline='centered' size='big'/></div>;
    }

    switch (currentTab.tabKey) {
      case 'pakets':
        content = <Paket onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
        break;
      case 'services':
        content = <Service onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
        break;
      case 'service_categories':
        content = <ServiceCategory onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
        break;
      case 'contracts':
        content = <Contract onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
        break;
      case 'clients':
        content = <Client onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
        break;
      case 'users':
        content = <User onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
        break;
      case 'roles':
        content = <Role onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
        break;
      case 'cards':
        content = <Card onSubmit={this.onSubmit} handleChange={storeTmpFormData} handleDelete={this.onDelete} handleCancel={cancelData} currentTab={currentTab}/>;
        break;
      default:
        content = <h1>Form component for this entity is not defined.</h1>;
    }

    return (
      <>
        <div>{(editCollision && editCollision.collisionRecord) ? 'Пользователь ' + editCollision.userName + ' редактирует эту запись.' : ''}</div>
        {content}
        {editMode ? <AuditDetails data={currentTab.form.data} /> : ''}
      </>
    );
  }
};

const mapDispatchToProps = dispatch => {
  return {
    storeTmpFormData: (payload) => {
      dispatch(storeTabTmpFormData(payload));
    },
    saveData: (tabKey, formData, columns, mode, page) => {
      dispatch(saveFormData(tabKey, formData, columns, mode, page));
    },
    addRecord: (tabKey, formData, columns, mode, page) => {
      dispatch(addRecord(tabKey, formData, columns, mode, page));
    },
    deleteData: (tabKey, formData, columns, page) => {
      dispatch(deleteCurrentEntityItem(tabKey, formData, columns, page));
    },
    cancelData: () => {
      dispatch(cancelEditFormData());
    }
  };
};

export default connect(null, mapDispatchToProps)(EntityEditor);