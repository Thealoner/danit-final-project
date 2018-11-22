INSERT INTO `company` (`id`, `sname`, `name`, `phone`, `phone2`, `email`, `address`, `orgcode`) VALUES (1, 'Test Company', 'Test Company', NULL, NULL, 'org@org.com', 'Ukraine', '000000');

INSERT INTO `employee_category` (`id`, `name`) VALUES (1, '1 катгеория');
INSERT INTO `employee_category` (`id`, `name`) VALUES (2, '2 катгеория');
INSERT INTO `employee_category` (`id`, `name`) VALUES (3, 'Высшая катгеория');

INSERT INTO `discount` (`id`, `name`, `percent`, `date_from`, `date_to`) VALUES (1, '10%', 10, CURRENT_TIMESTAMP, NULL);

INSERT INTO `position` (`id`, `name`, `description`) VALUES (1, 'Boss', 'Boss');
INSERT INTO `position` (`id`, `name`, `description`) VALUES (2, 'Cleaning manager', 'Cleaning manager');

INSERT INTO `department` (`id`, `pid`, `sname`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (1, NULL, 'Energym 1', 'Energym 1', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `sname`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (4, 1, 'Energym 1 1', 'Energym 1 1', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `sname`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (5, 1, 'Energym 1 2', 'Energym 1 2', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `sname`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (2, NULL, 'Energym 2', 'Energym 2', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `sname`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (3, NULL, 'Energym 3', 'Energym 3', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `sname`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (6, 3, 'Energym 3 1', 'Energym 3 1', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);
INSERT INTO `department` (`id`, `pid`, `sname`, `name`, `date_from`, `date_to`, `hier_level`, `companyid`, `sort_position`) VALUES (7, 3, 'Energym 3 2', 'Energym 3 2', CURRENT_TIMESTAMP, NULL, 1, 1, NULL);

INSERT INTO `employee` (`id`, `first_name`, `last_name`, `family_name`, `departmentid`, `job_begin_date`, `dismiss_date`, `positionid`, `email`, `internal_number`, `phone1`, `phone2`, `employee_categoryid`, `photo`, `cardid`, `dbuser`, `discountid`, `gender`) VALUES (1, 'Петр', 'Петрович', 'Петров', 1, CURRENT_TIMESTAMP, NULL, 1, NULL, NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, 0);

INSERT INTO `gym` (`id`, `name`, `description`, `capacity`, `company`) VALUES (1, 'Гимнастический зал', NULL, 20, 1);

INSERT INTO `training_type` (`id`, `name`, `description`, `avaliable4group`) VALUES (1, 'Стретчинг', 'Стретчинг для группы', 1);

INSERT INTO `group_training` (`id`, `employeeid`, `gymid`, `serviceid`, `amount`, `state`, `description`, `training_typeid`) VALUES (1, 1, 1, null, 10, 1, NULL, 1);
