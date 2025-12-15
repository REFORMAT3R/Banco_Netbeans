-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 14-12-2025 a las 20:06:06
-- Versión del servidor: 8.4.7
-- Versión de PHP: 8.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `banco`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `correo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `edad` int NOT NULL,
  `dni` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `direccion` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `codigoCliente` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`codigoCliente`),
  UNIQUE KEY `dni` (`dni`),
  UNIQUE KEY `unique_correo` (`correo`),
  UNIQUE KEY `unique_telefono` (`telefono`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`nombre`, `apellido`, `telefono`, `correo`, `edad`, `dni`, `direccion`, `codigoCliente`) VALUES
('Maria', 'Lopez Ludeña', '912345678', 'maria@mail.com', 28, '20000001', 'Jr. Flores 456', 'CLI001'),
('Carlos', 'Ruiz Motta', '922334455', 'carlos@mail.com', 35, '20000002', 'Av. Arequipa 880', 'CLI002'),
('Elena', 'Diaz Yucra', '933445566', 'elena@mail.com', 42, '20000003', 'Calle Lima 202', 'CLI003');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
CREATE TABLE IF NOT EXISTS `cuenta` (
  `codigoCuenta` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `codigoCliente` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `saldo` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`codigoCuenta`),
  KEY `codigoCliente` (`codigoCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`codigoCuenta`, `codigoCliente`, `saldo`) VALUES
('CTA00000001', 'CLI001', 0),
('CTA00000002', 'CLI002', 0),
('CTA00000003', 'CLI003', 0),
('CTA00000004', 'CLI001', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

DROP TABLE IF EXISTS `empleado`;
CREATE TABLE IF NOT EXISTS `empleado` (
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `correo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `edad` int NOT NULL,
  `dni` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `direccion` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `codigoEmpleado` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`codigoEmpleado`),
  UNIQUE KEY `dni` (`dni`),
  UNIQUE KEY `unique_correo` (`correo`),
  UNIQUE KEY `unique_telefono` (`telefono`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`nombre`, `apellido`, `telefono`, `correo`, `edad`, `dni`, `direccion`, `codigoEmpleado`) VALUES
('Super', 'Admin', '999999999', 'admin@banco.com', 30, '00000001', 'Calle Principal 123', 'EMP001'),
('Juan', 'Perez Vargas', '987654321', 'juan.perez@banco.com', 25, '10000001', 'Av. Siempre Viva 742', 'EMP002'),
('Ana', 'Gomez Guevara', '987123456', 'ana.gomez@banco.com', 27, '10000002', 'Jr. Los Pinos 101', 'EMP003');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transaccion`
--

DROP TABLE IF EXISTS `transaccion`;
CREATE TABLE IF NOT EXISTS `transaccion` (
  `idTransaccion` int NOT NULL AUTO_INCREMENT,
  `codigoCuenta` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `codigoCuentaDestino` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `codigoEmpleado` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `monto` double NOT NULL,
  `fechaHora` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `tipo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idTransaccion`),
  KEY `codigoCuenta` (`codigoCuenta`),
  KEY `codigoCuentaDestino` (`codigoCuentaDestino`),
  KEY `codigoEmpleado` (`codigoEmpleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombreUsuario` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `contrasenia` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `tipo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `codigoEmpleado` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `codigoCliente` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `nombreUsuario` (`nombreUsuario`),
  KEY `fk_usuario_empleado` (`codigoEmpleado`),
  KEY `fk_usuario_cliente` (`codigoCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombreUsuario`, `contrasenia`, `estado`, `tipo`, `codigoEmpleado`, `codigoCliente`) VALUES
(1, 'admin', 'admin123', 1, 'ADMIN', 'EMP001', NULL),
(2, 'juan', 'juan123', 1, 'EMPLEADO', 'EMP002', NULL),
(3, 'ana', 'ana123', 1, 'EMPLEADO', 'EMP003', NULL),
(4, 'maria', 'maria123', 1, 'CLIENTE', NULL, 'CLI001'),
(5, 'carlos', 'carlos123', 1, 'CLIENTE', NULL, 'CLI002'),
(6, 'elena', 'elena123', 1, 'CLIENTE', NULL, 'CLI003');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD CONSTRAINT `cuenta_ibfk_1` FOREIGN KEY (`codigoCliente`) REFERENCES `cliente` (`codigoCliente`);

--
-- Filtros para la tabla `transaccion`
--
ALTER TABLE `transaccion`
  ADD CONSTRAINT `transaccion_ibfk_1` FOREIGN KEY (`codigoCuenta`) REFERENCES `cuenta` (`codigoCuenta`),
  ADD CONSTRAINT `transaccion_ibfk_2` FOREIGN KEY (`codigoCuentaDestino`) REFERENCES `cuenta` (`codigoCuenta`),
  ADD CONSTRAINT `transaccion_ibfk_3` FOREIGN KEY (`codigoEmpleado`) REFERENCES `empleado` (`codigoEmpleado`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_cliente` FOREIGN KEY (`codigoCliente`) REFERENCES `cliente` (`codigoCliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuario_empleado` FOREIGN KEY (`codigoEmpleado`) REFERENCES `empleado` (`codigoEmpleado`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
