DROP DATABASE IF EXISTS `epam_e_popovich`;
CREATE DATABASE IF NOT EXISTS `epam_e_popovich`;
USE `epam_e_popovich`;

DROP TABLE IF EXISTS `email_services`;
CREATE TABLE IF NOT EXISTS `email_services` (
  `id_email_serv` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id_email_serv`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DELETE FROM `email_services`;

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `id_account` tinyint(4) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `id_email_serv` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_account`),
  KEY `FK_accounts_email_services` (`id_email_serv`),
  CONSTRAINT `FK_accounts_email_services` FOREIGN KEY (`id_email_serv`) REFERENCES `email_services` (`id_email_serv`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

DELETE FROM `accounts`;