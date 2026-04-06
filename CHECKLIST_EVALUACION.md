# 📋 CHECKLIST DEFINITIVO - 2ª EVALUACIÓN DAM - Origen & Código

## REQUISITOS OBLIGATORIOS (5 puntos)

| # | Requisito | Estado | Evidencia |
|---|-----------|--------|-----------|
| 1 | **Dar de alta** (3 tablas, validación, imagen) | ✅ COMPLETO | - Usuarios: AddUsuarioServlet + add-usuario.jsp (valida email, contraseña)<br>- Productos: AddProductoServlet + add-producto.jsp (valida nombre, precio, imagen con Thumbnailator)<br>- Categorías: AddCategoriaServlet + add-categoria.jsp (valida nombre) |
| 2 | **Listado y Vista Detalle** (3 tablas) | ✅ COMPLETO | - Usuarios: list-usuarios.jsp + view-usuario.jsp<br>- Productos: productos.jsp + detalle-producto.jsp<br>- Categorías: list-categorias.jsp + view-categoria.jsp<br>- Pedidos: pedidos.jsp + detalle-pedido.jsp |
| 3 | **Búsqueda** (3 tablas, 2 criterios cada una) | ✅ COMPLETO | - Productos: SearchProductosServlet (nombre + origen)<br>- Usuarios: SearchUsuarioServlet (nombre + email)<br>- Categorías: SearchCategoriaServlet (nombre + descripcion) |
| 4 | **Modificar y dar de baja** | ✅ COMPLETO | - Productos: EditProductoServlet + DeleteProductoServlet<br>- Usuarios: DeleteUsuarioServlet<br>- Categorías: DeleteCategoriaServlet<br>- Confirmación JS con confirm() en todos los formularios |
| 5 | **Login de usuarios** (2 roles) | ✅ COMPLETO | - LoginServlet + login.jsp<br>- 2 roles: Admin (esAdmin=true) vs Usuario normal<br>- Navbar adapta según rol |

---

## OTRAS FUNCIONALIDADES (7 de 10 puntos - descartados infraestrutura)

| # | Funcionalidad | Estado |
|---|---------------|--------|
| 1 | **Repositorio GitHub + Git Flow** | ✅ HECHO |
| 2 | **Funcionalidad (relaciones)** | ✅ HECHO |
| 3 | **Funcionalidad (Javascript) - Ajax** | ✅ HECHO |
| 4 | **Funcionalidad (Paginación)** | ✅ HECHO |
| 5 | **Cuarta tabla** (PedidoItems) | ✅ HECHO |
| 6 | **Zona privada** | ✅ HECHO |
| 7 | **Confirmación JS** | ✅ HECHO |
| 8 | **Docker Compose con BBDD** | ✅ HECHO |
| 9 | AWS | ❌ Descartado (infraestructura propia) |
| 10 | PostgreSQL | ❌ Descartado (infraestructura propia) |
| 11 | MongoDB | ❌ Descartado (infraestructura propia) |

---

## 📊 RESUMEN DEFINITIVO

| Sección | Puntos Posibles | Puntos Conseguidos |
|----------|-----------------|-------------------|
| **Requisitos Obligatorios** | 5 | **5/5** ✅ |
| **Otras Funcionalidades** | 7 (descartados 3 de infra) | **7/7** ✅ |
| **TOTAL** | 12 | **12/12** 🎯 |

---

## ✅ ESTADO FINAL: 10/10

**El proyecto está COMPLETO para la presentación.**

- Todas las funcionalidades implementadas
- Código limpio y modular
- Diseño responsive
- Docker Compose funcionando
- Git Flow aplicado correctamente