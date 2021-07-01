/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 10.4.11-MariaDB : Database - grami
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`grami` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `grami`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKda8tuywtf0gb6sedwk7la1pgi` (`user_id`),
  CONSTRAINT `FKda8tuywtf0gb6sedwk7la1pgi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `address` */

insert  into `address`(`id`,`country`,`city`,`street`,`zip_code`,`user_id`) values 
(2,'Croatia','Zagreb','Andrije Hebranga 1','10000',6),
(3,'Serbia','Belgrade','Milutina Milankovića 25/5','11000',5),
(5,'Serbia','Belgrade','Rada Končara 19/2','11080',9),
(6,'Serbia','Kragujevac','Kneza Miloša 117a','34000',10),
(7,'Serbia','Beograd','Rada Končara 19/2','11080',11),
(8,'Srbija','Belgrade','Zagorska 38/19','11080',1);

/*Table structure for table `artist` */

DROP TABLE IF EXISTS `artist`;

CREATE TABLE `artist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stage_name` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;

/*Data for the table `artist` */

insert  into `artist`(`id`,`stage_name`) values 
(1,'Justin Bieber'),
(2,'Katy Perry'),
(3,'Rihanna'),
(4,'Lady Gaga'),
(5,'Metallica'),
(6,'Sigrid'),
(7,'30 Seconds To Mars'),
(8,'The Rolling Stones'),
(9,'Jay-Z'),
(10,'Lil Wayne'),
(11,'Bee Gees'),
(12,'The 1975'),
(13,'Machine Gun Kelly'),
(14,'Eminem'),
(15,'Black Eyed Peas'),
(16,'Madvillain (MF Doom & Madlib)'),
(17,'MF Doom'),
(18,'MA Doom (Masta Ace & MF Doom)'),
(19,'Dangerdoom (Dangermouse & MF Doom)'),
(20,'JJ DOOM (Jneiro Jarel & MF Doom)'),
(21,'Miley Cyrus'),
(22,'Nelly'),
(23,'Alicia Keys'),
(24,'The Black Keys'),
(25,'Johnny Cash'),
(26,'Mac DeMarco'),
(27,'The Weeknd'),
(28,'DMX'),
(29,'Dua Lipa'),
(30,'Drake');

/*Table structure for table `card_info` */

DROP TABLE IF EXISTS `card_info`;

CREATE TABLE `card_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_type` varchar(255) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `cvc` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsdwndjge06c782w4x7owcdru7` (`user_id`),
  CONSTRAINT `FKsdwndjge06c782w4x7owcdru7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

/*Data for the table `card_info` */

insert  into `card_info`(`id`,`card_type`,`holder_name`,`card_number`,`expiration_month`,`expiration_year`,`cvc`,`user_id`) values 
(1,'mastercard','Mike Myers','252365512018',4,2022,232,6),
(8,'visa','Mile Mikic','535547412155',8,2023,327,5),
(10,'mastercard','Katarina Bešlić','545961125382',8,2023,360,1);

/*Table structure for table `cart_item` */

DROP TABLE IF EXISTS `cart_item`;

CREATE TABLE `cart_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) DEFAULT NULL,
  `vinyl_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjnaj4sjyqjkr4ivemf9gb25w` (`user_id`),
  KEY `FKl9go12626glx5ehe0eo8qo5qp` (`vinyl_id`),
  KEY `FKen9v41ihsnhcr0i7ivsd7i84c` (`order_id`),
  CONSTRAINT `FKen9v41ihsnhcr0i7ivsd7i84c` FOREIGN KEY (`order_id`) REFERENCES `user_order` (`id`),
  CONSTRAINT `FKjnaj4sjyqjkr4ivemf9gb25w` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKl9go12626glx5ehe0eo8qo5qp` FOREIGN KEY (`vinyl_id`) REFERENCES `vinyl` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8mb4;

/*Data for the table `cart_item` */

insert  into `cart_item`(`id`,`quantity`,`vinyl_id`,`user_id`,`order_id`) values 
(9,1,15,6,15),
(11,1,15,6,17),
(67,1,17,5,26),
(68,1,6,5,27),
(79,1,25,6,31),
(80,1,24,6,33),
(81,1,22,5,32),
(82,1,15,5,34),
(85,1,6,9,36),
(86,1,16,5,37),
(87,1,8,5,38),
(88,1,4,10,39),
(89,1,7,10,39),
(91,1,3,9,41),
(92,3,10,11,42),
(94,1,2,9,NULL),
(98,1,3,5,43),
(112,1,30,1,44),
(113,1,5,1,44),
(114,1,18,1,45),
(115,1,16,1,45),
(117,1,16,1,48),
(118,1,18,1,48),
(119,1,18,6,46),
(120,1,16,6,46),
(124,1,3,5,NULL),
(125,5,5,5,NULL),
(136,1,2,6,NULL),
(137,1,3,6,NULL),
(138,1,4,6,NULL),
(144,2,2,1,49);

/*Table structure for table `format` */

DROP TABLE IF EXISTS `format`;

CREATE TABLE `format` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `format` */

insert  into `format`(`id`,`name`) values 
(1,'Vinyl'),
(2,'2LP'),
(3,'3LP'),
(4,'LP Box Set');

/*Table structure for table `genre` */

DROP TABLE IF EXISTS `genre`;

CREATE TABLE `genre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;

/*Data for the table `genre` */

insert  into `genre`(`id`,`name`) values 
(1,'Hip Hop'),
(2,'Rap'),
(3,'Pop'),
(4,'RnB'),
(5,'Rock'),
(6,'Electronic'),
(7,'Heavy Metal'),
(8,'Alternative Metal'),
(9,'Trash Metal'),
(10,'Alternative'),
(11,'Hard Rock'),
(12,'Disco'),
(13,'Funk'),
(14,'Soul'),
(19,'Jazz'),
(20,'Blues'),
(21,'Garage'),
(22,'Symphonic Metal'),
(23,'Folk'),
(24,'Country'),
(25,'Indie Rock');

/*Table structure for table `record_label` */

DROP TABLE IF EXISTS `record_label`;

CREATE TABLE `record_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL,
  `year` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4;

/*Data for the table `record_label` */

insert  into `record_label`(`id`,`name`,`year`) values 
(1,'Roc-A-Fella Records','1997'),
(2,'Universal International Music B.V. ‎','2020'),
(3,'Universal Music','2020'),
(4,'Def Jam Recordings','2007'),
(5,'Interscope Records','2020'),
(6,'Elektra Records','1987'),
(7,'Island Records','2019'),
(8,'Interscope Records','2018'),
(9,'Virgin Records','1994'),
(10,'Cash Money Records','2004'),
(11,'RSO Records','1976'),
(12,'Dirty Hit','2020'),
(13,'Bad Boy Entertainment ','2017'),
(14,'Aftermath Entertainment','2018'),
(15,'Interscope Records','2000'),
(16,'Cash Money Records','2005'),
(17,'Stones Throw','2004'),
(18,'Nature Sounds','2005'),
(19,'Fat Beats Records','2012'),
(20,'Metalface Records','2005'),
(21,'Lex Records','2014'),
(22,'Universal Records','2000'),
(23,'RCA Records','2020'),
(24,'Nonesuch Records','2010'),
(25,'Blackened','2020'),
(26,'Mercury Nashville','2020'),
(27,'Def Jam Recordings','2021'),
(28,'Mac\'s Record Label','2019'),
(29,'Republic Records','2020'),
(30,'Ear Music Classic','2003'),
(31,'Warner Records','2020'),
(32,'Universal Republic','2011');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `role` */

insert  into `role`(`id`,`name`) values 
(1,'ROLE_ADMIN'),
(2,'ROLE_CUSTOMER');

/*Table structure for table `song` */

DROP TABLE IF EXISTS `song`;

CREATE TABLE `song` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `song_name` varchar(150) DEFAULT NULL,
  `duration` varchar(10) DEFAULT NULL,
  `vinyl_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vinyl_id` (`vinyl_id`),
  CONSTRAINT `song_ibfk_1` FOREIGN KEY (`vinyl_id`) REFERENCES `vinyl` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=651 DEFAULT CHARSET=utf8mb4;

/*Data for the table `song` */

insert  into `song`(`id`,`song_name`,`duration`,`vinyl_id`) values 
(1,'All Around Me','2:17',1),
(2,'Habitual','2:48',1),
(3,'Come Around Me','3:21',1),
(4,'Intentions (Feat. Quavo)','3:33',1),
(5,'Yummy','3:29',1),
(6,'Available','3:15',1),
(7,'Forever (Feat. Post Malone & Clever)','3:40',1),
(8,'Running Over (Feat. Lil Dicky)','3:00',1),
(9,'Take It Out On Me','2:58',1),
(10,'Second Emotion','3:23',1),
(11,'Get Me (Feat. Kehlani)','3:05',1),
(12,'E.T.A.','2:57',1),
(13,'Confirmation','2:51',1),
(16,'Never Really Over','3:43',2),
(17,'Cry About It Later','3:09',2),
(18,'Teary Eyes','3:02',2),
(19,'Daisies','2:54',2),
(20,'Resilient','3:07',2),
(21,'Not The End Of The World','2:58',2),
(22,'Smile','2:46',2),
(23,'Champagne Problems','3:16',2),
(24,'Tucked','3:07',2),
(25,'Harley\'s In Hawaii','3:05',2),
(26,'Only Love','3:18',2),
(27,'What Makes A Woman','2:11',2),
(28,'Chromatica','1:00',3),
(29,'Alice','2:57',3),
(30,'Stupid Love','3:13',3),
(31,'Rain On Me (Feat. Ariana Grande)','3:02',3),
(32,'Free Woman','3:11',3),
(33,'Fun Tonight','2:53',3),
(34,'Chromatica II','0:41',3),
(35,'911','2:52',3),
(36,'Plastic Doll','3:41',3),
(37,'Sour Candy (Feat. BLACKPINK)','2:37',3),
(38,'Enigma','2:59',3),
(39,'Replay','3:06',3),
(40,'Chromatica III','0:27',3),
(41,'Sign From Above (Feat. Elton John)','4:04',3),
(42,'1000 Doves','3:35',3),
(43,'Babylon','2:41',3),
(44,'Umbrella (Feat. Jay Z)','4:35',4),
(45,'Push Up On Me','3:15',4),
(46,'Don\'t Stop The Music','4:27',4),
(47,'Breakin\' Dishes','3:20',4),
(48,'Shut Up And Drive','3:33',4),
(49,'Hate That I Love You (Feat. Ne-Yo)','3:39',4),
(50,'Say It','4:10',4),
(51,'Sell Me Candy','2:45',4),
(52,'Lemme Get That','3:41',4),
(53,'Rehab','4:45',4),
(54,'Question Existing','4:08',4),
(55,'Good Girl Gone Bad','3:35',4),
(56,'Helpless','6:39',5),
(57,'The Small Hours','6:43',5),
(58,'The Wait','4:55',5),
(59,'Crash Curse In Brain Surgery','3:10',5),
(60,'Last Caress/Green Hell','3:30',5),
(61,'Sucker Punch','3:14',6),
(62,'Mine Right Now','3:23',6),
(63,'Basic','3:37',6),
(64,'Strangers','3:53',6),
(65,'Don\'t Feel Like Crying','2:37',6),
(66,'Level Up','2:17',6),
(67,'Sight Of You','3:57',6),
(68,'In Vain','4:07',6),
(69,'Don\'t Kill My Vibe','3:04',6),
(70,'Business Dinners','2:48',6),
(71,'Never Mine','3:38',6),
(72,'Dynamite','3:51',6),
(73,'Walk On Water','3:05',7),
(74,'Dangerous Night','3:19',7),
(75,'Rescue Me','3:37',7),
(76,'One Track Mind (Feat. A$AP Rocky)','3:37',7),
(77,'Monolith','1:38',7),
(78,'Love Is Madness (Feat. Halsey)','3:54',7),
(79,'Great Wide Open','4:49',7),
(80,'Hall To The Victor','3:22',7),
(81,'Dawn Will Rise','3:57',7),
(82,'Remedy','3:57',7),
(83,'Live Like A Dream','4:06',7),
(84,'Rider','2:58',7),
(85,'Love Is Strong','3:46',8),
(86,'You Got Me Rocking','3:34',8),
(87,'Sparks Will Fly','3:14',8),
(88,'The Worst','2:24',8),
(89,'New Faces','2:50',8),
(90,'Moon Is Up','3:41',8),
(91,'Out Of Tears','5:25',8),
(92,'I Go Wild','4:19',8),
(93,'Brand New Car','4:13',8),
(94,'Sweethearts Together','4:46',8),
(95,'Suck On The Jugular','4:26',8),
(96,'Blinded By Rainbows','4:33',8),
(97,'Baby Break It Down','4:07',8),
(98,'Thru And Thru','5:59',8),
(99,'Mean Disposition','4:09',8),
(100,'Intro / A Million And One Questions / Rhyme No More','3:21',9),
(101,'The City Is Mine','4:02',9),
(102,'I Know What Girls Like','4:50',9),
(103,'Imaginary Player','3:57',9),
(104,'Streets Is Watching','3:58',9),
(105,'Friend Or Foe \'98','2:09',9),
(106,'Lucky Me','5:00',9),
(107,'(Always Be My) Sunshine','4:43',9),
(108,'Who You Wit II','4:29',9),
(109,'Face Off','3:31',9),
(110,'Real Niggaz','5:07',9),
(111,'Rap Game/Crack Game','2:40',9),
(112,'Where I\'m From','4:26',9),
(113,'You Must Love Me','5:47',9),
(114,'Walk In','2:57',10),
(115,'This Is The Carter (Feat. Mannie Fresh)','4:36',10),
(116,'BM J.R.','4:59',10),
(117,'On The Block #1','0:19',10),
(118,'I Miss My Dawgs (Feat. Reel)','4:35',10),
(119,'We Don\'t (Feat. Baby AKA The Birdman)','4:10',10),
(120,'On My Own (Feat. Reel)','4:28',10),
(121,'Tha Heat','4:35',10),
(122,'Cash Money Millionaires','4:42',10),
(123,'Inside','1:27',10),
(124,'Bring It Back (Feat. Mannie Fresh)','4:21',10),
(125,'Who Wanna','4:32',10),
(126,'On The Block #2','0:24',10),
(127,'Get Down (Feat. Baby AKA The Birdman)','4:33',10),
(128,'Snitch','3:54',10),
(129,'Hoes (Feat. Mannie Fresh)','4:27',10),
(130,'Only Way (Feat. Baby AKA The Birdman)','4:33',10),
(131,'Earthquake','4:55',10),
(132,'Ain\'t That A Bi**h','4:00',10),
(133,'Walk Out','1:09',10),
(134,'You Should Be Dancing','4:17',11),
(135,'You Stepped Into My Life','3:25',11),
(136,'Love So Right','3:37',11),
(137,'Lovers','3:35',11),
(138,'Can\'t Keep A Good Man Down','4:43',11),
(139,'Boogie Child','4:11',11),
(140,'Love Me','3:58',11),
(141,'Subway','4:23',11),
(142,'The Way It Was','3:18',11),
(143,'Children Of The World','3:07',11),
(151,'The 1975','4:56',13),
(152,'People','2:39',13),
(153,'The End (Music For Cars)','2:31',13),
(154,'Frail State Of Mind','3:54',13),
(155,'Streaming','1:33',13),
(156,'The Birthday Party','4:46',13),
(157,'Yeah I Know','4:13',13),
(158,'Then Because She Goes','2:07',13),
(159,'Jesus Christ 2005 God Bless America','4:24',13),
(160,'Roadkill','2:55',13),
(161,'Me & You Together Song','3:27',13),
(162,'I Think There\'s Something You Should Know','4:01',13),
(163,'Nothing Revealed / Everything Denied Choir','3:38',13),
(164,'Tonight (I Wish I Was Your Boy)','4:07',13),
(165,'Shiny Collarbone','2:51',13),
(166,'If You\'re Too Shy (Let Me Know)','5:19',13),
(167,'Playing On My Mind','3:24',13),
(168,'Having No Head','6:05',13),
(169,'What Should I Say','4:06',13),
(170,'Bagsy Not In Net','2:27',13),
(171,'Don\'t Worry','2:48',13),
(172,'Guys','4:30',13),
(173,'The Gunner','3:38',14),
(174,'Wake + Bake','3:03',14),
(175,'Go for Broke (Feat. James Arthur)','3:30',14),
(176,'At My Best (Feat. Hailee Steinfeld)','3:19',14),
(177,'Kiss the Sky','3:07',14),
(178,'Golden God','3:18',14),
(179,'Trap Paris (Feat. Quavo and Ty Dolla $ign)','3:23',14),
(180,'Moonwalkers (Feat. DubXX)','3:05',14),
(181,'Can\'t Walk','3:05',14),
(182,'Bad Things (Feat. Camila Cabello)','3:59',14),
(183,'Rehab','4:26',14),
(184,'Let You Go','3:05',14),
(185,'27','4:58',14),
(186,'The Ringer','5:37',15),
(187,'Greatest','3:46',15),
(188,' Lucky You (Feat. Joyner Lucas)','4:04',15),
(189,'Paul (Skit)','0:35',15),
(190,'Normal','3:42',15),
(191,'Em Calls Paul (Skit)','0:49',15),
(192,'Stepping Stone','5:09',15),
(193,' Not Alike (Feat. Royce Da 5\'9)','4:48',15),
(194,'Kamikaze','3:36',15),
(195,'Fall','4:22',15),
(196,'Nice Guy (Feat. Jessie Reyez)','2:30',15),
(197,'Good Guy (Feat. Jessie Reyez)','2:22',15),
(198,'Venom (Music From The Motion Picture)','4:29',15),
(199,'BEP Empire','4:39',16),
(200,'Weekends','4:46',16),
(201,'Get Original','2:52',16),
(202,'Hot','4:04',16),
(203,'Cali To New York','4:04',16),
(204,'Lil\' Lil\'','4:10',16),
(205,'On My Own','3:52',16),
(206,'Release','5:07',16),
(207,'Bridging The Gaps','4:56',16),
(208,'Go Go','4:53',16),
(209,'Rap Song','3:42',16),
(210,'Bringing It Back','3:36',16),
(211,'Tell Your Mama Come','3:14',16),
(212,'Request + Line','3:35',16),
(213,'Tha Mobb','5:21',17),
(214,'Fly In','2:23',17),
(215,'Money On My Mind','4:32',17),
(216,'Fireman - Main','4:24',17),
(217,'Mo Fire','3:24',17),
(218,'On Tha Block #1 - Skit','0:39',17),
(219,'Best Rapper Alive','4:54',17),
(220,'Lock And Load','4:47',17),
(221,'Oh No','3:12',17),
(222,'Grown Man','4:06',17),
(223,'On Tha Block #2 - Skit','0:26',17),
(224,'Hit Em Up','4:07',17),
(225,'Carter II','2:24',17),
(226,'Hustler Musik','5:04',17),
(227,'Receipt','3:48',17),
(228,'Shooter','4:35',17),
(229,'Weezy Baby','4:19',17),
(230,'On Tha Block #3 - Skit','0:14',17),
(231,'I\'m A Ddboy','4:00',17),
(232,'Feel Me','3:49',17),
(233,'Get Over','4:43',17),
(234,'Fly Out','2:25',17),
(235,'The Illest Villains','1:55',18),
(236,'Accordion','1:58',18),
(237,'Meat Grinder','2:11',18),
(238,'Bistro','1:07',18),
(239,'Raid (Feat. MED a.k.a. Medaphoar)','2:30',18),
(240,'America\'s Most Blunted (Feat. Lord Quas)','3:54',18),
(241,'Sickfit (Instrumental)','1:21',18),
(242,'Rainbows','2:51',18),
(243,'Curls','1:35',18),
(244,'Do Not Fire! (Instrumental)','0:52',18),
(245,'Money Folder','3:02',18),
(246,'Shadows of Tomorrow (Feat. Lord Quas)','2:36',18),
(247,'Operation Lifesaver AKA Mint Test','1:30',18),
(248,'Figaro','2:25',18),
(249,'Hardcore Hustle (Feat. Wildchild)','1:21',18),
(250,'Strange Ways','1:51',18),
(251,'Fancy Clown (Feat. Viktor Vaughn)','1:55',18),
(252,'Eye (Feat. Stacy Epps)','1:57',18),
(253,'Supervillain Theme (Instrumental)','0:52',18),
(254,'All Caps','2:10',18),
(255,'Great Day','2:16',18),
(256,'Rhinestone Cowboy','3:59',18),
(436,'Side A','1:07',26),
(437,'Side B','1:01',26),
(438,'Side C','1:00',26),
(439,'Side D','0:57',26),
(440,'Side E','0:33',26),
(441,'Side F','0:17',26),
(442,'Side G','0:09',26),
(443,'Side H','0:17',26),
(444,'Everlasting Light','2:22',25),
(445,'Next Girl','3:37',25),
(446,'Tighten Up','2:41',25),
(447,'Howlin\' For You','4:07',25),
(448,'She\'s Long Gone','3:11',25),
(449,'Black Mud','3:41',25),
(450,'The Only One','4:02',25),
(451,'Too Afraid To Love You','3:31',25),
(452,'Ten Cent Pistol','2:59',25),
(453,'Sinister Kid','1:49',25),
(454,'The Go Getter','2:55',25),
(455,'I\'m Not The One','3:26',25),
(456,'Unknown Brother','1:59',25),
(457,'Never Gonna Give You Up','4:07',25),
(458,'These Days','3:31',25),
(459,'Truth Without Love','3:22',24),
(460,'Time Machine','4:02',24),
(461,'Authors Of Forever','3:37',24),
(462,'Wasted Energy','2:22',24),
(463,'Underdog','3:03',24),
(464,'3 Hour Drive','4:13',24),
(465,'Me X 7','2:10',24),
(466,'Show Me Love','4:28',24),
(467,'So Done','3:12',24),
(468,'Gramercy Park','3:21',24),
(469,'Love Looks Better','1:59',24),
(470,'You Save Me','3:57',24),
(471,'Jill Scott','3:09',24),
(472,'Perfect Way To Die','2:12',24),
(473,'Good Job','5:07',24),
(474,'Intro (Feat. Cedric the Entertainer)','1:21',23),
(475,'St. Louie','4:27',23),
(476,'Greed, Hate, Envy','4:15',23),
(477,'Country Grammar (Hot Shit)','4:47',23),
(478,'Steal the Show (Feat. St. Lunatics)','5:27',23),
(479,'Interlude (Feat. Cedric the Entertainer)','0:33',23),
(480,'Ride wit Me (Feat. City Spud)','4:51',23),
(481,'E.I.','4:34',23),
(482,'Thicky Thick Girl (Feat. Murphy Lee & Ali)','4:34',23),
(483,'For My (Feat. Lil\' Wayne)','4:08',23),
(484,'Utha Side','4:33',23),
(485,'Tho Dem Wrappas','4:09',23),
(486,'Wrap Sumden (Feat. St. Lunatics)','4:16',23),
(487,'Batter Up (Feat. Murphy Lee and Ali)','5:27',23),
(488,'Never Let \'Em C U Sweat (Feat. The Teamsters)','4:14',23),
(489,'Luven Me','4:07',23),
(490,'Outro (Feat. Cedric the Entertainer)','0:44',23),
(491,'Bookhead','1:59',22),
(492,'Pause Tape','2:06',22),
(493,'The Signs (Feat. Mr. Gone)','1:28',22),
(494,'Viberion Son (Feat. Del The Funky Homosapien)','3:34',22),
(495,'Rhymin Slang (Dave Sitek Remix)','2:38',22),
(496,'Guv\'nor (BADBADNOTGOOD Remix)','2:52',22),
(497,'Retarded Fren (Thom Yorke & Jonny Greenwood version)','3:17',22),
(498,'Bookfiend (Clams Casino version)','2:53',22),
(499,'Banished (Beck Remix)','2:53',22),
(500,'El Chupa Nibre','2:40',21),
(501,'Sofa King','2:57',21),
(502,'The Mask','3:12',21),
(503,'Perfect Hair','2:03',21),
(504,'Benzie Box','3:00',21),
(505,'Old School','2:40',21),
(506,'A.T.H.F','3:03',21),
(507,'Basket Case','2:35',21),
(508,'No Names','3:07',21),
(509,'Crosshairs','2:27',21),
(510,'Mince Meat','2:33',21),
(511,'Vats of Urine','1:48',21),
(512,'Space Ho\'s','3:29',21),
(513,'Bada Bing','4:35',21),
(530,'Intro','1:09',19),
(531,'Change the Beat','3:04',19),
(532,'Name Dropping','1:42',19),
(533,'Dead Bent','2:22',19),
(534,'Go with the Drawls','0:58',19),
(535,'Gas Flows','2:41',19),
(536,'Doomsday','3:22',19),
(537,'Hey!','3:22',19),
(538,'Accordion','1:50',19),
(539,'Curls / Great Day','3:47',19),
(540,'Rhymes Like Dimes','2:50',19),
(541,'I Hear Voices','2:04',19),
(542,'My Favorite Ladies','2:06',19),
(543,'One Beer','3:33',19),
(544,'Fine Print','3:51',19),
(561,'D Ski\'s Intro','1:22',20),
(562,'Nineteen Seventy Something','2:52',20),
(563,'Son of Yvonne','3:15',20),
(564,'Da\'Pro','3:15',20),
(565,'Store Frontin\'','0:37',20),
(566,'Me and My Gang','3:35',20),
(567,'Crush Hour (Feat. Pav Bundy)','2:42',20),
(568,'Think I Am (Feat. Big Daddy Kane & MF Doom)','2:40',20),
(569,'Fresh Fest (Feat. Reggie B)','3:19',20),
(570,'Hoe-Tel Leftovers','0:32',20),
(571,'Slow Down','4:33',20),
(572,'Home Sweet Home','3:00',20),
(573,'Dedication','1:00',20),
(574,'I Did It','3:24',20),
(575,'In da Spot (Feat. Milani the Artist)','2:54',20),
(576,'Outtakes','3:32',20),
(577,'Here Comes The Cowboy','3:00',29),
(578,'Nobody','3:32',29),
(579,'Finally Alone','2:25',29),
(580,'Little Dogs March','2:29',29),
(581,'Preoccupied','4:00',29),
(582,'Choo Choo','2:39',29),
(583,'K','3:33',29),
(584,'Heart To Heart','3:31',29),
(585,'Hey Cowgirl','2:16',29),
(586,'On The Square','3:29',29),
(587,'All Of Our Yesterdays','4:04',29),
(588,'Skyless Moon','4:05',29),
(589,'Baby Bye Bye','7:29',29),
(590,'Alone Again','4:10',30),
(591,'Too Late','3:59',30),
(592,'Hardest To Love','3:31',30),
(593,'Scared To Live','3:11',30),
(594,'Snowchild','4:07',30),
(595,'Escape From LA','5:55',30),
(596,'Heartless','3:21',30),
(597,'Faith','4:43',30),
(598,'Blinding Lights','3:21',30),
(599,'In Your Eyes','3:57',30),
(600,'Save Your Tears','3:35',30),
(601,'Repeat After Me (Interlude)','3:15',30),
(602,'After Hours','6:01',30),
(603,'Until I Bleed Out','3:12',30),
(604,'We Right Here','3:23',31),
(605,'Who We Be','2:55',31),
(606,'The Rain','3:11',31),
(607,'Where The Hood At','4:49',31),
(608,'Party Up (Up In Here)','3:47',31),
(609,'A\'Yo Kato','5:59',31),
(610,'Slippin\'','3:03',31),
(611,'The Prayer IV','3:17',31),
(612,'Future Nostalgia','3:04',32),
(613,'Don’t Start Now','3:03',32),
(614,'Cool','2:29',32),
(615,'Physical','3:13',32),
(616,'Levitating','3:23',32),
(617,'Pretty Please','3:14',32),
(618,'Hallucinate','3:28',32),
(619,'Love Again','4:18',32),
(620,'Break My Heart','3:41',32),
(621,'Good In Bed','3:38',32),
(622,'Boys Will Be Boys','2:56',32),
(624,'That\'s What Love Is','2:57',1),
(625,'At Least For Now','2:34',1),
(626,'Over My Dead Body','4:32',34),
(627,'Shot For Me','3:44',34),
(628,'Headlines','3:56',34),
(629,'Crew Love (Feat. The Weeknd)','3:28',34),
(630,'Take Care (Feat. Rihanna)','4:37',34),
(631,'Marvin\'s Room','5:47',34),
(632,'Buried Alive Interlude (By Kendrick Lamar)','2:31',34),
(633,'Under Ground Kings','3:32',34),
(634,'We\'ll Be Fine (Feat. Birdman)','4:08',34),
(635,'Make Me Proud (Feat. Nicki Minaj)','3:39',34),
(636,'Lord Knows (Feat. Rick Ross)','5:07',34),
(637,'Cameras / Good Ones Go Interlude','7:15',34),
(638,'Doing It Wrong','4:25',34),
(639,'The Real Her (Feat. Lil Wayne & André 3000)','5:21',34),
(640,'Look What You\'ve Done','5:02',34),
(641,'HYFR (Hell Ya Fucking Right) (Feat. Lil Wayne)','3:26',34),
(642,'Practice','3:57',34),
(643,'The Ride','5:51',34);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`id`,`first_name`,`last_name`,`email`,`phone_number`,`username`,`password`) values 
(1,'Katarina','Bešlić','katarinabeslic@gmail.com','+381691996901','katarina','$2a$10$cTVTrGw/NWXsN5tTFyY43uzm4HL98OjeS4k3Es2TNibmWkxEbw7hC'),
(5,'Mile','Miletić','mile@gmail.com','+3985464451','mile','$2a$10$GaNZq.tmxwJGVHwXnYUgDOoYtnV5ncUHtH1rS.S.JImGLxLkW0i0W'),
(6,'Mike','Myers','mike@gmail.com','+381654545155','mike','$2a$10$GaNZq.tmxwJGVHwXnYUgDOoYtnV5ncUHtH1rS.S.JImGLxLkW0i0W'),
(7,'Jacob','Daniels','jacob@gmail.com','+4512059968','jacob','$2a$10$9bYxkAOVN4FTE9IrooMiYeyzBGuXKgYM/KhyhRM8RGy8s7fcz8ZTG'),
(9,'Lidija','Simić','lidija@gmail.com','+381651212799','lidija','$2a$10$g..ZAARJjrDG1aKT5R3S9OF0Qw1vKJxFMpaOHahFkqSr56T6vrX.K'),
(10,'David','Jovanović','david@gmail.com','+381621346988','david','$2a$10$MXRByeDf4mJPH4ByEQNkqe1n.jViSL.QmEFlc8UDuN5SDelKPM3z6'),
(11,'Ilija','Antovic','ilija@gmail.com','+381654545112','ilija','$2a$10$aThbTv0AZHmI6Mnr2E7VLOCjAGoerrjtKUVe4.ysvqRRMUWo.n.2.');

/*Table structure for table `user_order` */

DROP TABLE IF EXISTS `user_order`;

CREATE TABLE `user_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `currency` varchar(255) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `total_price` decimal(18,2) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `card_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlguxu9qa64s58y51e1371sxks` (`address_id`),
  KEY `FKto9iq2y7pm250h30t02fq0m1` (`card_id`),
  KEY `FKj86u1x7csa8yd68ql2y1ibrou` (`user_id`),
  CONSTRAINT `FKj86u1x7csa8yd68ql2y1ibrou` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKlguxu9qa64s58y51e1371sxks` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKto9iq2y7pm250h30t02fq0m1` FOREIGN KEY (`card_id`) REFERENCES `card_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_order` */

insert  into `user_order`(`id`,`currency`,`date_time`,`order_status`,`payment_method`,`total_price`,`address_id`,`card_id`,`user_id`) values 
(15,'EUR','2021-03-25 16:22:19','Delivered','card',28.99,2,1,6),
(17,'EUR','2021-03-26 12:24:00','Confirmed','card',28.99,2,1,6),
(26,'EUR','2021-04-05 23:17:26','Received','card',39.99,3,8,5),
(27,'EUR','2021-04-05 23:19:21','Cancelled','card',24.99,3,8,5),
(31,'EUR','2021-04-13 20:26:50','Received','card',33.99,2,1,6),
(32,'EUR','2021-04-14 16:27:57','Received','card',15.99,3,8,5),
(33,'EUR','2021-04-14 20:39:51','Delivered','paypal',41.99,2,NULL,6),
(34,'EUR','2021-04-14 20:51:20','Received','paypal',28.99,3,NULL,5),
(36,'EUR','2021-04-14 22:01:09','Delivered','paypal',24.99,5,NULL,9),
(37,'EUR','2021-04-14 22:05:01','Received','paypal',26.99,3,NULL,5),
(38,'EUR','2021-04-14 23:31:40','Received','paypal',52.99,3,NULL,5),
(39,'EUR','2021-04-15 16:31:15','Confirmed','paypal',68.98,6,NULL,10),
(41,'EUR','2021-04-16 13:22:43','Received','paypal',29.99,5,NULL,9),
(42,'EUR','2021-04-17 15:48:24','Delivered','paypal',119.97,7,NULL,11),
(43,'EUR','2021-05-25 13:24:30','Received','card',29.99,3,8,5),
(44,'EUR','2021-06-01 14:40:48','Received','card',51.98,8,10,1),
(45,'EUR','2021-06-01 14:56:45','Received','paypal',60.98,8,NULL,1),
(46,'EUR','2021-06-01 15:02:17','Received','paypal',60.98,2,NULL,6),
(48,'EUR','2021-06-14 09:49:21','Cancelled','paypal',60.98,8,NULL,1),
(49,'EUR','2021-07-01 15:05:45','Received','card',51.98,8,10,1);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`role_id`,`user_id`) values 
(1,1,1),
(2,2,5),
(3,2,6),
(4,2,7),
(5,1,7),
(6,2,10),
(77,2,9),
(86,2,11);

/*Table structure for table `vinyl` */

DROP TABLE IF EXISTS `vinyl`;

CREATE TABLE `vinyl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vinyl_name` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `img_url` varchar(244) DEFAULT NULL,
  `price` decimal(18,2) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `artist_id` int(11) DEFAULT NULL,
  `record_label_id` int(11) DEFAULT NULL,
  `format_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `artist_id` (`artist_id`),
  KEY `record_label_id` (`record_label_id`),
  KEY `format_id` (`format_id`),
  CONSTRAINT `vinyl_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `vinyl_ibfk_2` FOREIGN KEY (`record_label_id`) REFERENCES `record_label` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `vinyl_ibfk_3` FOREIGN KEY (`format_id`) REFERENCES `format` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4;

/*Data for the table `vinyl` */

insert  into `vinyl`(`id`,`vinyl_name`,`description`,`img_url`,`price`,`stock`,`artist_id`,`record_label_id`,`format_id`) values 
(1,'Changes','Changes is the fifth studio album by Canadian singer-songwriter Justin Bieber. It was released on through Def Jam Recordings and RBMG on February 14, 2020. It was released on Valentine\'s Day and serves as the follow-up to his previous album, Purpose (2015). The album features guest appearances from Quavo, Post Malone, Clever, Lil Dicky, Travis Scott, Kehlani, and Summer Walker. The production is handled by Bieber himself, Adam Messinger, The Audibles, Boi-1da, Harv, Nasri, Poo Bear, Sasha Sirota, Tainy, and Vinylz, as well as NBA center JaVale McGee, using his music name Pierre. It is primarily a pop, R&B, and electro-R&B record, with trap elements.','/images/vinyl/justin-bieber_changes.jpg',34.99,100,1,2,1),
(2,'Smile','Smile is the sixth studio album by American singer Katy Perry. It was released on August 28, 2020, by Capitol Records, three years after its predecessor Witness (2017). Perry worked with a multitude of producers on the album, including Josh Abraham, Carolina Liar, The Daylights, G Koop, Andrew Goldstein, Oligee, Oscar Görres, Oscar Holter, Ilya, Ian Kirkpatrick, The Monsters & Strangerz, Charlie Puth, Stargate and Zedd. She described Smile as her \"journey towards the light, with stories of resilience, hope, and love\". Musically a pop album, Smile is characterized by its themes of self-help and empowerment.','/images/vinyl/katy-perry_smile.jpg',25.99,47,2,3,1),
(3,'Chromatica','Chromatica is the sixth studio album by American singer Lady Gaga, released on May 29, 2020 by Interscope Records and subsidiary Streamline. Interscope postponed the album\'s release as a result of the COVID-19 pandemic. Gaga supervised the production with longtime collaborator BloodPop, Blackpink, Ariana Grande, Elton John, and a variety of producers to create a concept album exploiting her dance-pop roots. Chromatica eschews the acoustic style of Gaga\'s previous releases, Joanne (2016) and A Star Is Born (2018), for a sound gleaning vintage 1990s house music. Most of the album\'s recording was conducted at Henson Recording Studios and Gaga\'s in-home Hollywood Hills studio.','/images/vinyl/lady-gaga_chromatica.jpg',29.99,98,4,5,1),
(4,'Good Girl Gone Bad','Good Girl Gone Bad is the third studio album by Barbadian singer Rihanna. It was released on May 31, 2007, by Def Jam Recordings and SRP Records. Rihanna worked with various producers on the album, including Christopher \"Tricky\" Stewart, Terius \"Dream\" Nash, Neo da Matrix, Timbaland, Carl Sturken, Evan Rogers and Stargate. Inspired by Brandy Norwood\'s fourth studio album Afrodisiac (2004), Good Girl Gone Bad is a pop, dance-pop and R&B record with 1980s music influences. Described as a turning point in Rihanna\'s career, it represents a departure from the Caribbean sound of her previous releases, Music of the Sun (2005) and A Girl like Me (2006). Apart from the sound, she also endorsed a new image for the release going from an innocent young woman to an edgier, more mature look.','/images/vinyl/rihanna_good-girl-gone-bad.jpg',33.99,34,3,4,2),
(5,'The $5.98 E.P. – Garage Days Re-Revisited','The $5.98 E.P. – Garage Days Re-Revisited (re-released as The $9.98 CD – Garage Days Re-Revisited) is an EP by American heavy metal band Metallica, released on August 21, 1987 by Elektra Records. It consists entirely of covers of late-\'70s and early-\'80s new wave of British heavy metal bands and punk rock music rehearsed in Lars Ulrich\'s soundproofed garage and then recorded in Los Angeles over the course of six days. It is the group\'s first release following the death of bassist Cliff Burton and the first to feature his successor, Jason Newsted.','/images/vinyl/metallica-The_$5.98_E.P.-Garage_Days_Re-Revisited.jpg',22.99,14,5,6,1),
(6,'Sucker Punch','Sucker Punch is the debut studio album by Norwegian singer Sigrid, released on 8 March 2019 through Island Records. It follows the release of Sigrid\'s 2018 EP Raw. None of the songs featured on Raw were included on the album; however, two were included from Sigrid\'s debut EP Don\'t Kill My Vibe (2017). Sigrid enlisted collaborators Martin Sjølie, Odd Martin Skalnes, Oscar Holter, Askjell Solstrand, Patrik Berger, and Martin Stilling for the album\'s production. The result was a pop, electropop, and synth-pop record described by music critics as exploring both mainstream and indie pop musical styles.','/images/vinyl/sigrid-sucker_punch.jpg',24.99,35,6,7,1),
(7,'America','America (stylized in uppercase) is the fifth studio album by American rock band Thirty Seconds to Mars, released on April 6, 2018 through Interscope Records. It is their first album in five years, after Love, Lust, Faith and Dreams (2013), as well as their first release for Interscope, following the band\'s departure from Virgin Records in 2014. It is also the final album by the band to feature lead guitarist Tomo Miličević, who left the band two months after its release.','/images/vinyl/30stm-america.jpg',34.99,74,7,8,1),
(8,'Voodoo Lounge','Voodoo Lounge is the 20th British and 22nd American studio album by British rock band The Rolling Stones, released in July 1994. As their first new release under their new alliance with Virgin Records, it ended a five-year gap since their last studio album, Steel Wheels in 1989. Voodoo Lounge is also the band\'s first album without long-time bassist Bill Wyman. He left the band in early 1991, though the Stones did not formally announce the departure until 1993.[2] In 2009, the album was remastered and reissued by Universal Music. This album was released as a double album on vinyl and a single CD and cassette.','/images/vinyl/the-rolling-stones_voodo-lounge.jpg',52.99,9,8,9,3),
(9,'In My Lifetime, Vol. 1','In My Lifetime, Vol. 1 is the second studio album by American rapper Jay-Z, released by Roc-A-Fella Records and Def Jam Recordings on November 4, 1997. The album debuted at #3 on the US Billboard 200 chart and was certified Platinum by the RIAA. The album sold over 138,000 copies in its first week.','/images/vinyl/jay-z_in-my-lifetime-vol-1.jpg',29.99,20,9,1,2),
(10,'Tha Carter','Tha Carter is the fourth studio album by American rapper Lil Wayne. It was released on June 29, 2004, by Cash Money Records and Universal Records. The production on the album was mostly handled by Cash Money\'s former in-house producer Mannie Fresh, before Mannie left the label. The album spawned four sequels: Tha Carter II, Tha Carter III, Tha Carter IV, and Tha Carter V. The album debuted at number five on the US Billboard 200 chart, selling 116,000 copies in its first week. The album was later certified platinum by the Recording Industry Association of America (RIAA) in September 2020.','/images/vinyl/lil-wayne_tha-carter.jpg',39.99,22,10,10,1),
(11,'Children Of The World','Children of the World is a 1976 album by the Bee Gees. The first single, \"You Should Be Dancing\", went to No. 1 in the US and Canada, and was a top ten hit in numerous other territories. The album has sold over 2.5 million copies. It was the group\'s fourteenth album (twelfth internationally). The album was re-issued on CD by Reprise Records and Rhino Records in 2006. This was the first record featuring the Gibb-Galuten-Richardson production team which would have many successful collaborations in the following years.','/images/vinyl/bee-gees_children-of-the-world.jpg',22.99,10,11,11,1),
(13,'Notes On A Conditional Form','Notes on a Conditional Form is the fourth studio album by English band the 1975. It was released on 22 May 2020 through Dirty Hit and Polydor Records. In February 2017, the band announced the follow-up to their second album, I Like It When You Sleep, for You Are So Beautiful yet So Unaware of It (2016), would be titled Music For Cars, a reference to their third extended play of the same name (2013). Initially intended as the final chapter of a planned trilogy, Music For Cars later evolved into an \"era\" consisting of two albums. The first part, A Brief Inquiry into Online Relationships, released in November 2018. Recording of the second part took place over 19 months, spanning 15 studios in four countries. In addition to extended sessions in London, four months in Los Angeles and a long-term residence at Angelic Residential Recording Studio in Northamptonshire, large parts of the album were recorded in a mobile studio while the band performed on their Music for Cars Tour. Due to vinyl manufacturing issues and lead singer Matthew Healy\'s tendency to announce arbitrary release dates, it faced several delays. Ultimately, the 1975 submitted the album two weeks before the COVID-19 pandemic forced most of the world into lockdowns.','/images/vinyl/the-1975_notes-on-a-conditional-form.jpg',37.99,12,12,12,2),
(14,'Bloom','Bloom (stylized as bloom) is the third studio album by American musician Machine Gun Kelly. It was released on May 12, 2017, by Bad Boy, Interscope Records and EST 19XX.The album was preceded by the hit single, \"Bad Things\", a collaboration with Camila Cabello, which reached number four on the Billboard Hot 100, The album features guest appearances by rapper Quavo and singers Hailee Steinfeld, Ty Dolla $ign, and James Arthur.','/images/vinyl/machine-gun-kelly_bloom.jpg',28.99,25,13,13,1),
(15,'Kamikaze','Kamikaze (stylized as KAMIKAZƎ) is the tenth studio album by American rapper Eminem, released on August 31, 2018 by Aftermath Entertainment, Interscope Records, and Shady Records without prior announcement. The album features guest appearances from Joyner Lucas, Royce da 5\'9\", Jessie Reyez, and uncredited vocals by Justin Vernon. Eminem, credited as Slim Shady, and Dr. Dre served as executive producers, while production for individual tracks comes from a variety of musicians.','/images/vinyl/eminem-kamikaze.jpg',28.99,147,14,14,1),
(16,'Bridging The Gap','Bridging the Gap is the second studio album by American hip hop group Black Eyed Peas, released on September 26, 2000. This is their last album where they are credited as Black Eyed Peas, until the release of Masters of the Sun Vol. 1 in 2018.\nThe album had three official singles: \"BEP Empire/Get Original\", \"Weekends\" and \"Request + Line\", the last featuring Macy Gray. The song \"Weekends\" was later remixed and renamed \"Another Weekend\" for the Deluxe Edition of their fifth studio album, The E.N.D. Singer Kim Hill was deeply involved in making the album, but left the group after it was released. The album received favorable reviews, and holds a score of 74 on Metacritic.','/images/vinyl/black-eyed-peace_bridging-the-gap.jpg',26.99,20,15,15,1),
(17,'Tha Carter II','Tha Carter II is the fifth studio album by American rapper Lil Wayne. It was released on December 6, 2005, by Cash Money Records and Universal Distribution. Recording sessions took place from 2004 to 2005, with Birdman and his brother Ronald \"Slim\" Williams serving as the record\'s executive producers. Additional producers on the album included The Runners and The Heatmakerz, among others. The album serves as a sequel to his fourth album Tha Carter (2004), and is classified as a southern hip hop record. It was supported by three singles (\"Fireman\", \"Hustler Musik\" and \"Shooter\").\nTha Carter II received critical acclaim and ranks highly in retrospectives of Lil Wayne\'s best work. The album debuted at number two on the US Billboard 200 chart. The album was later certified double platinum by the Recording Industry Association of America (RIAA) in September 2020.[4]','/images/vinyl/lil-wayne_tha-carter-2.jpg',39.99,24,10,16,1),
(18,'Madvillainy','Madvillainy is the only studio album by American hip hop duo Madvillain, comprising MC MF Doom and producer Madlib. It was released on March 23, 2004, on Stones Throw Records.\nThe album was recorded between 2002 and 2004. Madlib created most of the instrumentals during a trip to Brazil in his hotel room using minimal amounts of equipment: a Boss SP-303 sampler, a turntable, and a tape deck.[1] Fourteen months before the album was released, an unfinished demo version was stolen and leaked onto the internet. Frustrated, the duo stopped working on the album and returned to it only after they had released other solo projects.','/images/vinyl/mf-doom_madvillainy.jpg',33.99,18,16,17,1),
(19,'Live from Planet X','Live from Planet X is a live album by British-American rapper/producer MF Doom. It was released via Nature Sounds on March 5, 2005. It was recorded live in San Francisco, California on January 22, 2004. Originally titled Live at the DNA Lounge, the album was initially given away with Special Herbs, Vols. 5 & 6. It includes tracks from Operation: Doomsday, Take Me to Your Leader, and Madvillainy.','/images/vinyl/mf-doom_live-from-planet-x.jpg',29.99,25,17,1,1),
(20,'MA Doom: Son of Yvonne','MA Doom: Son of Yvonne is the fourth solo studio album by American rapper Masta Ace. The beats on the album are sourced from the Special Herbs series of instrumental mixtapes by MF Doom; Doom did not directly collaborate with Masta Ace in the production of this album outside of giving Ace his blessing to use the beats. DOOM, however, makes a vocal appearance on the song \"Think I Am\", alongside fellow guest star Big Daddy Kane. Other guests on the album include Pav Bundy, Reggie B and Milani the Artist. The album was released on July 17, 2012, via M3 Records and Fat Beats Records.','/images/vinyl/mf-doom_son-of-yvonne.jpg',31.99,9,18,19,1),
(21,'The Mouse & The Mask','Back in 2005, super producer Danger Mouse and masked hip hop supervillain DOOM came together for “The Mouse And The Mask”, a collaborative album released under the name Dangerdoom. Among DOOM’s most successful projects both critically and commercially, the album was inspired by Cartoon Network’s late-night Adult Swim programming slate, and includes appearances from several Adult Swim characters, including Aqua Teen Hunger Force, Space Ghost, Harvey Birdman, and more.\n“The Mouse And The Mask” also features guest spots from Talib Kweli, Ghostface Killah, and Cee Lo Green. This deluxe 3xLP vinyl reissue courtesy of DOOM’s own label Metalface Records also contains several bonus cuts not on the original album. The full 7-track 2006 Dangerdoom EP “Occult Hymn” is included, as well as two previously unreleased tracks, making this the definitive Dangerdoom collection.','/images/vinyl/mf-doom_the-mouse-and-the-mask.jpg',28.99,20,19,20,1),
(22,'Bookhead EP','Bookhead EP is a collaborative EP by alternative hip hop artists Jneiro Jarel and MF Doom under the moniker JJ Doom. It was released on Lex Records on February 17, 2014. It features 9 tracks from the 2013 Butter Edition re-release of Key to the Kuffs. The EP was re-released on Vinyl by Lex Records on May 22, 2017.','/images/vinyl/mf-doom_bookhead.jpg',15.99,19,20,21,1),
(23,'Country Grammar','Country Grammar is the debut studio album by American rapper Nelly. It was released on June 27, 2000, by Universal Records. The production on the album was handled by Jason \"Jay E\" Epperson, with additional production by C-Love, Kevin Law, City Spud, Steve \"Blast\" Wills and Basement Beats. Nelly contributed to all lyrics on the album, with Epperson and City Spud also contributing. The album introduced a unique St. Louis, Midwestern sound, and introduces Nelly\'s vocal style of pop-rap singalongs and Midwestern, Missouri twang. It was supported four successful singles: \"Country Grammar (Hot Shit)\", \"E.I.\", \"Ride wit Me\" and \"Batter Up\". Its lead single, \"Country Grammar (Hot Shit)\", peaked at number 7 on the Billboard Hot 100 and UK Singles Chart. \"E.I.\" charted at number 16, number 12 and number 11 on the Hot 100, UK Singles Chart and ARIA Singles Chart, respectively. \"Ride wit Me\" peaked within the top five on the Hot 100, ARIA Singles Chart, Irish Singles Chart and UK Singles Chart. The album\'s fourth and final single, \"Batter Up\" featuring Murphy Lee and Ali, achieved moderate chart success.\n\nCountry Grammar received positive reviews, with critics praising Nelly\'s vocal style and the album\'s production. It topped the US Billboard 200 chart for five consecutive weeks, and the US Top R&B/Hip-Hop Albums chart for six consecutive weeks. It peaked in the top five on the New Zealand Albums Chart and Australian Albums Chart, as well as the top ten on the Canadian Albums Chart and Dutch Albums Chart. The album was certified three times platinum by the Recording Industry Association of New Zealand (RIANZ) and Music Canada (MC), denoting shipments of 45,000 and 300,000 copies, respectively.\n\nIn 2016, Country Grammar became the ninth hip hop album to be certified Diamond by the Recording Industry Association of America (RIAA),[2] denoting shipment of 10 million copies in the US. Its commercial success secured Nelly\'s status as one of the most successful hip hop acts of the 2000s decade. On Billboard\'s decade-end chart, Nelly ranked as the third most successful act of the 2000s decade, due largely to the success of Country Grammar and his follow-up album Nellyville (2002).','/images/vinyl/nelly_country-grammar.jpg',37.99,30,22,22,1),
(24,'Alicia','Alicia is the seventh studio album by American singer-songwriter Alicia Keys. It was primarily recorded at Oven Studios and Jungle City Studios, both in New York, during 2017 to 2019 and released by RCA Records on September 18, 2020. Written and produced largely by Keys, the album also features songwriting and production contributions from Swizz Beatz, Ludwig Göransson, Rob Knox, Ed Sheeran, and The-Dream, among others. Keys collaborated with more artists on the recording than in her previous albums, enlisting vocalists such as Sampha, Tierra Whack, Diamond Platnumz, Snoh Aalegra, and Jill Scott for certain tracks.\n\nAlicia\'s mostly low-tempo and subtly melodic music reconciles the experimental direction of Keys\' previous album Here (2016) with her earlier work\'s bass drum-driven R&B and piano-based balladry. Throughout, individual songs incorporate sounds from a wide range of other genres, including orchestral pop, progressive soul, funk, ambient, country, and Caribbean music. Thematically, they explore identity as a multifaceted concept, sociopolitical concerns, and forms of love within the framework of impressionistic lyrics and personal narratives. The album has been described by Keys as therapeutic and reflective of greater introspection in herself, expressing ideas and feelings of hope, frustration, despair, ambivalence, and equanimity shared in her memoir More Myself (2020), which was written during Alicia\'s recording.\n\nThe album was originally scheduled to be released on March 20, 2020, then May 15, before being delayed indefinitely in response to the COVID-19 pandemic. It was marketed with an extended traditional rollout campaign that featured various media appearances by Keys and the release of seven singles, including the Miguel duet \"Show Me Love\", \"Time Machine\", \"Underdog\", and \"So Done\" (with Khalid). After a surprise announcement of its impending release in September, Alicia debuted at number four on the Billboard 200 in its first week and became Keys\' eighth top-10 record in the US, while charting in the top 10 in several other countries. However, it fell off the US chart a few weeks later.\n\nA critical success, Alicia received praise for Keys\' nuanced vocal performances and the music\'s accessibility, while her thematic messages were considered balanced, healing, and timely against the backdrop of unfolding world events. The singles \"Good Job\" and \"Perfect Way to Die\" resonated especially with the importance of essential workers during the pandemic and with the 2020–2021 racial unrest over police brutality in the US, respectively. In further support of the album, Keys will perform in concert from June to September 2021 on Alicia – The World Tour, which was postponed from the previous year due to the pandemic.','/images/vinyl/alicia-keys_alicia.jpg',41.99,149,23,23,1),
(25,'Brothers','Brothers (printed as This is an album by The Black Keys. The name of this album is Brothers. on the front cover) is the sixth studio album by American rock duo The Black Keys. Co-produced by the group, Mark Neill, and Danger Mouse, it was released on May 18, 2010 on Nonesuch Records. Brothers was the band\'s commercial breakthrough, as it sold over 73,000 copies in the United States in its first week and peaked at number three on the Billboard 200, their best performance on the chart to that point.\n\nThe album\'s lead single, \"Tighten Up\", the only track from the album produced by Danger Mouse, became their most successful single to that point, spending 10 weeks at number one on the Alternative Songs chart and becoming the group\'s first single on the Billboard Hot 100, peaking at number 87 and was later certified gold. The second single, \"Howlin\' for You\", went gold as well. In April 2012, the album was certified platinum in the US by the RIAA for shipping over one million copies. It also went double-platinum in Canada and gold in the UK. In 2011, it won three Grammy Awards, including honors for Best Alternative Music Album.','/images/vinyl/the-black-keys_brothers.jpg',34.99,19,24,24,1),
(26,'S&M2','S&M2 (stylized as S&M2) is a live album by American heavy metal band Metallica and the San Francisco Symphony. The album is a follow-up to S&M, a live collaborative album released in 1999. Like S&M, the album was recorded during a live performance in San Francisco at the Chase Center in 2019. The performance was also filmed and released theatrically on October 9, 2019.\nAt Metacritic, which assigns a normalized rating out of 100 to reviews from mainstream publications, the album received an average score of 78 based on seven reviews, indicating \"generally favorable reviews\".[16]\n\nCritics generally praised the album as a worthy successor of S&M. Paul Brannigan of Kerrang! gave the performance a perfect rating, writing that the album \"stands as a tribute both to Metallica’s growing confidence as players and composers, and an absolute vindication of their decision to revisit one of their most inspired creative outings.\"\nIn a mixed review, Andy Cush of Pitchfork felt that the album had the same issues that S&M did. Cush wrote that the songs are not suited to an orchestral accompaniment, writing \"Metallica’s best songs, intricate and ambitious though they may be, are not actually well suited for the additional orchestrating they get here, precisely because they are plenty symphonic already.\" Cush was also critical of the song selection for S&M2, noting that \"Of the 20 pieces of music here, more than half appeared in a similar form more than two decades ago on the first S&M.\"','/images/vinyl/metallica-s&m2.jpg',109.99,10,5,25,1),
(29,'Here Comes The Cowboy','Here Comes the Cowboy received polarizing reviews upon release from critics and fans. While some critics noted a musical maturity for DeMarco and the minimalist production, most critics were divided on the album\'s slower pace and lack of focus. Thomas Hobbs of NME said of the album, \"Here Comes the Cowboy suggests Mac DeMarco is ready to explore more mature themes and grow beyond the slacker image he has helped turn into a pop culture staple. This record\'s slower pace won\'t be for everybody, just as unassuming This Old Dog wasn\'t, but, should you let it, this record will transport you somewhere calm and reflective. At a time of great chaos, that sure sounds good to me.\" Rolling Stone\'s Joe Levy called the songs \"stark, meditative, lonely, and stubbornly isolated, like spending 45 minutes petting a cat. A static search for comfort.\"\n\nIn a generally mixed review, Timothy Monger of AllMusic said of the album, \"With its camera phone happy-face button cover and minimalist production, Here Comes the Cowboy is a mixed bag of a record beset by an overall aimlessness where some crafty low-key gems have to share the bus with a few inane clunkers that probably should have stayed in the vault.\" Rachel Aroesti of The Guardian noted that Here Comes the Cowboy may retain some of the disarming simplicity and emotional universality that has become DeMarco\'s trademark, but it is ultimately an album that fails to welcome the listener warmly into its world.\"','/images/vinyl/mac-demarco_here-comes-the-cowboy.jpg',24.99,35,26,28,1),
(30,'After Hours','After Hours is the fourth studio album by Canadian singer the Weeknd, released on March 20, 2020, by XO and Republic Records. Primarily produced by the Weeknd, it features a variety of producers, including DaHeala, Illangelo, Max Martin, Metro Boomin, and OPN, most of whom the Weeknd had worked with previously. The standard edition of the album has no features, although the remixes edition contains guest appearances from Chromatics and Lil Uzi Vert. Thematically, After Hours explores promiscuity, overindulgence, and self-loathing.\n\nPrior to the album\'s release, the Weeknd confirmed that After Hours would face stylistic differences to its predecessor, Starboy (2016). Music journalists have noted the album as an artistic reinvention for the Weeknd, with the introduction of new wave and dream pop influences. The artwork and aesthetic for its promotional material has been described as psychedelic and being inspired by various films, such as Casino (1995), Fear and Loathing in Las Vegas (1998), Joker, and Uncut Gems (both 2019), while its title is borrowed from the 1985 film directed by Martin Scorsese.\n\nAfter Hours was supported by four singles: \"Heartless\", \"Blinding Lights\", \"In Your Eyes\", and \"Save Your Tears\", with the first two topping the US Billboard Hot 100. Three singles reached the top five on the chart and received a platinum certification. The album\'s title track was released as a promotional single. In March 2020, After Hours broke the record for the most global pre-adds in Apple Music history, with over 1.02 million users. The album received generally positive reviews from music critics, with some naming it the Weeknd\'s best work. It debuted atop the Billboard 200, earning 444,000 album-equivalent units of which 275,000 were pure sales, marking the Weeknd\'s fourth number-one album in the US, and stayed atop the chart for four consecutive weeks. It also reached the top spot in 20 other countries, including Canada and the United Kingdom. After Hours will be promoted with the After Hours Tour, which is set to span Europe and North America.','/images/vinyl/the-weeknd_after-hours.jpg',28.99,499,27,29,2),
(31,'THE SMOKE OUT FESTIVAL PRESENTS (LTD COLOURED 180G LP)','Earl Simmons (December 18, 1970 – April 9, 2021), known by his stage name DMX (\"Dark Man X\"), was an American rapper, songwriter, and actor. He began rapping in the early 1990s and released his debut album It\'s Dark and Hell Is Hot in 1998, to both critical acclaim and commercial success, selling 251,000 copies within its first week of release.[3][4] DMX released his best-selling album, ... And Then There Was X, in 1999, which included the hit single \"Party Up (Up in Here)\". His 2003 singles \"Where the Hood At?\" and \"X Gon\' Give It to Ya\" were also commercially successful. He was the first artist to debut an album at No. 1 five times in a row on the Billboard 200 charts. Overall, DMX has sold over 74 million records worldwide.\n\nDMX was featured in films such as Belly, Romeo Must Die, Exit Wounds, Cradle 2 the Grave, and Last Hour. In 2006, he starred in the reality television series DMX: Soul of a Man, which was primarily aired on the BET cable television network. In 2003, he published a book of his memoirs entitled, E.A.R.L.: The Autobiography of DMX.','/images/vinyl/dmx-smokeout.jpg',21.99,5,28,30,2),
(32,'Future Nostalgia','Future Nostalgia is the second studio album by English singer Dua Lipa, released on 27 March 2020 by Warner Records. Lipa enlisted writers and producers such as Jeff Bhasker, Ian Kirkpatrick, Stuart Price, The Monsters & Strangerz, and Koz in order to create a \"nostalgic\" pop and disco record with influences from dance-pop and electronic music, inspired by the music that Lipa enjoyed during her childhood.\n\nThe album spawned five singles, along with the title track as a promotional single. \"Don\'t Start Now\" was released on 31 October 2019, as the album\'s lead single, attaining both critical and commercial success. The song became her first top-three entry on the Billboard Hot 100 chart. \"Physical\" and \"Break My Heart\" were released as the second and third singles, respectively, both reaching the top 10 on the UK Singles Chart. \"Hallucinate\" and a remix of \"Levitating\" featuring DaBaby were released as the fourth and fifth singles on 17 July and 1 October 2020, respectively; the latter earned the album its second top-five single on the Hot 100 and the third top-ten of Lipa\'s career, following \"New Rules\" (2017). The album was originally scheduled to be released on 3 April 2020, but was moved forward after leaking in its entirety two weeks earlier. To promote the album, Lipa is slated to embark on the Future Nostalgia Tour, commencing in September 2021.\n\nUpon its release, Future Nostalgia received widespread acclaim from music critics, many of whom praised the production and its cohesion. Commercially, the album topped the charts in thirteen countries and reached the top ten in thirty-one countries. In the United Kingdom, it peaked atop the UK Albums Chart for four non-consecutive weeks, becoming her first album to do so as well as garnering her first ever nomination for the Mercury Prize. At the 63rd Annual Grammy Awards, Future Nostalgia was nominated for Album of the Year and won Best Pop Vocal Album, whilst \"Don\'t Start Now\" was nominated for Record of the Year, Song of the Year and Best Pop Solo Performance.\n\nFuture Nostalgia was succeeded by its remix album, Club Future Nostalgia, released on 28 August 2020. A French edition of Future Nostalgia was released on 27 November 2020, which yielded the single \"Fever\". A reissue of the album, subtitled The Moonlight Edition, was released through Warner on 11 February 2021, along with its lead single, \"We\'re Good\".','/images/vinyl/dua-lipa_future-nostalgia.png',27.99,300,29,31,2),
(34,'Take Care','Take Care is the second studio album by Canadian rapper Drake. It was released on November 15, 2011 by Young Money Entertainment, Cash Money Records and Universal Republic Records. The album features guest appearances from The Weeknd, Rihanna, Kendrick Lamar, Birdman, Nicki Minaj, Rick Ross, Lil Wayne, and André 3000. Alongside prominent production from the album\'s executive producers Drake and 40, further contributors include T-Minus, Chantal Kreviazuk, Boi-1da, Illangelo, Jamie xx, Supa Dups, Just Blaze, Chase N. Cashe, and Doc McKinney.\n\nPrior to Take Care, Drake released Thank Me Later, which experienced positive critical success, but left him feeling disjointed about the album\'s musical content. Expressing a desire to reunite with 40, his long-time producer who featured in parts on Thank Me Later, the duo worked extensively on the new album once recording sessions began in 2010. Drake\'s vocals on the album feature emotional crooning, alto vocals, a guttural cadence, a melodic flow, and a larger emphasis on singing than on Thank Me Later. In comparison to his debut album, Drake revealed that the album is called Take Care because \"I get to take my time this go-round [rather than rush]\".','/images/vinyl/drake-take_care.jpg',31.99,250,30,32,1);

/*Table structure for table `vinyl_genre` */

DROP TABLE IF EXISTS `vinyl_genre`;

CREATE TABLE `vinyl_genre` (
  `vinyl_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL,
  PRIMARY KEY (`vinyl_id`,`genre_id`),
  KEY `FKb569hns5yuwwcbf84xhgnmlt9` (`genre_id`),
  KEY `FKk43pkc22s3asggahv7x0d6oa3` (`vinyl_id`),
  CONSTRAINT `FKb569hns5yuwwcbf84xhgnmlt9` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`),
  CONSTRAINT `FKk43pkc22s3asggahv7x0d6oa3` FOREIGN KEY (`vinyl_id`) REFERENCES `vinyl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `vinyl_genre` */

insert  into `vinyl_genre`(`vinyl_id`,`genre_id`) values 
(1,1),
(1,3),
(2,3),
(3,3),
(3,6),
(4,1),
(4,2),
(4,4),
(5,7),
(5,8),
(5,9),
(6,3),
(7,5),
(7,10),
(8,5),
(8,11),
(9,1),
(10,1),
(10,2),
(11,12),
(11,13),
(11,14),
(13,3),
(13,5),
(13,6),
(14,1),
(14,2),
(14,3),
(15,1),
(15,2),
(16,1),
(17,1),
(17,2),
(18,1),
(18,2),
(18,19),
(19,1),
(19,2),
(20,1),
(20,2),
(21,1),
(21,2),
(22,1),
(22,2),
(22,10),
(23,1),
(23,2),
(23,3),
(24,4),
(24,13),
(24,14),
(25,5),
(25,20),
(25,21),
(26,7),
(26,22),
(29,3),
(29,10),
(29,25),
(30,1),
(30,3),
(30,4),
(31,1),
(31,2),
(32,3),
(32,6),
(34,1),
(34,3),
(34,4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
