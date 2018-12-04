import pakets from '../../SampleJson/pakets.json';
import services from '../../SampleJson/services.json';
import serviceCategories from '../../SampleJson/service_categories.json';
import serviceRules from '../../SampleJson/service_rules.json';
import contracts from '../../SampleJson/contracts.json';
import organizations from '../../SampleJson/organizations.json';
import clients from '../../SampleJson/clients.json';

const GridEntities = [
  {
    id: 'pakets',
    name: 'Пакеты',
    nameForAddBtn: 'пакет',
    sampleData: pakets,
    apiUrl: '/pakets',
    recordType: 'tabbed',
    recordComponent: 'Package',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Название', field: 'title' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Можно купить?', field: 'purchasable' },
      { title: 'Активен', field: 'active' }
    ],
    schema: {
      type: 'object',
      required: [
        'accessWithoutCardTimesLimit',
        'autoActivateAfterDays',
        'freezeDays',
        'freezeMinTerm',
        'freezeTimes',
        'guestVisits',
        'price',
        'term',
        'title',
        'usersMin',
        'visitTime'
      ],
      properties: {
        accessWithoutCardTimesLimit: {
          title: 'Ограничения на вход без карты, раз',
          type: 'number'
        },
        active: {
          title: 'Активен',
          type: 'boolean'
        },
        autoActivateAfterDays: {
          title: 'Автостарт через, дней',
          type: 'number'
        },
        freezeDays: {
          title: 'Заморозка на общую длительность, дней',
          type: 'number'
        },
        freezeMinTerm: {
          title: 'Минимальная длительность заморозки, дней',
          type: 'number'
        },
        freezeTimes: {
          title: 'Количество возможных заморозок',
          type: 'number'
        },
        guestVisits: {
          title: 'Гостевые посещения',
          type: 'number'
        },
        limitAdditionalServices: {
          title: 'Ораничение по количеству доп. услуг',
          type: 'boolean'
        },
        limitUsageByPaymentPercentage: {
          title: 'Ограничение использования по проценту оплаты',
          type: 'boolean'
        },
        limitVisitTime: {
          title: 'Ограничить времени пребывания',
          type: 'boolean'
        },
        openDateAllowed: {
          title: 'Продажа с открытой датой',
          type: 'boolean'
        },
        price: {
          title: 'Цена',
          type: 'number'
        },
        purchasable: {
          title: 'Доступен для покупки',
          type: 'boolean'
        },
        term: {
          title: 'Срок действия, месяцев',
          type: 'number'
        },
        title: {
          title: 'Название',
          type: 'string'
        },
        usersMin: {
          title: 'Минимальное количество пользователей',
          type: 'number'
        },
        visitTime: {
          title: 'Ограничение времени пребывания, минут',
          type: 'number'
        }
      }
    }
  },
  {
    id: 'services',
    name: 'Сервисы',
    nameForAddBtn: 'сервис',
    sampleData: services,
    apiUrl: '/services',
    recordType: 'tabbed',
    recordComponent: 'Package',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Название', field: 'title' },
      { title: 'Категория', field: 'serviceCategory.title' },
      { title: 'service_qty', field: 'service_qty' },
      { title: 'Цена', field: 'price', align: 'left' },
      { title: 'Себестоимость', field: 'cost', align: 'left' },
      { title: 'Единица измерения', field: 'unit', align: 'left' },
      { title: 'Кол-во единиц', field: 'unitsNumber', align: 'left' },
      { title: 'Активен', field: 'active' }
    ],
    schema: {
      type: 'object',
      properties: {
        title: {
          title: 'Название',
          type: 'string'
        },
        unit: {
          title: 'Единица измерения',
          type: 'string'
        },
        unitsNumber: {
          title: 'Количество единиц',
          type: 'number'
        },
        price: {
          title: 'Цена',
          type: 'number'
        },
        cost: {
          title: 'Себестоимость',
          type: 'number'
        },
        active: {
          title: 'Активен',
          type: 'boolean'
        }
      }
    },
    uiSchema: {}
  },
  {
    id: 'service_categories',
    name: 'Категории сервисов',
    nameForAddBtn: 'категорию сервиса',
    sampleData: serviceCategories,
    apiUrl: '/service_categories',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Название', field: 'title' },
      { title: 'Активен', field: 'active' }
    ],
    schema: {
      type: 'object',
      properties: {
        title: {
          title: 'Название',
          type: 'string'
        }
      }
    },
    uiSchema: {}
  },
  {
    id: 'service_rules',
    name: 'Условия сервисов',
    nameForAddBtn: 'условие сервиса',
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
    nameForAddBtn: 'контракт',
    sampleData: contracts,
    apiUrl: '/contracts',
    recordType: 'tabbed',
    recordComponent: 'Package',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Пакет', field: 'paket.title' },
      { title: 'Клиент', field: 'client.name', align: 'left' },
      { title: 'Активен', field: 'active' }
    ]
  },
  {
    id: 'organizations',
    name: 'Организации',
    nameForAddBtn: 'организацию',
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
    nameForAddBtn: 'клиента',
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
      required: ['firstName'],
      properties: {
        firstName: {type: 'string', title: 'Имя'},
        lastName: {type: 'string', title: 'Фамилия'},
        gender: {type: 'string', title: 'Пол'},
        birthDate: {type: 'string', title: 'Дата рождения'},
        phoneNumber: {type: 'string', title: 'Телефон'},
        email: {type: 'string', title: 'Email'},
        active: {type: 'boolean', title: 'Активен'}
      }
    },
    uiSchema: {
      firstName: {'ui:autofocus': true},
      email: {'ui:widget': 'email'},
      birthDate: {'ui:widget': 'date'}
    }
  },
  {
    id: 'users',
    name: 'Пользователи',
    nameForAddBtn: 'пользователя',
    apiUrl: '/users',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'id' },
      { title: 'Логин', field: 'username' },
      { title: 'Роль', field: 'roles.role' }
    ],
    schema: {
      title: 'Редактирование пользователя',
      type: 'object',
      required: [
        'username'
      ],
      properties: {
        id: {
          title: 'ID',
          type: 'number'
        },
        username: {
          title: 'Логин',
          type: 'string'
        },
        roles: {
          title: '',
          type: 'array',
          items: {
            type: 'object',
            properties: {
              role: {
                title: 'Роль',
                type: 'string',
                enum: ['ADMIN', 'USER'],
                enumNames: ['Admin', 'User']
              }
            }
          }
        }
      }
    },
    uiSchema: {
      roles: {
        items: {
          role: {
            'ui:widget': 'radio'
          }
        },
        'ui:options': {
          addable: false,
          removable: false
        }
      }
    }
  },
  {
    id: 'roles',
    name: 'Роли пользователей',
    nameForAddBtn: 'роль пользователя',
    apiUrl: '/roles',
    recordType: 'simple',
    columns: [
      { title: 'ID', field: 'role' }
    ],
    schema: {
      title: 'Роль Пользователя',
      type: 'object',
      properties: {
        role: {
          title: 'Роль',
          type: 'string',
          enum: ['ADMIN', 'USER'],
          enumNames: ['Admin', 'User']
        }
      }
    },
    uiSchema: {
      role: {
        'ui:widget': 'radio'
      }
    }
  }
];

function getEntityByType (entityType) {
  return GridEntities.find((el) => {
    return el.id === entityType;
  });
}

export { getEntityByType };
export default GridEntities;