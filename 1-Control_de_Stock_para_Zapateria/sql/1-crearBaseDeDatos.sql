-- MySQL dump 10.13  Distrib 5.1.51, for Win32 (ia32)
--
-- Host: localhost    Database: controlstock
-- ------------------------------------------------------
-- Server version	5.1.51-community

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
-- Table structure for table `articulo`
--

DROP TABLE IF EXISTS `articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articulo` (
  `idArticulo` int(11) NOT NULL AUTO_INCREMENT,
  `tipoArticulo` int(11) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `codigoArticulo` varchar(4) NOT NULL,
  `idProveedor` int(11) DEFAULT NULL,
  `precioDeVenta` double DEFAULT NULL,
  PRIMARY KEY (`idArticulo`),
  UNIQUE KEY `idArticulo` (`idArticulo`),
  KEY `FKBA9B58FB35D99639` (`idProveedor`),
  CONSTRAINT `FKBA9B58FB35D99639` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `color` (
  `idColor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `codigoColor` varchar(255) NOT NULL,
  PRIMARY KEY (`idColor`),
  UNIQUE KEY `idColor` (`idColor`),
  UNIQUE KEY `nombre` (`nombre`),
  UNIQUE KEY `codigoColor` (`codigoColor`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compra` (
  `idCompra` int(11) NOT NULL AUTO_INCREMENT,
  `numero` int(11) DEFAULT NULL,
  `codigoCompra` int(11) NOT NULL,
  `costoUnitario` double DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `codigoDeBarrasGenerado` bit(1) DEFAULT NULL,
  `vendida` bit(1) DEFAULT NULL,
  `idPrecioDeVenta` int(11) DEFAULT NULL,
  `idArticulo` int(11) NOT NULL,
  `precioDeVenta` double DEFAULT NULL,
  `precioDePorMayor` double DEFAULT NULL,
  `idColor` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCompra`),
  UNIQUE KEY `idCompra` (`idCompra`),
  UNIQUE KEY `codigoCompra` (`codigoCompra`),
  KEY `FK78A4219E3694E38` (`idPrecioDeVenta`),
  KEY `FK78A4219E81E16599` (`idArticulo`),
  KEY `FK78A4219E914425C3` (`idColor`),
  CONSTRAINT `FK78A4219E3694E38` FOREIGN KEY (`idPrecioDeVenta`) REFERENCES `precio` (`idPrecio`),
  CONSTRAINT `FK78A4219E81E16599` FOREIGN KEY (`idArticulo`) REFERENCES `articulo` (`idArticulo`),
  CONSTRAINT `FK78A4219E914425C3` FOREIGN KEY (`idColor`) REFERENCES `color` (`idColor`)
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `perfilusuario`
--

DROP TABLE IF EXISTS `perfilusuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfilusuario` (
  `idPerfilUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPerfilUsuario`),
  UNIQUE KEY `idPerfilUsuario` (`idPerfilUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `precio`
--

DROP TABLE IF EXISTS `precio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `precio` (
  `idPrecio` int(11) NOT NULL AUTO_INCREMENT,
  `desdeNumero` int(11) NOT NULL,
  `hastaNumero` int(11) NOT NULL,
  `valor` double NOT NULL,
  `idArticuloCorrespondiente` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPrecio`),
  UNIQUE KEY `idPrecio` (`idPrecio`),
  KEY `FK8EF99366690022C1` (`idArticuloCorrespondiente`),
  CONSTRAINT `FK8EF99366690022C1` FOREIGN KEY (`idArticuloCorrespondiente`) REFERENCES `articulo` (`idArticulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedor` (
  `idProveedor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `codigoFabricante` varchar(4) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idProveedor`),
  UNIQUE KEY `idProveedor` (`idProveedor`),
  UNIQUE KEY `codigoFabricante` (`codigoFabricante`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `relacioncompracodigounico`
--

DROP TABLE IF EXISTS `relacioncompracodigounico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relacioncompracodigounico` (
  `idRelacionCompraCodigoUnico` int(11) NOT NULL AUTO_INCREMENT,
  `codigoUnico` varchar(255) NOT NULL,
  `idCompra` int(11) NOT NULL,
  PRIMARY KEY (`idRelacionCompraCodigoUnico`),
  UNIQUE KEY `idRelacionCompraCodigoUnico` (`idRelacionCompraCodigoUnico`),
  UNIQUE KEY `codigoUnico` (`codigoUnico`),
  KEY `FK214DE1989741839F` (`idCompra`),
  CONSTRAINT `FK214DE1989741839F` FOREIGN KEY (`idCompra`) REFERENCES `compra` (`idCompra`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tarea`
--

DROP TABLE IF EXISTS `tarea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarea` (
  `idTarea` int(11) NOT NULL AUTO_INCREMENT,
  `tipoArticulo` int(11) NOT NULL,
  `numero` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  PRIMARY KEY (`idTarea`),
  UNIQUE KEY `idTarea` (`idTarea`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipopago`
--

DROP TABLE IF EXISTS `tipopago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipopago` (
  `idTipoPago` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `sigla` varchar(255) NOT NULL,
  `recargo` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTipoPago`),
  UNIQUE KEY `idTipoPago` (`idTipoPago`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `nombrePersona` varchar(255) NOT NULL,
  `apellidoPersona` varchar(255) NOT NULL,
  `idPerfilUsuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `idUsuario` (`idUsuario`),
  KEY `FK5B4D8B0E98B72163` (`idPerfilUsuario`),
  CONSTRAINT `FK5B4D8B0E98B72163` FOREIGN KEY (`idPerfilUsuario`) REFERENCES `perfilusuario` (`idPerfilUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venta` (
  `idVenta` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `precioDeVenta` double NOT NULL,
  `descuento` int(11) NOT NULL,
  `idCompra` int(11) NOT NULL,
  `idTipoPago` int(11) DEFAULT NULL,
  `precioDeVentaModificado` double DEFAULT NULL,
  PRIMARY KEY (`idVenta`),
  UNIQUE KEY `idVenta` (`idVenta`),
  UNIQUE KEY `idCompra` (`idCompra`),
  KEY `FK4EB7A2C281CE79D` (`idTipoPago`),
  KEY `FK4EB7A2C9741839F` (`idCompra`),
  CONSTRAINT `FK4EB7A2C281CE79D` FOREIGN KEY (`idTipoPago`) REFERENCES `tipopago` (`idTipoPago`),
  CONSTRAINT `FK4EB7A2C9741839F` FOREIGN KEY (`idCompra`) REFERENCES `compra` (`idCompra`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-04-21  0:23:59
