import packages from '../../SampleJson/packages.json';
import services from '../../SampleJson/services.json';
import serviceCategories from '../../SampleJson/service_categories.json';
import serviceRules from '../../SampleJson/service_rules.json';
import contracts from '../../SampleJson/contracts.json';
import organizations from '../../SampleJson/organizations.json';
import clients from '../../SampleJson/clients.json';

const GridEntities = [
  {
    id: 'packages',
    name: 'Пакеты',
    sampleData: packages,
    apiUrl: '/packages',
    recordType: 'tabbed',
    recordComponent: 'Package',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Название', field: 'title' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'services',
    name: 'Сервисы',
    sampleData: services,
    apiUrl: '/services',
    recordType: 'tabbed',
    recordComponent: 'Package',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Название', field: 'title' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'service_categories',
    name: 'Категории сервисов',
    sampleData: serviceCategories,
    apiUrl: '/service_categories',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Описание', field: 'description' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'service_rules',
    name: 'Условия сервисов',
    sampleData: serviceRules,
    apiUrl: '/service_rules',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Название', field: 'title' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'contracts',
    name: 'Контракты',
    sampleData: contracts,
    apiUrl: '/contracts',
    recordType: 'tabbed',
    recordComponent: 'Package',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Пакет', field: 'package.title' },
      { title: 'Клиент', field: 'client.name', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'organizations',
    name: 'Организации',
    sampleData: organizations,
    apiUrl: '/organizations',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Название', field: 'title' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'clients',
    name: 'Клиенты',
    sampleData: clients,
    apiUrl: '/clients',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Дата Рождения', field: 'birthDate' },
      { title: 'Имя', field: 'firstName' },
      { title: 'Фамилия', field: 'lastName' },
      { title: 'Пол', field: 'gender' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'users',
    name: 'Пользователи',
    apiUrl: '/users',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Дата Рождения', field: 'username' },
      { title: 'Имя', field: 'roles' }
    ]
  },
  {
    id: 'roles',
    name: 'Роли Пользователей',
    apiUrl: '/roles',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'role' }
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