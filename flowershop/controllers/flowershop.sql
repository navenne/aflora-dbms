-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-12-2021 a las 02:04:35
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `flowershop`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bill`
--
CREATE DATABASE flowershop;

USE flowershop;

CREATE TABLE `bill` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `customerId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `bill`
--

INSERT INTO `bill` (`id`, `date`, `customerId`) VALUES
(1, '2021-12-09', 1),
(2, '2021-12-09', 1),
(4, '2021-12-09', 1),
(5, '2021-12-09', 2),
(6, '2021-12-09', 1),
(7, '2021-12-09', 1),
(8, '2021-12-09', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bouquet`
--

CREATE TABLE `bouquet` (
  `id` int(11) NOT NULL,
  `line_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `bouquet`
--

INSERT INTO `bouquet` (`id`, `line_id`) VALUES
(1, 3),
(2, 4),
(3, 5),
(4, 6),
(5, 7),
(6, 8),
(7, 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `phoneNumber` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `customer`
--

INSERT INTO `customer` (`id`, `name`, `phoneNumber`) VALUES
(1, 'Marta', '123456789'),
(2, 'Maria', '123456789');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flower`
--

CREATE TABLE `flower` (
  `id` int(11) NOT NULL,
  `species` varchar(30) NOT NULL,
  `color` varchar(30) NOT NULL,
  `price` float NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `flower`
--

INSERT INTO `flower` (`id`, `species`, `color`, `price`, `stock`) VALUES
(1, 'rosa', 'roja', 2.5, 10),
(2, 'clavel', 'blanco', 3.1, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flowerbouquet`
--

CREATE TABLE `flowerbouquet` (
  `bouquetId` int(11) NOT NULL,
  `flowerId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `flowerbouquet`
--

INSERT INTO `flowerbouquet` (`bouquetId`, `flowerId`, `quantity`) VALUES
(2, 1, 4),
(2, 2, 5),
(3, 1, 2),
(3, 2, 2),
(4, 1, 3),
(4, 2, 3),
(5, 1, 1),
(5, 2, 2),
(6, 1, 2),
(6, 2, 1),
(7, 1, 2),
(7, 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `line`
--

CREATE TABLE `line` (
  `id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `subtotal` float NOT NULL,
  `billId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `line`
--

INSERT INTO `line` (`id`, `quantity`, `subtotal`, `billId`) VALUES
(1, 1, 10.6, 1),
(3, 1, 8.1, 4),
(4, 1, 25.5, 5),
(5, 1, 11.2, 5),
(6, 1, 16.8, 6),
(7, 1, 8.7, 6),
(8, 1, 8.1, 7),
(9, 1, 8.1, 8);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_bill_client` (`customerId`);

--
-- Indices de la tabla `bouquet`
--
ALTER TABLE `bouquet`
  ADD PRIMARY KEY (`id`,`line_id`),
  ADD KEY `fk_bouquet_line1` (`line_id`);

--
-- Indices de la tabla `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `flower`
--
ALTER TABLE `flower`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `flowerbouquet`
--
ALTER TABLE `flowerbouquet`
  ADD PRIMARY KEY (`bouquetId`,`flowerId`),
  ADD KEY `fk_bouquet_has_flower_flower1` (`flowerId`);

--
-- Indices de la tabla `line`
--
ALTER TABLE `line`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_line_bill1` (`billId`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `bill`
--
ALTER TABLE `bill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `bouquet`
--
ALTER TABLE `bouquet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `flower`
--
ALTER TABLE `flower`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `line`
--
ALTER TABLE `line`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `fk_bill_client` FOREIGN KEY (`customerId`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `bouquet`
--
ALTER TABLE `bouquet`
  ADD CONSTRAINT `fk_bouquet_line1` FOREIGN KEY (`line_id`) REFERENCES `line` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `flowerbouquet`
--
ALTER TABLE `flowerbouquet`
  ADD CONSTRAINT `fk_bouquet_has_flower_bouquet1` FOREIGN KEY (`bouquetId`) REFERENCES `bouquet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_bouquet_has_flower_flower1` FOREIGN KEY (`flowerId`) REFERENCES `flower` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `line`
--
ALTER TABLE `line`
  ADD CONSTRAINT `fk_line_bill1` FOREIGN KEY (`billId`) REFERENCES `bill` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
