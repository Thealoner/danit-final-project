import packages from '../../../SampleJson/packages.json';
import services from '../../../SampleJson/services.json';
import serviceCategories from '../../../SampleJson/service_categories.json';
import serviceRules from '../../../SampleJson/service_rules.json';
import contracts from '../../../SampleJson/contracts.json';
import organizations from '../../../SampleJson/organizations.json';

const GridEntities = [
  {
    id: 'packages',
    name: 'Пакеты',
    sampleData: packages,
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
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Название', field: 'title' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'serviceCategories',
    name: 'Категории сервисов',
    sampleData: serviceCategories,
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Описание', field: 'description' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'serviceRules',
    name: 'Условия сервисов',
    sampleData: serviceRules,
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
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Название', field: 'title' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  }
];

export default GridEntities;