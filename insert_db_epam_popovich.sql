USE `epam_e_popovich`;

INSERT INTO `email_services` (`id_email_serv`, `address`) VALUES
	(1, '@tut.by');
	
INSERT INTO `accounts` (`id_account`, `login`, `password`, `id_email_serv`) VALUES
	(1, 'epam1.popovich', 'epampopovich', 1),
	(2, 'epam2.popovich', 'epam2popovich', 1);