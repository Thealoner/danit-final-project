create table employee_category (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(100)
);

create table discount (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(100),
  percent varchar(100),
  date_from timestamp,
  date_to timestamp
);

create table position (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(100),
  description varchar(100)
);

create table department (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  pid int,
  short_name varchar(100),
  name varchar(100),
  date_from timestamp,
  date_to varchar(100),
  hier_level varchar(100),
  companyid int,
  sort_position varchar(100)
);

create table employee (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name varchar(100),
  last_name varchar(100),
  family_name varchar(100),
  departmentid int,
  job_begin_date timestamp,
  dismiss_date timestamp,
  positionid int,
  email varchar(100),
  internal_number int,
  phone1 varchar(100),
  phone2 varchar(100),
  employee_categoryid int,
  photo varchar(100),
  cardid int,
  dbuser varchar(100),
  discountid int,
  gender bit
);

create table gym (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(100),
  description varchar(100),
  capacity int,
  company int
);

create table training_type (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(100),
  description varchar(100),
  avaliable4group bit
);

create table group_training (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  employeeid int,
  gymid int,
  serviceid int,
  amount int,
  state int,
  description varchar(100),
  training_typeid int
);

create table user_roles (
  created_by varchar(100) not null,
  creation_date timestamp not null,
  last_modified_by varchar(100) not null,
  last_modified_date timestamp not null,
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  role varchar(100)
);

create table users (
  created_by varchar(100) not null,
  creation_date timestamp not null,
  last_modified_by varchar(100) not null,
  last_modified_date timestamp not null,
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username varchar(100),
  password varchar(100),
  email varchar (100),
  avatar_image_name varchar(200)
);

create table users_roles (
  users_id int not null,
  roles_id int not null
);

create table clients (
  created_by varchar(100) not null,
  creation_date timestamp not null,
  last_modified_by varchar(100) not null,
  last_modified_date timestamp not null,
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name varchar(100),
  last_name varchar(100),
  gender varchar(100),
  birth_date date,
  phone_number varchar(100),
  email varchar(100),
  active bit
);

create table packages (
  created_by varchar(100) not null,
  creation_date timestamp not null,
  last_modified_by varchar(100) not null,
  last_modified_date timestamp not null,
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title varchar(100),
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
  created_by varchar(100) not null,
  creation_date timestamp not null,
  last_modified_by varchar(100) not null,
  last_modified_date timestamp not null,
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  start_date timestamp ,
  end_date timestamp ,
  credit float ,
  active bit,
  client_id int,
  package_id int
);

create table cards (
  created_by varchar(100) not null,
  creation_date timestamp not null,
  last_modified_by varchar(100) not null,
  last_modified_date timestamp not null,
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  card_code varchar(100) ,
  card_active bit ,
  contract_id int
);

create table service_categories (
  created_by varchar(100) not null,
  creation_date timestamp not null,
  last_modified_by varchar(100) not null,
  last_modified_date timestamp not null,
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title varchar(100) ,
  active bit
);

create table services (
  created_by varchar(100) not null,
  creation_date timestamp not null,
  last_modified_by varchar(100) not null,
  last_modified_date timestamp not null,
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title varchar(100) ,
  price float ,
  cost float ,
  unit varchar(100) ,
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

INSERT INTO `users` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `username`, `password`, `email`, `avatar_image_name`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, 'Admin', '$2a$10$Mn3RH2lJJoOPoBLgmOCBe.es79lxr8EH3uY2N5c4akvCsWYyp5AWO', 'garmashs@gmail.com', '');
INSERT INTO `users` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `username`, `password`, `email`, `avatar_image_name`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, 'Sarah', '$2a$10$fK5Mdi.z.PhDb1Jdx1HAYODtemdU3OuF5xQ03Jh885MS.3qzrQn8G', 'testuser1@gmail.com', '');
INSERT INTO `users` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `username`, `password`, `email`, `avatar_image_name`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, 'Anna', '$2a$10$ShSRNc6kTTRKwemtS1XE6OdXhGMxWLD1IwiP24adcVSDa3Ga7Q9ua', 'testuser2@gmail.com', '');

INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (1, 1);
INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (1, 2);
INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (2, 1);
INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (2, 2);
INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES (3, 2);

INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, 'Philippa', 'Lawtie', 'Female', STR_TO_DATE('17-03-1979', '%d-%m-%Y'), '958-143-9304', 'plawtie0@amazonaws.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, 'Camille', 'Gilfoy', 'Female', STR_TO_DATE('06-05-1988', '%d-%m-%Y'), '285-186-2191', 'cgilfoy1@storify.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, 'Camille', 'Drohane', 'Female', STR_TO_DATE('30-08-2005', '%d-%m-%Y'), '708-735-5298', 'mdrohane2@arizona.edu', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 4, 'Trace', 'Mattia', 'Male', STR_TO_DATE('22-03-1993', '%d-%m-%Y'), '124-104-7181', 'tmattia3@theatlantic.com', true);
