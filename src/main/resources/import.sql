INSERT INTO `company` (`id`, `short_name`, `name`, `phone`, `phone2`, `email`, `address`, `orgcode`) VALUES (1, 'Test Company', 'Test Company', NULL, NULL, 'org@org.com', 'Ukraine', '000000');

INSERT INTO `employee_category` (`id`, `name`) VALUES (1, '1 катгеория');
INSERT INTO `employee_category` (`id`, `name`) VALUES (2, '2 катгеория');
INSERT INTO `employee_category` (`id`, `name`) VALUES (3, 'Высшая катгеория');

INSERT INTO `discount` (`id`, `name`, `percent`, `date_from`, `date_to`) VALUES (1, '10%', 10, CURRENT_TIMESTAMP, NULL);

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
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 5, 'Ly', 'Lotherington', 'Male', parsedatetime('17-11-1996', 'dd-MM-yyyy'), '124-104-7181', 'llotherington4@wikipedia.org', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 6, 'Thomasa', 'Feechum', 'Female', parsedatetime('16-07-1973', 'dd-MM-yyyy'), '261-676-6376', 'tfeechum5@posterous.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 7, 'Carina', 'Toffanini', 'Female', parsedatetime('07-12-1976', 'dd-MM-yyyy'), '847-209-2920', 'etoffanini6@twitter.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 8, 'Serhii', 'Belmont', 'Male', parsedatetime('29-12-1991', 'dd-MM-yyyy'), '653-323-4324', 'cbelmont7@answers.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 9, 'Lorne', 'Reis', 'Male', parsedatetime('07-02-2004', 'dd-MM-yyyy'), '846-578-0662', 'cbelmont7@answers.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 10, 'Griselda', 'Winspeare', 'Female', parsedatetime('21-11-1971', 'dd-MM-yyyy'), '155-846-2959', 'gwinspeare9@businessinsider.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 11, 'Broomhilda', 'Winspeare', 'Female', parsedatetime('21-11-1971', 'dd-MM-yyyy'), '342-567-456', 'bwinspeare9@businessinsider.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 12, 'Jisquet', 'Augmenti', 'Female', parsedatetime('4-08-1990', 'dd-MM-yyyy'), '678-244-333', 'notreda9@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 13, 'Vitaliy', 'Golomozuy', 'Male', parsedatetime('09-09-1975', 'dd-MM-yyyy'), '890-244-33-83', 'mailvital@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 14, 'Hannah', 'Montana', 'Female', parsedatetime('06-09-1975', 'dd-MM-yyyy'), '890-2675-33-63', 'hannahm@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 15, 'Yolanda', 'Squatpump', 'Female', parsedatetime('05-11-1995', 'dd-MM-yyyy'), '8567-244-367-80', 'yosquat@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 16, 'Park', 'Jurassic', 'Male', parsedatetime('04-03-1996', 'dd-MM-yyyy'), '890-244-33-83', 'kma@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 17, 'Chichi', 'Diem', 'Male', parsedatetime('01-12-1986', 'dd-MM-yyyy'), '908-244-83', 'chidiem@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 18, 'Iggy', 'Azalea', 'Female', parsedatetime('02-04-1976', 'dd-MM-yyyy'), '675-344-6', 'iggye@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 19, 'Elixyvett', 'Bella', 'Male', parsedatetime('31-12-1998', 'dd-MM-yyyy'), '48-354-900', 'mrelix@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 20, 'Gwendolyn', 'Idalah', 'Female', parsedatetime('27-02-1968', 'dd-MM-yyyy'), '890-198-042', 'gven68@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 21, 'Almond', 'Milk', 'Male', parsedatetime('27-02-1968', 'dd-MM-yyyy'), '43-165-005', 'almonde68@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 22, 'Bonanza', 'Alfonzo', 'Male', parsedatetime('18-05-1977', 'dd-MM-yyyy'), '433-111-000', 'bonanzo68@gmail.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 23, 'Boo', 'Hoo', 'Male', parsedatetime('19-07-1978', 'dd-MM-yyyy'), '673-911-050', 'boohoohoo@yahoo.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 24, 'Byrd', 'Cadence', 'Male', parsedatetime('22-08-1988', 'dd-MM-yyyy'), '098-911-050-67', 'byrd@yahoo.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 25, 'Cinnamon', 'Roll', 'Female', parsedatetime('23-09-1989', 'dd-MM-yyyy'), '096-990-07-07', 'cinnabon@yahoo.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 26, 'Asmund', 'Jukanpoika', 'Male', parsedatetime('24-10-1990', 'dd-MM-yyyy'), '096-988-70-32', 'yukaona@yahoo.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 27, 'Gandalf', 'Grey', 'Male', parsedatetime('25-11-1888', 'dd-MM-yyyy'), '096-123-45-67', 'fellowship@yahoo.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 28, 'Helga', 'Yari', 'Female', parsedatetime('26-12-1998', 'dd-MM-yyyy'), '096-19-44-66', 'hellga@yahoo.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 29, 'Death', 'Star', 'Female', parsedatetime('27-01-1967', 'dd-MM-yyyy'), '07-2345-4-65', 'starwar@yahoo.com', true);
INSERT INTO `clients` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `first_name`, `last_name`, `gender`, `birth_date`, `phone_number`, `email`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 30, 'Hodor', 'Hodor', 'Male', parsedatetime('28-02-1964', 'dd-MM-yyyy'), '07-123-98-54', 'hodorhodor@yahoo.com', true);

INSERT INTO `packages` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `term`, `price`, `freeze_times`, `freeze_days`, `freeze_min_term`, `access_without_card_times_limit`, `auto_activate_after_days`, `guest_visits`, `open_date_allowed`, `users_min`, `limit_visit_time`, `visit_time`, `limit_additional_services`, `limit_usage_by_payment_percentage`, `active`, `purchasable`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, 'Classic', 2, 15000, 2, 20, 20, 2, 10, 1, true, true, true, 100, false, true, true, true);
INSERT INTO `packages` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `term`, `price`, `freeze_times`, `freeze_days`, `freeze_min_term`, `access_without_card_times_limit`, `auto_activate_after_days`, `guest_visits`, `open_date_allowed`, `users_min`, `limit_visit_time`, `visit_time`, `limit_additional_services`, `limit_usage_by_payment_percentage`, `active`, `purchasable`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, 'Classic-Morning', 1, 10000, 2, 20, 20, 2, 10, 1, true, true, true, 100, false, true, true, true);
INSERT INTO `packages` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `term`, `price`, `freeze_times`, `freeze_days`, `freeze_min_term`, `access_without_card_times_limit`, `auto_activate_after_days`, `guest_visits`, `open_date_allowed`, `users_min`, `limit_visit_time`, `visit_time`, `limit_additional_services`, `limit_usage_by_payment_percentage`, `active`, `purchasable`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, 'Classic-Platinum', 5, 20000, 2, 20, 20, 2, 10, 1, true, true, true, 100, false, true, true, true);

INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, parsedatetime('01-10-2018', 'dd-MM-yyyy'), parsedatetime('01-10-2019', 'dd-MM-yyyy'), 10000, true , 1, 2);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, parsedatetime('30-11-2018', 'dd-MM-yyyy'), parsedatetime('30-11-2019', 'dd-MM-yyyy'), 10000, true , 1, 3);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, parsedatetime('03-08-2018', 'dd-MM-yyyy'), parsedatetime('03-08-2019', 'dd-MM-yyyy'), 10000, true , 2, 2);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 4, parsedatetime('05-07-2018', 'dd-MM-yyyy'), parsedatetime('05-07-2019', 'dd-MM-yyyy'), 10000, true , 3, 2);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 5, parsedatetime('13-06-2018', 'dd-MM-yyyy'), parsedatetime('13-06-2019', 'dd-MM-yyyy'), 10000, true , 4, 2);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 6, parsedatetime('21-05-2018', 'dd-MM-yyyy'), parsedatetime('21-05-2019', 'dd-MM-yyyy'), 10000, true , 5, 2);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 7, parsedatetime('19-05-2018', 'dd-MM-yyyy'), parsedatetime('19-05-2019', 'dd-MM-yyyy'), 10000, true , 6, 2);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 8, parsedatetime('05-05-2018', 'dd-MM-yyyy'), parsedatetime('05-05-2019', 'dd-MM-yyyy'), 10000, true , 7, 2);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 9, parsedatetime('29-04-2018', 'dd-MM-yyyy'), parsedatetime('29-04-2019', 'dd-MM-yyyy'), 10000, true , 8, 2);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 10, parsedatetime('03-04-2018', 'dd-MM-yyyy'), parsedatetime('03-04-2019', 'dd-MM-yyyy'), 10000, true , 9, 2);
INSERT INTO `contracts` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `start_date`, `end_date`, `credit`, `active`, `client_id`, `package_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 11, parsedatetime('01-03-2018', 'dd-MM-yyyy'), parsedatetime('01-03-2019', 'dd-MM-yyyy'), 10000, true , 9, 3);

INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, '999666555123', true, 1);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, '999777856412', true, 2);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, '999888775784', true, 3);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 4, '999888845769', true, 4);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 5, '999889632542', true, 5);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 6, '999123548654', true, 6);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 7, '999447856951', true, 7);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 8, '999667769814', true, 8);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 9, '999660985692', true, 9);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 10, '999666990557', true, 10);
INSERT INTO `cards` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `card_code`, `card_active`, `contract_id`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 11, '999122895338', true, 11);

INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, 'SPA', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, 'YOGA', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, 'BAR', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 4, 'RECEPTION', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 5, 'MEDICAL CABINET', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 6, 'SOLARIUM', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 7, 'FIGHT CLUB', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 8, 'SWIMMING POOL', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 9, 'GROUP TRAINING STUDIO', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 10, 'WELLNESS STUDIO', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 11, 'CYCLE STUDIO', true);
INSERT INTO `service_categories` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 12, 'GYM', true);

INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 1, 'Total Body Workout', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 2, 'Yoga', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 3, 'Fly Yoga', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 4, 'HIIT', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 5, 'Medical examination', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 6, 'Box', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 7, 'Karate', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 8, 'Aqua Aerobic', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 9, 'Wellness consultation', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 10, 'Meal plan', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 11, 'Pilates', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 12, 'Zumba', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 13, 'Cycle', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 14, 'Tanning', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 15, 'Personal training', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 16, 'TABATA', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 17, 'Massage', 200, 200, 'min', 55, true);
INSERT INTO `services` (`created_by`, `creation_date`, `last_modified_by`, `last_modified_date`, `id`, `title`, `price`, `cost`, `unit`, `units_number`, `active`) VALUES ('SuperUser', CURRENT_TIMESTAMP, 'SuperUser', CURRENT_TIMESTAMP, 18, 'Skin treatment', 200, 200, 'min', 55, true);

INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (17, 1);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (18, 1);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (2, 2);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (3, 2);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (1, 4);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (2, 4);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (3, 4);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (5, 5);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (14, 6);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (6, 7);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (7, 7);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (8, 8);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (4, 9);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (11, 9);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (12, 9);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (16, 9);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (1, 9);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (9, 10);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (17, 10);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (13, 11);
INSERT INTO `services_service_categories` (`services_id`, `service_categories_id`) VALUES (15, 12);