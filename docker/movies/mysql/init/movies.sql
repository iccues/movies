-- MySQL dump 10.13  Distrib 8.3.0, for Win64 (x86_64)
--
-- Host: localhost    Database: movies
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `mid` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `introduction` text,
  PRIMARY KEY (`mid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,'BanG Dream! Ave Mujica','「我说过了吧？请你们将剩余的人生交给我」 丰川祥子招募组建的乐队・Ave Mujica取得了演唱会和媒体曝光等商业上的成功。 少女失去了曾发誓命运与共的友人，又失去了自己出生长大的家庭。 她为什么要背负他人的一生，将乐队坚持下去呢？将过往与真容都以假面遮掩，今夜亦降临于完美的箱庭。'),(2,'金牌得主 (舞冰的祈愿)','在滑冰之路上受挫的青年·明浦路司，与憧憬花样滑冰的少女·结束祈相遇。司被祈对滑冰的执着打动，主动担任了她的教练。天赋不断绽放的祈，作为指导者不断成长的司。二人结成搭档，目标是成为光荣的“金牌得主”！'),(3,'群花绽放彷如修罗',NULL),(4,'赛马娘 芦毛灰姑娘 Part.1','于《周刊YOUNG JUMP》火爆连载中，总销量突破650万部！！以跨媒体作品《赛马娘》中的角色·小栗帽为主角的漫画，终于改编为动画！！笠松特雷森学园，这所衰颓的地方赛区学园，迎来了一名赛马娘，其名为小栗帽。她以压倒性的奔跑，颠覆所有常识。这名终将被称为“怪物”的灰色少女，此刻开始书写全新的传说……驰骋青春的灰姑娘故事，终于开跑！！ ');
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `showtime`
--

DROP TABLE IF EXISTS `showtime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `showtime` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `mid` int NOT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `price` int NOT NULL,
  `totalSeats` int NOT NULL,
  `bookedSeats` int NOT NULL,
  `cinemaName` varchar(100) DEFAULT NULL,
  `hallName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `showtime`
--

LOCK TABLES `showtime` WRITE;
/*!40000 ALTER TABLE `showtime` DISABLE KEYS */;
INSERT INTO `showtime` VALUES (1,1,'2025-04-11 21:00:00','2025-04-11 23:00:00',30,50,1,'Cinema1','Hall1'),(2,1,'2025-04-11 21:00:00','2025-04-11 23:00:00',30,50,0,'Cinema1','Hall2'),(3,2,'2025-04-11 21:00:00','2025-04-11 23:00:00',30,50,0,'Cinema1','Hall3');
/*!40000 ALTER TABLE `showtime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `tid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `sid` int NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,20,1);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `uid` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `passwordHash` varchar(255) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'root','$2a$10$rjDW93hkwsYG4oko/fl9O.KZLYzAWICI.IksBH3xzwQdGGSCZ.oeu',1),(2,'root1','$2a$10$YApjnVqJiQooxTlyde4fYunK2iN1XSDCxhBBJ4HRjtK2ZLFTINp5.',1),(3,'root2','$2a$10$tC0NdemGH2xpqkEX566YC.zBDzp8tsqc7H.5YLWi3sujEo5XAkfVu',1),(4,'u1','$2a$10$HFxuB3FF1sXlSncpKJEM9e/6vGZKPTKBg8Z2W92f7gNkKowS9aLni',0),(5,'u2','$2a$10$0rcUmEKZ3ApbF9uMkDCeOuDwkO0ePx10rTq/Snwhs5Zj7y6CHSH4O',0),(6,'u3','$2a$10$rP6e7Ivf0Cvpk.FzJ5MIXuOl9d44AP4ZM8a3ae4i2MLSFbgt3Od7a',0),(7,'u4','$2a$10$3X5aSJa1kf63LeI3sdApD.kT7h.NnVDvtIYHCX0k62WMQKj.MhdsK',0),(8,'u5','$2a$10$tWkYwtntDxj2IvBH5ZqzyubM7ZjtPkwIe2NUMfRVJNHHR3bb7wGN2',0),(9,'u6','$2a$10$YNUboecXF1a36HyZ/sD92ulbcdkrzbBK12nInBlX23WXCfe3d.cGK',0),(10,'u7','$2a$10$ctZ/RvCGXP8XRPM2hfCx0Oi.xQJnzYxvwtB2rW03NQNIvqndUGqe6',0),(11,'u8','$2a$10$RE07YDmGTJknYw1xvcUR3e39qr14CixY9YYTe3hQwg0jw5LDG7Gti',0),(12,'u9','$2a$10$ldkhViV2FczOqjFVr6/YseNKW76Ae5WtcdeCgdET8pPQAJQpno7hm',0),(13,'u10','$2a$10$4ahf17YpQUKJ.M.E6V8iH.G8P.xrljmWUPlmrPmB6cdnGlgk4Mroa',0),(14,'u11','$2a$10$XM5WaQttHcaDy.3rbOOHU.VQP2YM2ckVJgYdrRrdn2YVr1Lv2ruP.',0),(20,'u20','$2a$10$rdX.bVVWY6I9yWcKcH1brOQgsYEWzJiV77tdCjfgIdud5rkpRt2F2',0),(21,'u21','$2a$10$S4ueF4REpz7wJ/ZK/19NCeTfOJhGBnNA8VDiH4UwDlHOanQtAd6bu',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-16 13:02:51
