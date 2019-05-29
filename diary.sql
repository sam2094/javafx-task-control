CREATE DATABASE  IF NOT EXISTS `diary` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `diary`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: diary
-- ------------------------------------------------------
-- Server version	5.5.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (13,'Java'),(18,'Sport'),(22,'Musical instrument'),(24,'Pyton'),(25,'English'),(26,'Box');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `remaining_days` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT 'Not Done',
  `category_id` int(11) DEFAULT NULL,
  `active` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (8,'OOP','2019-02-28',45,'Done',1,0),(9,'AAA','2019-02-28',5,'Done',1,0),(10,'OOP','2019-02-28',20,'Done',13,0),(11,'edd','2019-02-28',61,'Done',13,0),(12,'dff','2019-02-28',51,'Done',13,0),(13,'uvw','2019-02-28',51,'Done',19,0),(14,'dd','2019-02-28',1,'Done',18,0),(15,'Pres','2019-02-28',31,'Done',18,0),(16,'Guitar','2019-02-28',31,'Done',22,0),(17,'bvyhq','2019-02-28',515,'Done',13,0),(18,'uhgi','2019-02-28',51,'Done',22,0),(19,'Pianino','2019-02-28',15,'Done',22,0),(20,'pres','2019-02-28',31,'Done',18,0),(21,'Sport','2019-02-28',31,'Done',18,0),(22,'Learn Pyton','2019-02-28',31,'Done',24,1),(23,'idj','2019-03-03',65,'Done',23,0),(24,'Learn English','2019-03-03',20,'Done',25,0),(25,'Piano','2019-03-03',60,'Done',22,1),(26,'Learn to box','2019-03-03',365,'Done',26,1),(27,'Pump your biceps','2019-03-03',62,'Done',18,1),(28,'Learn String','2019-03-03',20,'Not done',13,1),(29,'BBB','2019-03-03',43445,'Done',13,0),(30,'AAA','2019-03-03',555,'Not Done',24,1),(31,'AAA','2019-03-03',444,'Not Done',26,1);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-03 16:19:15
