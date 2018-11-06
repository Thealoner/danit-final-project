import clients from '../../SampleJson/clients.json';

const GridEntities = [
  {
    id: 'clients',
    name: 'Клиенты',
    sampleData: clients,
    columns: [
      { title: 'ID', field: 'id', width: 150 },
      { title: 'Номер карты', field: 'cardId' },
      { title: 'Имя', field: 'firstName' },
      { title: 'Фамилия', field: 'lastName', align: 'left' },
      { title: 'Дата рождения', field: 'birthDate' },
      { title: 'Пол', field: 'gender' },
      { title: 'E-mail', field: 'email' },
      { title: 'Номер телефона', field: 'phoneNumber' }
    ]
  }
];

export default GridEntities;