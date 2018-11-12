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
      { title: 'Можно купить?', field: 'purchasable' },
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
      { title: 'Категория', field: 'service_category.title' },
      { title: 'service_qty', field: 'service_qty' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Себестоимость', field: 'cost', align: 'left' },
      { title: 'Единица измерения', field: 'unit', align: 'left' },
      { title: 'Кол-во единиц', field: 'units_number', align: 'left' },
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
      { title: 'Название', field: 'title' },
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
      { title: 'Описание', field: 'description' },
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
      { title: 'Имя', field: 'firstName' },
      { title: 'Фамилия', field: 'lastName' },
      { title: 'Пол', field: 'gender' },
      { title: 'Дата Рождения', field: 'birthDate' },
      { title: 'Телефон', field: 'phoneNumber' },
      { title: 'Email', field: 'email' }
    ],
    schema: {
      title: 'Edit Client',
      type: 'object',
      required: 'firstName',
      properties: {
        firstName: {type: 'string', title: 'Имя'},
        lastName: {type: 'string', title: 'Фамилия'},
        gender: {type: 'string', title: 'Пол'},
        birthDate: {type: 'string', title: 'Дата рождения'},
        phoneNumber: {type: 'string', title: 'Телефон'},
        email: {type: 'string', title: 'Email'},
        active: {type: 'boolean', title: 'Активен'},
        contracts: {
          type: 'array',
          title: 'Контракты', 
          items: {
            type: 'object',
            properties: {
              startDate: {type: 'string', title: 'Дата начала'},
              endDate: {type: 'string', title: 'Дата окончания'},
              credit: {type: 'string', title: 'Кредит'}
            }
          }
        }
      }
    },
    uiSchema: {
      firstName: {'ui:autofocus': true},
      email: {'ui:widget': 'email'},
      birthDate: {'ui:widget': 'date'},
      contracts: {
        "ui:options": {
          orderable: false
        }
      }
    }
  },
  {
    id: 'users',
    name: 'Пользователи',
    apiUrl: '/users',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Логин', field: 'username' },
      { title: 'Роли', field: 'roles' }
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