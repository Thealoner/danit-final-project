import packages from './SampleJson/packages.json';
import services from './SampleJson/services.json';
import serviceCategories from './SampleJson/service_categories.json';
import serviceRules from './SampleJson/service_rules.json';
import contracts from './SampleJson/contracts.json';
import organizations from './SampleJson/organizations.json';

const GridEntities = [
  {
    id: 'packages',
    name: 'Пакеты',
    sampleData: packages,
    columns: [
      { title: 'ID', field: 'id', width: 150 },
      { title: 'Title', field: 'title' },
      { title: 'Price', field: 'price', align: 'left' },
      { title: 'Active', field: 'active' }
    ]
  },
  {
    id: 'services',
    name: 'Сервисы',
    sampleData: services,
    columns: [
      { title: 'ID', field: 'id', width: 150 },
      { title: 'Title', field: 'title' },
      { title: 'Price', field: 'price', align: 'left' },
      { title: 'Active', field: 'active' }
    ]
  },
  {
    id: 'serviceCategories',
    name: 'Категории сервисов',
    sampleData: serviceCategories,
    columns: [
      { title: 'ID', field: 'id', width: 150 },
      { title: 'Description', field: 'description' },
      { title: 'Price', field: 'price', align: 'left' },
      { title: 'Active', field: 'active' }
    ]
  },
  {
    id: 'serviceRules',
    name: 'Условия сервисов',
    sampleData: serviceRules,
    columns: [
      { title: 'ID', field: 'id', width: 150 },
      { title: 'Title', field: 'title' },
      { title: 'Price', field: 'price', align: 'left' },
      { title: 'Active', field: 'active' }
    ]
  },
  {
    id: 'contracts',
    name: 'Контракты',
    sampleData: contracts,
    columns: [
      { title: 'ID', field: 'id', width: 150 },
      { title: 'Gender', field: 'gender' },
      { title: 'Credit', field: 'credit', align: 'left' },
      { title: 'Active', field: 'active' }
    ]
  },
  {
    id: 'organizations',
    name: 'Организации',
    sampleData: organizations,
    columns: [
      { title: 'ID', field: 'id', width: 150 },
      { title: 'Title', field: 'title' },
      { title: 'Price', field: 'price', align: 'left' },
      { title: 'Active', field: 'active' }
    ]
  }
];

export default GridEntities;