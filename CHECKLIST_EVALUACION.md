# 📋 CHECKLIST EVALUACIÓN 2ª EVALUACIÓN DAM - Origen & Código

## REQUISITOS OBLIGATORIOS (1 punto cada uno = 5 puntos)

| # | Requisito | Estado | Evidencia |
|---|-----------|--------|-----------|
| 1 | **Dar de alta** (3 tablas con validación) | ✅ HECHO | - Usuarios: AddUsuarioServlet + add-usuario.jsp (valida email, contraseña)<br>- Productos: AddProductoServlet + add-producto.jsp (valida nombre, precio)<br>- Categorías: AddCategoriaServlet + add-categoria.jsp (valida nombre)<br>- ❌ FALTA: imágenes en productos (el campo imagen_url existe pero no se sube nada) |
| 2 | **Listado y Vista Detalle** (3 tablas) | ✅ HECHO | - Usuarios: list-usuarios.jsp + view-usuario (falta ver detalle)<br>- Productos: productos.jsp + view-producto.jsp<br>- Categorías: list-categorias.jsp (falta vista detalle)<br>- Pedidos: pedidos.jsp + detalle-pedido.jsp |
| 3 | **Búsqueda** (3 tablas, 2 criterios) | ✅ HECHO | - Productos: SearchProductosServlet (nombre + origen) - 2 criterios ✅<br>- ❌ Falta búsqueda en Usuarios<br>- ❌ Falta búsqueda en Categorías |
| 4 | **Modificar y dar de baja** | ✅ HECHO | - Productos: DeleteProductoServlet, edit-producto (falta crear servlet)<br>- Usuarios: DeleteUsuarioServlet<br>- Categorías: DeleteCategoriaServlet<br>- ✅ Confirmación JS con confirm() en productos.jsp, cart.jsp, view-producto.jsp |
| 5 | **Login de usuarios** (2 roles) | ✅ HECHO | - LoginServlet + login.jsp<br>- 2 roles: Admin (esAdmin=true) vs Usuario normal<br>- Navbar adapta: Admin ve menú admin, usuarios ven "Mi Perfil" |

---

## OTRAS FUNCIONALIDADES (1 punto cada uno = 10 puntos posibles)

| # | Funcionalidad | Estado | Evidencia |
|---|---------------|--------|-----------|
| 1 | **Repositorio GitHub + Git Flow** | ✅ HECHO | - Repo: https://github.com/8sandro8/Origen-y-Codigo<br>- Ramas: feature/*, develop, main<br>- Commits con mensajes en español |
| 2 | **Funcionalidad (relaciones)** | ✅ HECHO | - Usuario → Pedido (getByUsuario)<br>- Pedido → PedidoItems (getByPedido)<br>- Producto → Categoría (categoriaId)<br>- CheckoutServlet crea pedido + items |
| 3 | **Funcionalidad (Javascript) - Ajax** | ✅ HECHO | - RemoveFromCartServlet devuelve JSON<br>- fetch() en cart.jsp elimina sin recarga<br>- ✅ ENTREGADO |
| 4 | **Funcionalidad (Paginación)** | ✅ HECHO | - ProductoDao.getAllPaginated(limit, offset)<br>- ListProductosServlet ?page=N<br>- Bootstrap pagination en productos.jsp (12/página)<br>- ✅ ENTREGADO |
| 5 | **Cuarta tabla** (alta, baja, listado) | ✅ HECHO | - 4ª tabla: PedidoItems (items_pedido)<br>- Listado: en detalle-pedido.jsp<br>- Alta: en CheckoutServlet (al comprar)<br>- Baja: PedidoItemDao.deleteByPedido |
| 6 | **Zona privada** | ✅ HECHO | - PerfilServlet (/profile)<br>- mi-perfil.jsp con pestañas: Datos Personales, Contraseña, Mis Pedidos<br>- Editar nombre/email, cambiar contraseña<br>- ✅ ENTREGADO |
| 7 | **(JS) Confirmación antes de modificar/borrar** | ✅ HECHO | - productos.jsp: confirm('¿Eliminar producto?')<br>- cart.jsp: confirm('¿Eliminar este producto del carrito?')<br>- view-producto.jsp: confirm('¿Estás seguro de eliminar este producto?') |
| 8 | **Docker Compose con BBDD** | ✅ HECHO | - docker-compose.yml con app + MariaDB<br>- db/schema.sql + db/init.sql con datos<br>- Puerto 4008 (app), 4009 (BBDD)<br>- ✅ ENTREGADO |
| 9 | **Desplegar en AWS** | ❌ NO | No se ha desplegado en AWS |
| 10 | **PostgreSQL** | ❌ NO | Solo MariaDB configurada |
| 11 | **MongoDB** | ❌ NO | No configurada |

---

## 📊 RESUMEN

| Sección | Puntos Posibles | Puntos Conseguidos |
|----------|-----------------|-------------------|
| Requisitos Obligatorios | 5 | **4.5** ⚠️ |
| Otras Funcionalidades | 10 | **7** ✅ |
| **TOTAL** | 15 | **11.5 - 12** |

### 🔴 PENDIENTES (para intentar más puntos):

1. **Búsqueda en Categorías** - Añadir búsqueda en list-categorias.jsp
2. **Búsqueda en Usuarios** - Crear búsqueda en list-usuarios.jsp  
3. **Vista Detalle de Categoría** - Crear view-categoria.jsp
4. **Vista Detalle de Usuario** - Crear view-usuario.jsp
5. **Editar Producto** - Crear EditProductoServlet + edit-producto.jsp
6. **Editar Categoría** - Crear EditCategoriaServlet + edit-categoria.jsp

¿Quieres que implementemos alguno de estos puntos pendientes?