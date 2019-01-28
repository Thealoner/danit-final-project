import React from 'react';

const AuditDetails = ({data}) => {
  return (
    <div>
      <p>Информация о записи</p>
      Создана пользователем: {data.createdBy}<br />
      Дата создания: {data.creationDate}<br />
      Последнее редактирование пользователем: {data.lastModifiedBy}<br />
      Дата последнего редактирования: {data.lastModifiedDate}
    </div>
  )
};

export default AuditDetails;