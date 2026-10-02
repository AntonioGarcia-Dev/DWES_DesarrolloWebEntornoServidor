-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         12.3.3-MariaDB - MariaDB Server
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.20.0.7320
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para gestion_nominas
CREATE DATABASE IF NOT EXISTS `gestion_nominas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `gestion_nominas`;

-- Volcando estructura para tabla gestion_nominas.empleados
CREATE TABLE IF NOT EXISTS `empleados` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `sexo` char(1) NOT NULL,
  `categoria` int(11) DEFAULT 1 CHECK (`categoria` >= 1 and `categoria` <= 10),
  `anyos` int(11) DEFAULT 0 CHECK (`anyos` >= 0),
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla gestion_nominas.empleados: ~5 rows (aproximadamente)
INSERT INTO `empleados` (`dni`, `nombre`, `sexo`, `categoria`, `anyos`) VALUES
	('11111111C', 'Alan Turing', 'M', 9, 5),
	('12345678A', 'Linus Torvalds', 'M', 5, 10),
	('32000031R', 'Ada Lovelace', 'F', 9, 2),
	('32000032G', 'James Cosling', 'M', 5, 4),
	('87654321B', 'Margaret Hamilton', 'F', 7, 15);

-- Volcando estructura para tabla gestion_nominas.nominas
CREATE TABLE IF NOT EXISTS `nominas` (
  `dni` varchar(9) NOT NULL,
  `sueldo` int(11) DEFAULT NULL,
  PRIMARY KEY (`dni`),
  CONSTRAINT `1` FOREIGN KEY (`dni`) REFERENCES `empleados` (`dni`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla gestion_nominas.nominas: ~5 rows (aproximadamente)
INSERT INTO `nominas` (`dni`, `sueldo`) VALUES
	('11111111C', 235000),
	('12345678A', 180000),
	('32000031R', 220000),
	('32000032G', 150000),
	('87654321B', 245000);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
