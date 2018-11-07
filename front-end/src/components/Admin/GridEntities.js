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
    apiBaseUrl: '/packages',
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
    apiBaseUrl: '/services',
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
    apiBaseUrl: '/service_categories',
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
    apiBaseUrl: '/service_rules',
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
    apiBaseUrl: '/contracts',
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
    apiBaseUrl: '/organizations',
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
    apiBaseUrl: '/clients',
    recordType: 'simple',
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

export default GridEntities;