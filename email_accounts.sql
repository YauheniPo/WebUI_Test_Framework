-- --------------------------------------------------------
-- Хост:                         localhost
-- Версия сервера:               5.5.23 - MySQL Community Server (GPL)
-- Операционная система:         Win64
-- HeidiSQL Версия:              9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных mailbox
DROP DATABASE IF EXISTS `mailbox`;
CREATE DATABASE IF NOT EXISTS `mailbox` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `mailbox`;

-- Дамп структуры для таблица mailbox.email_accounts
DROP TABLE IF EXISTS `email_accounts`;
CREATE TABLE IF NOT EXISTS `email_accounts` (
  `email_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`email_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы mailbox.email_accounts: ~5 rows (приблизительно)
DELETE FROM `email_accounts`;
/*!40000 ALTER TABLE `email_accounts` DISABLE KEYS */;
INSERT INTO `email_accounts` (`email_id`, `login`, `password`) VALUES
	(1, 'epam1.popovich@tut.by', 'epampopovich'),
	(2, 'epam2.popovich@tut.by', 'epam2popovich'),
	(3, 'epam2.popovich@tut.by', 'epam2popovich'),
	(4, 'epam1.popovich@tut.by', 'epampopovich'),
	(5, 'epam1.popovich@tut.by', 'epampopovich');
/*!40000 ALTER TABLE `email_accounts` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
