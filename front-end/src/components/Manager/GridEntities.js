import clients from '../../SampleJson/clients.json';

const GridEntities = [
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
  return GridEntities.find((el) => {
    return el.id === entityType;
  });
}

export { getEntityByType };
export default GridEntities;