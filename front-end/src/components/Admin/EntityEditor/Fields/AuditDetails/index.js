import React, {Component} from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

class AuditDetails extends Component {
  state = {
    isDataClosed: false
  };

  toggleArrow = () => this.setState((prevState) => ({isDataClosed: !prevState.isDataClosed}));
  render () {
    const {data} = this.props;
    const { isDataClosed } = this.state;
    return (

      <div className={`data ${isDataClosed ? 'openedData' : 'closedData'}`}>
        <div className='data-header' onClick={() => this.toggleArrow()}>
          <p>Информация о записи</p>
          <FontAwesomeIcon icon="angle-right" size="1x" onClick={this.toggleEntitiesMenu} className='arrow'/>
        </div>

        <div className='data__details'>
          Создана пользователем: {data.createdBy}<br/>
          Дата создания: {data.creationDate}<br/>
          Последнее редактирование пользователем: {data.lastModifiedBy}<br/>
          Дата последнего редактирования: {data.lastModifiedDate}
        </div>
      </div>

    );
  };
  // }
}

export default AuditDetails;
