import clients from '../../SampleJson/clients.json';

const gridEntities = [
  {
    id: 'clients',
    name: 'Клиенты',
    sampleData: clients,
    apiUrl: '/clients',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Имя', field: 'firstName' },
      { title: 'Фамилия', field: 'lastName' },
      { title: 'Пол', field: 'gender' },
      { title: 'Дата Рождения', field: 'birthDate' },
      { title: 'Телефон', field: 'phoneNumber' },
      { title: 'Email', field: 'email' }
    ]
  }
];

function getEntityByType (entityType) {
  return gridEntities.find((el) => {
    return el.id === entityType;
  });
}

export { getEntityByType };
export default gridEntities;