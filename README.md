# 🚀 Repositorio de Proyectos Java

Este repositorio aloja dos sistemas independientes desarrollados con arquitectura MVC y persistencia en MySQL.

---

## 1. 🛒 Sistema Profesional de Ventas (Empresa A)
Ubicado en la carpeta: `/Sistema_Ventas_EmpresaA`
* **Tecnología**: Maven.
* **Descripción**: Gestión de inventario de productos (Electrónicos, Laptops, etc.).
* **Estado**: CRUD completamente funcional con interfaz Swing.

## 2. 📦 Sistema de Pedidos Multihilo (Empresa B)
Ubicado en la carpeta: `/Sistema_Pedidos_EmpresaB`
* **Tecnología**: Java Estándar con Concurrencia.
* **Descripción**: Manejo de solicitudes mediante hilos para optimizar procesos logísticos.
* **Datos de Prueba**: La base de datos ya incluye registros de pedidos para validación inmediata.

---

## 🗄️ Base de Datos
El archivo localhost.sql contiene la base de datos para el sistema A.
El archivo `Script_DB.sql` contiene las tablas necesarias para el sistemaB. Los pedidos ya cargados permiten visualizar el comportamiento del sistema multihilo sin necesidad de registros manuales previos.
