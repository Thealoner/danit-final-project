import clients from '../../SampleJson/clients.json';

const GridEntities = [
  {
    id: 'clients',
    name: 'Клиенты',
    sampleData: clients,
    recordType: 'simple',
    apiUrl: '/clients',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Дата Рождения', field: 'birthDate' },
      { title: 'Имя', field: 'firstName' },
      { title: 'Фамилия', field: 'lastName' },
      { title: 'Пол', field: 'gender' },
      { title: 'Активен', field: 'active' }
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