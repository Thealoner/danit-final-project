create table clients (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  first_name varchar not null,
  last_name varchar not null,
  gender varchar not null,
  birth_date timestamp not null,
  phone_number varchar not null,
  email varchar not null,
  active bit not null
);

create table user_roles (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  role varchar not null
);

create table users (
  created_by varchar not null,
  creation_date timestamp not null,
  last_modified_by varchar not null,
  last_modified_date timestamp not null,
  id int identity primary key,
  username varchar not null,
  password varchar not null
);

create table users_roles (
  users_id int not null,
  roles_id int not null
);

INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, 'Philippa', 'Lawtie', 'Female', parsedatetime('17-03-1979', 'dd-MM-yyyy'), '958-143-9304', 'plawtie0@amazonaws.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, 'Camille', 'Gilfoy', 'Female', parsedatetime('06-05-1988', 'dd-MM-yyyy'), '285-186-2191', 'cgilfoy1@storify.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, 'Camille', 'Drohane', 'Female', parsedatetime('30-08-2005', 'dd-MM-yyyy'), '708-735-5298', 'mdrohane2@arizona.edu', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 4, 'Trace', 'Mattia', 'Male', parsedatetime('22-03-1993', 'dd-MM-yyyy'), '124-104-7181', 'tmattia3@theatlantic.com', true);


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
