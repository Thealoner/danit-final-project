create table company (
  id int identity primary key,
  short_name varchar,
  name varchar,
  phone varchar,
  phone2 timestamp,
  email varchar,
  address varchar,
  orgcode varchar
);

create table employee_category (
  id int identity primary key,
  name varchar
);

create table discount (
  id int identity primary key,
  name varchar,
  percent varchar,
  date_from timestamp,
  date_to timestamp
);

create table position (
  id int identity primary key,
  name varchar,
  description varchar
);

create table department (
  id int identity primary key,
  pid int,
  short_name varchar,
  name varchar,
  date_from timestamp,
  date_to varchar,
  hier_level varchar,
  companyid int,
  sort_position varchar
);

create table employee (
  id int identity primary key,
  first_name varchar,
  last_name varchar,
  family_name varchar,
  departmentid int,
  job_begin_date timestamp,
  dismiss_date timestamp,
  positionid int,
  email varchar,
  internal_number int,
  phone1 varchar,
  phone2 varchar,
  employee_categoryid int,
  photo varchar,
  cardid int,
  dbuser varchar,
  discountid int,
  gender bit
);

create table gym (
  id int identity primary key,
  name varchar,
  description varchar,
  capacity int,
  company int
);

create table training_type (
  id int identity primary key,
  name varchar,
  description varchar,
  avaliable4group bit
);

create table group_training (
  id int identity primary key,
  employeeid int,
  gymid int,
  serviceid int,
  amount int,
  state int,
  description varchar,
  training_typeid int
);

create table user_roles (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  role varchar
);

create table users (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  username varchar,
  password varchar
);

create table users_roles (
  users_id int not null,
  roles_id int not null
);

create table clients (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  first_name varchar,
  last_name varchar,
  gender varchar,
  birth_date timestamp,
  phone_number varchar,
  email varchar,
  active bit
);

create table packages (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  title varchar,
  term int,
  price float ,
  freeze_times int,
  freeze_days int,
  freeze_min_term int,
  access_without_card_times_limit int,
  auto_activate_after_days int,
  guest_visits int,
  open_date_allowed bit,
  users_min bit,
  limit_visit_time bit,
  visit_time int,
  limit_additional_services bit,
  limit_usage_by_payment_percentage bit,
  active bit,
  purchasable bit
);

create table contracts (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  start_date timestamp ,
  end_date timestamp ,
  credit float ,
  active bit,
  client_id int,
  package_id int
);

create table cards (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  card_code varchar ,
  card_active bit ,
  contract_id int
);

create table service_categories (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  title varchar ,
  active bit
);

create table services (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  title varchar ,
  price float ,
  cost float ,
  unit varchar ,
  units_number int ,
  active bit
);

create table services_service_categories (
  services_id int,
  service_categories_id int
);

INSERT INTO `company` (`id`, `short_name`, `name`, `phone`, `phone2`, `email`, `address`, `orgcode`) VALUES (1, 'Test Company', 'Test Company', NULL, NULL, 'org@org.com', 'Ukraine', '000000');

INSERT INTO `employee_category` (`id`, `name`) VALUES (1, '1 категория');
INSERT INTO `employee_category` (`id`, `name`) VALUES (2, '2 категория');
INSERT INTO `employee_category` (`id`, `name`) VALUES (3, 'Высшая категория');

INSERT INTO `discount` (`id`, `name`, `percent`, `date_from`, `date_to`) VALUES (1, '10 percent', 10, CURRENT_TIMESTAMP, NULL);

INSERT INTO `position` (`id`, `name`, `description`) VALUES (1, 'Boss', 'Boss');
INSERT INTO `position` (`id`, `name`, `description`) VALUES (2, 'Cleaning manager', 'Cleaning manager');

INSERT INTO `department` (`id`, `pid`, `short_name`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (1, NULL, 'Energym 1', 'Energym 1', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `short_name`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (4, 1, 'Energym 1 1', 'Energym 1 1', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `short_name`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (5, 1, 'Energym 1 2', 'Energym 1 2', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `short_name`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (2, NULL, 'Energym 2', 'Energym 2', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `short_name`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (3, NULL, 'Energym 3', 'Energym 3', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `short_name`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (6, 3, 'Energym 3 1', 'Energym 3 1', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `short_name`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (7, 3, 'Energym 3 2', 'Energym 3 2', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);

INSERT INTO `employee` (`id`, `first_name`, `last_name`, `family_name`, `departmentid`, `job_begin_date`, `dismiss_date`, `positionid`, `email`, `internal_number`, `phone1`, `phone2`, `employee_categoryid`, `photo`, `cardid`, `dbuser`, `discountid`, `gender`) VALUES (1, 'Петр', 'Петрович', 'Петров', 1, CURRENT_TIMESTAMP, NULL, 1, NULL, NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, 0);

INSERT INTO `gym` (`id`, `name`, `description`, `capacity`, `company`) VALUES (1, 'Гимнастический зал', NULL, 20, 1);

INSERT INTO `training_type` (`id`, `name`, `description`, `avaliable4group`) VALUES (1, 'Стретчинг', 'Стретчинг для группы', 1);

INSERT INTO `group_training` (`id`, `employeeid`, `gymid`, `serviceid`, `amount`, `state`, `description`, `training_typeid`) VALUES (1, 1, 1, null, 10, 1, NULL, 1);

INSERT INTO `user_roles` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`,`id`, `role`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, 'ADMIN');
INSERT INTO `user_roles` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`,`id`, `role`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, 'USER');
INSERT INTO `user_roles` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`,`id`, `role`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, 'TEST');

INSERT INTO `users` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `username`, `password`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, 'Admin', '$2a$10$Mn3RH2lJJoOPoBLgmOCBe.es79lxr8EH3uY2N5c4akvCsWYyp5AWO');
INSERT INTO `users` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `username`, `password`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, 'Sarah', '$2a$10$fK5Mdi.z.PhDb1Jdx1HAYODtemdU3OuF5xQ03Jh885MS.3qzrQn8G');
INSERT INTO `users` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `username`, `password`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, 'Anna', '$2a$10$ShSRNc6kTTRKwemtS1XE6OdXhGMxWLD1IwiP24adcVSDa3Ga7Q9ua');

INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (1, 1);
INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (1, 2);
INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (2, 1);
INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (2, 2);
INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (3, 2);

INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, 'Philippa', 'Lawtie', 'Female', parsedatetime('17-03-1979', 'dd-MM-yyyy'), '958-143-9304', 'plawtie0@amazonaws.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, 'Camille', 'Gilfoy', 'Female', parsedatetime('06-05-1988', 'dd-MM-yyyy'), '285-186-2191', 'cgilfoy1@storify.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, 'Camille', 'Drohane', 'Female', parsedatetime('30-08-2005', 'dd-MM-yyyy'), '708-735-5298', 'mdrohane2@arizona.edu', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 4, 'Trace', 'Mattia', 'Male', parsedatetime('22-03-1993', 'dd-MM-yyyy'), '124-104-7181', 'tmattia3@theatlantic.com', true);
