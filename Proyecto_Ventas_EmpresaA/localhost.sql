-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 27-01-2026 a las 03:06:14
-- Versión del servidor: 5.7.24
-- Versión de PHP: 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `crud_java`
--
CREATE DATABASE IF NOT EXISTS `crud_java` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `crud_java`;
--
-- Base de datos: `proyecto_bdii`
--
CREATE DATABASE IF NOT EXISTS `proyecto_bdii` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `proyecto_bdii`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `IDCLIENTE` varchar(2) NOT NULL,
  `NOMBRECLIENTE` varchar(100) DEFAULT NULL,
  `APELLIDOCLIENTE` varchar(100) DEFAULT NULL,
  `TELEFONO` varchar(10) DEFAULT NULL,
  `DIRECCION` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`IDCLIENTE`, `NOMBRECLIENTE`, `APELLIDOCLIENTE`, `TELEFONO`, `DIRECCION`) VALUES
('B3', 'Carlos', 'Hernandez', '9724568834', 'Ixtaltepec'),
('C1', 'Marcos', 'Perez', '9712343254', 'IXTEPEC'),
('C2', 'Marce', 'AG', '2215888443', 'Guevea de Humboldt');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `FK_IDVENTA` varchar(2) NOT NULL,
  `FK_IDCLIENTE` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleventa`
--

CREATE TABLE `detalleventa` (
  `IDDETALLE` varchar(25) NOT NULL,
  `IDVENTA` varchar(15) DEFAULT NULL,
  `IDPRODUCTO` varchar(10) DEFAULT NULL,
  `CANTIDAD` int(11) NOT NULL,
  `PRECIO_UNITARIO` decimal(10,2) DEFAULT NULL,
  `SUBTOTAL` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `detalleventa`
--

INSERT INTO `detalleventa` (`IDDETALLE`, `IDVENTA`, `IDPRODUCTO`, `CANTIDAD`, `PRECIO_UNITARIO`, `SUBTOTAL`) VALUES
('D1-V1', 'V1', 'P1', 1, '13500.00', 13500),
('D1-V2', 'V2', 'P1', 2, '13500.00', 27000),
('V3-D1', 'V3', 'A2', 1, '6000.00', 6000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `incluye`
--

CREATE TABLE `incluye` (
  `FK_IDPRODUCTO` varchar(2) NOT NULL,
  `FK_IDVENTA` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `IDPRODUCTO` varchar(2) NOT NULL,
  `NOMBREPRODUCTO` varchar(100) DEFAULT NULL,
  `DESCRIPCION` varchar(100) DEFAULT NULL,
  `PRECIO` decimal(10,2) DEFAULT NULL,
  `STOCK` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`IDPRODUCTO`, `NOMBREPRODUCTO`, `DESCRIPCION`, `PRECIO`, `STOCK`) VALUES
('A1', 'iPhone 11 Pro', 'A13 128 GB', '4500.00', 7),
('A2', 'iPhone 12 Proo', 'A14 256 GB', '6000.00', 5),
('A3', 'iPhone 12 Plus', 'A14 500 GB', '7000.00', 10),
('P1', 'Laptop HP', 'intel i5, 8GB RAM, SSD 256GB', '13500.00', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `IDVENTA` varchar(2) NOT NULL,
  `FECHAVENTA` date DEFAULT NULL,
  `TOTAL` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`IDVENTA`, `FECHAVENTA`, `TOTAL`) VALUES
('V1', '2025-11-27', '13500.00'),
('V2', '2025-11-27', '27000.00'),
('V3', '2026-01-20', '6000.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_venta` int(11) NOT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  `total` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_catalogo_publico`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_catalogo_publico` (
`idproducto` varchar(2)
,`nombreproducto` varchar(100)
,`precio` decimal(10,2)
,`stock` int(11)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_inventario`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_inventario` (
`nombreproducto` varchar(100)
,`stock` int(11)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_reporte_ventas`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_reporte_ventas` (
`producto` varchar(100)
,`total` decimal(32,0)
,`ingreso_total` decimal(42,2)
);

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_catalogo_publico`
--
DROP TABLE IF EXISTS `vista_catalogo_publico`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_catalogo_publico`  AS SELECT `producto`.`IDPRODUCTO` AS `idproducto`, `producto`.`NOMBREPRODUCTO` AS `nombreproducto`, `producto`.`PRECIO` AS `precio`, `producto`.`STOCK` AS `stock` FROM `producto` WHERE (`producto`.`STOCK` > 0)  ;

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_inventario`
--
DROP TABLE IF EXISTS `vista_inventario`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_inventario`  AS SELECT `producto`.`NOMBREPRODUCTO` AS `nombreproducto`, `producto`.`STOCK` AS `stock` FROM `producto``producto`  ;

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_reporte_ventas`
--
DROP TABLE IF EXISTS `vista_reporte_ventas`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_reporte_ventas`  AS SELECT `p`.`NOMBREPRODUCTO` AS `producto`, sum(`d`.`CANTIDAD`) AS `total`, sum((`d`.`CANTIDAD` * `d`.`PRECIO_UNITARIO`)) AS `ingreso_total` FROM (`detalleventa` `d` join `producto` `p` on((`d`.`IDPRODUCTO` = `p`.`IDPRODUCTO`))) GROUP BY `p`.`NOMBREPRODUCTO``NOMBREPRODUCTO`  ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`IDCLIENTE`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`FK_IDVENTA`,`FK_IDCLIENTE`),
  ADD KEY `COMPRA_FK_IDCLIENTE` (`FK_IDCLIENTE`);

--
-- Indices de la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD PRIMARY KEY (`IDDETALLE`),
  ADD KEY `IDVENTA` (`IDVENTA`),
  ADD KEY `IDPRODUCTO` (`IDPRODUCTO`);

--
-- Indices de la tabla `incluye`
--
ALTER TABLE `incluye`
  ADD PRIMARY KEY (`FK_IDPRODUCTO`,`FK_IDVENTA`),
  ADD KEY `INCLUYE_FK_IDVENTA` (`FK_IDVENTA`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`IDPRODUCTO`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`IDVENTA`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id_venta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `COMPRA_FK_IDCLIENTE` FOREIGN KEY (`FK_IDCLIENTE`) REFERENCES `cliente` (`IDCLIENTE`) ON DELETE CASCADE,
  ADD CONSTRAINT `COMPRA_FK_IDVENTA` FOREIGN KEY (`FK_IDVENTA`) REFERENCES `venta` (`IDVENTA`) ON DELETE CASCADE;

--
-- Filtros para la tabla `incluye`
--
ALTER TABLE `incluye`
  ADD CONSTRAINT `INCLUYE_FK_IDPRODUCTO` FOREIGN KEY (`FK_IDPRODUCTO`) REFERENCES `producto` (`IDPRODUCTO`) ON DELETE CASCADE,
  ADD CONSTRAINT `INCLUYE_FK_IDVENTA` FOREIGN KEY (`FK_IDVENTA`) REFERENCES `venta` (`IDVENTA`) ON DELETE CASCADE;
--
-- Base de datos: `sistemapedidos`
--
CREATE DATABASE IF NOT EXISTS `sistemapedidos` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `sistemapedidos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `id_pedido` int(11) NOT NULL,
  `cliente` varchar(50) DEFAULT NULL,
  `producto` varchar(50) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `hilo_proceso` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`id_pedido`, `cliente`, `producto`, `cantidad`, `estado`, `hilo_proceso`) VALUES
(1, 'Juan Perez', 'Laptop', 1, 'PROCESADO', 'Hilo-Alfa'),
(2, 'Maria Lopez', 'Mouse', 2, 'PROCESADO', 'Hilo-Gamma'),
(3, 'Carlos Ruiz', 'Monitor', 1, 'PROCESADO', 'Hilo-Beta'),
(4, 'Ana Gomez', 'Teclado', 3, 'PROCESADO', 'Hilo-Alfa'),
(5, 'ANA', 'Silla', 4, 'PROCESADO', 'Hilo-Alfa'),
(6, 'FLORENTINO', 'Mouse', 3, 'PROCESADO', 'Hilo-Alfa'),
(7, 'Marcos', 'Celular', 2, 'PROCESADO', 'Hilo-Alfa');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id_pedido`);
--
-- Base de datos: `tienda`
--
CREATE DATABASE IF NOT EXISTS `tienda` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `tienda`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `IDCLIENTE` varchar(2) NOT NULL,
  `NOMBRECLIENTE` varchar(100) DEFAULT NULL,
  `APELLIDOCLIENTE` varchar(100) DEFAULT NULL,
  `TELEFONO` varchar(10) DEFAULT NULL,
  `DIRECCION` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`IDCLIENTE`, `NOMBRECLIENTE`, `APELLIDOCLIENTE`, `TELEFONO`, `DIRECCION`) VALUES
('C1', 'Ana María', 'González', '5551234567', 'Calle 10, Oaxaca'),
('C2', 'Luis Angel', 'Perez Gomez', '9711224659', 'Col. Laureles'),
('C3', 'José', 'Espinoza Gutierrez', '9711456790', 'Av. 16 Septiembre');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL,
  `codigo_cliente` varchar(10) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `codigo_cliente`, `nombre`, `direccion`, `telefono`, `correo`) VALUES
(1, 'C001', 'YASIR', 'IXTALTEPEC', '124545668', 'yasirguzman98@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `FK_IDVENTA` varchar(2) NOT NULL,
  `FK_IDCLIENTE` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `compra`
--

INSERT INTO `compra` (`FK_IDVENTA`, `FK_IDCLIENTE`) VALUES
('V1', 'C1'),
('V2', 'C2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleventa`
--

CREATE TABLE `detalleventa` (
  `id_detalle` int(11) NOT NULL,
  `id_venta` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `incluye`
--

CREATE TABLE `incluye` (
  `FK_IDPRODUCTO` varchar(2) NOT NULL,
  `FK_IDVENTA` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `incluye`
--

INSERT INTO `incluye` (`FK_IDPRODUCTO`, `FK_IDVENTA`) VALUES
('P1', 'V1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `IDPRODUCTO` varchar(2) NOT NULL,
  `NOMBREPRODUCTO` varchar(100) DEFAULT NULL,
  `DESCRIPCION` varchar(100) DEFAULT NULL,
  `PRECIO` decimal(10,2) DEFAULT NULL,
  `STOCK` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`IDPRODUCTO`, `NOMBREPRODUCTO`, `DESCRIPCION`, `PRECIO`, `STOCK`) VALUES
('P1', 'Laptop HP', 'intel i5, 8GB RAM, SSD 256GB', '13500.00', 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL,
  `codigo_producto` varchar(10) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `precio` decimal(10,2) NOT NULL,
  `stock` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_producto`, `codigo_producto`, `nombre`, `descripcion`, `precio`, `stock`) VALUES
(1, 'P001', 'TECLADO', 'gamer, inalambrico, 3567mhz, dpi configurable', '1000.00', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `IDVENTA` varchar(2) NOT NULL,
  `FECHAVENTA` date DEFAULT NULL,
  `TOTAL` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`IDVENTA`, `FECHAVENTA`, `TOTAL`) VALUES
('V1', '2025-10-13', '349.99'),
('V2', '2024-11-08', '799.99'),
('V3', '2025-08-15', '5000.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_venta` int(11) NOT NULL,
  `codigo_venta` varchar(10) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total` decimal(10,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id_venta`, `codigo_venta`, `id_cliente`, `fecha`, `total`) VALUES
(1, 'V001', 1, '2025-11-07 04:40:29', '5000.00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`IDCLIENTE`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `codigo_cliente` (`codigo_cliente`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`FK_IDVENTA`,`FK_IDCLIENTE`),
  ADD KEY `COMPRA_FK_IDCLIENTE` (`FK_IDCLIENTE`);

--
-- Indices de la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `id_venta` (`id_venta`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `incluye`
--
ALTER TABLE `incluye`
  ADD PRIMARY KEY (`FK_IDPRODUCTO`,`FK_IDVENTA`),
  ADD KEY `INCLUYE_FK_IDVENTA` (`FK_IDVENTA`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`IDPRODUCTO`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_producto`),
  ADD UNIQUE KEY `codigo_producto` (`codigo_producto`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`IDVENTA`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id_venta`),
  ADD UNIQUE KEY `codigo_venta` (`codigo_venta`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `COMPRA_FK_IDCLIENTE` FOREIGN KEY (`FK_IDCLIENTE`) REFERENCES `cliente` (`IDCLIENTE`) ON DELETE CASCADE,
  ADD CONSTRAINT `COMPRA_FK_IDVENTA` FOREIGN KEY (`FK_IDVENTA`) REFERENCES `venta` (`IDVENTA`) ON DELETE CASCADE;

--
-- Filtros para la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD CONSTRAINT `detalleventa_ibfk_1` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id_venta`),
  ADD CONSTRAINT `detalleventa_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`);

--
-- Filtros para la tabla `incluye`
--
ALTER TABLE `incluye`
  ADD CONSTRAINT `INCLUYE_FK_IDPRODUCTO` FOREIGN KEY (`FK_IDPRODUCTO`) REFERENCES `producto` (`IDPRODUCTO`) ON DELETE CASCADE,
  ADD CONSTRAINT `INCLUYE_FK_IDVENTA` FOREIGN KEY (`FK_IDVENTA`) REFERENCES `venta` (`IDVENTA`) ON DELETE CASCADE;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
