# Checklist de Evaluación AA2 - Tienda de Café

> ⚠️ **CRÍTICO**: Los requisitos obligatorios SIN COMPLETAR = SUSPENSO AUTOMÁTICO

---

## 🔴 OBLIGATORIOS (1 pto cada uno)

### R01 — Dar de alta (3+ tablas con validación e imágenes)

- [ ] **Formulario alta Tabla 1 (usuarios)**
  - [ ] Campos: nombre, email, contraseña (todos requeridos)
  - [ ] Validación: email formato válido, contraseña mínimo 8 caracteres
  - [ ] Hash SHA-256 de contraseña antes de guardar

- [ ] **Formulario alta Tabla 2 (productos)**
  - [ ] Campos: nombre, descripción, precio, origen, categoría (todos requeridos)
  - [ ] Validación: precio numérico positivo, origen no vacío
  - [ ] **Campo imagen**: input file acepta .jpg/.png, se guarda en servidor, se almacena URL en BBDD

- [ ] **Formulario alta Tabla 3 (categorías)**
  - [ ] Campos: nombre, descripción (ambos requeridos)
  - [ ] Validación: nombre entre 3-100 caracteres

---

### R02 — Listado + Vista Detalle (3 tablas)

- [ ] **Listado Tabla 1 (usuarios)**
  - [ ] Tabla HTML con todos los registros
  - [ ] Cada fila clickeable → vista detalle

- [ ] **Vista Detalle Tabla 1 (usuarios)**
  - [ ] Muestra TODOS los campos del registro
  - [ ] Botón "Volver al listado"

- [ ] **Listado Tabla 2 (productos)**
  - [ ] Tabla HTML con todos los registros (nombre, precio, origen)
  - [ ] Cada fila clickeable → vista detalle

- [ ] **Vista Detalle Tabla 2 (productos)**
  - [ ] Muestra TODOS los campos (incluyendo imagen si existe)
  - [ ] Botón "Volver al listado"

- [ ] **Listado Tabla 3 (categorías)**
  - [ ] Tabla HTML con todos los registros
  - [ ] Cada fila clickeable → vista detalle

- [ ] **Vista Detalle Tabla 3 (categorías)**
  - [ ] Muestra TODOS los campos
  - [ ] Botón "Volver al listado"

---

### R03 — Búsqueda (3 tablas con 2+ criterios simultáneos)

- [ ] **Búsqueda Tabla 1 (usuarios)**
  - [ ] Formulario con 2+ campos de búsqueda (ej: nombre + email)
  - [ ] WHERE nombre LIKE '%X%' AND email LIKE '%Y%'
  - [ ] Resultados mostrados en tabla clickeable a detalle

- [ ] **Búsqueda Tabla 2 (productos)**
  - [ ] Formulario con 2+ campos de búsqueda (ej: nombre + origen)
  - [ ] WHERE nombre LIKE '%X%' AND origen LIKE '%Y%'
  - [ ] Resultados mostrados en tabla clickeable a detalle

- [ ] **Búsqueda Tabla 3 (categorías)**
  - [ ] Formulario con al menos 2 campos de búsqueda (ej: nombre)
  - [ ]WHERE nombre LIKE '%X%'
  - [ ] Resultados mostrados en tabla clickeable a detalle

---

### R04 — Modificar y Dar de Baja (desde detalle y listado)

- [ ] **Modificar Tabla 1 (usuarios)**
  - [ ] Botón "Modificar" en vista detalle → formulario pre-rellenado
  - [ ] Validación igual que en alta
  - [ ] Guardar cambios en BBDD

- [ ] **Dar de baja Tabla 1 (usuarios)**
  - [ ] Botón "Eliminar" en vista detalle y/o en listado
  - [ ] DELETE FROM usuarios WHERE id = X

- [ ] **Modificar Tabla 2 (productos)**
  - [ ] Botón "Modificar" en vista detalle → formulario pre-rellenado
  - [ ] Validación igual que en alta
  - [ ] Posibilidad de cambiar imagen

- [ ] **Dar de baja Tabla 2 (productos)**
  - [ ] Botón "Eliminar" en vista detalle y/o en listado

- [ ] **Modificar Tabla 3 (categorías)**
  - [ ] Botón "Modificar" en vista detalle → formulario pre-rellenado
  - [ ] Validación igual que en alta

- [ ] **Dar de baja Tabla 3 (categorías)**
  - [ ] Botón "Eliminar" en vista detalle y/o en listado

---

### R05 — Login de usuarios (2+ roles, web adaptativa)

- [ ] **Sistema login/logout**
  - [ ] Formulario login: email + contraseña
  - [ ] Validación credenciales contra BBDD (hash SHA-256)
  - [ ] Sesión almacena objeto Usuario
  - [ ] Logout invalida sesión

- [ ] **2+ roles diferenciados**
  - [ ] Rol "cliente": puede comprar, ver pedidos propios, modificar perfil
  - [ ] Rol "admin": puede CRUD todas las tablas, ver todos los pedidos
  - [ ] Campo `es_admin` BOOLEAN en tabla usuarios

- [ ] **Web adaptativa según rol**
  - [ ] Si cliente logueado: ve navbar con "Mi cuenta", "Mis pedidos", "Carrito"
  - [ ] Si admin logueado: ve navbar adicional con "Gestionar productos", "Gestionar usuarios"
  - [ ] Rutas protegidas según rol (filtro AuthFilter)

- [ ] **Control de acceso por rol**
  - [ ] Si usuario no logueado intenta acceder a zona protegida → redirigido a login
  - [ ] Si cliente intenta acceder a admin → redirigido o error 403

---

## 🟢 ADICIONALES (1 pto cada una)

---

### F01 — Repositorio GitHub + Git Flow

- [ ] Crear repositorio en GitHub (nombre: origen-codigo o similar)
- [ ] Inicializar git local con `git init`
- [ ] Crear rama develop: `git checkout -b develop`
- [ ] Rama main/master para producción
- [ ] Trabajar en feature branches desde develop
- [ ] Commits descriptivos por funcionalidad
- [ ] Merge a develop cuando feature esté completa

---

### F02 — Relaciones entre tablas (funcionalidad)

- [ ] Relación usuario → pedido (1:N)
  - [ ] Un usuario puede tener varios pedidos
  - [ ] Vista "Mis pedidos" muestra solo pedidos del usuario logueado

- [ ] Relación pedido → items_pedido → producto (N:M via items_pedido)
  - [ ] Un pedido tiene varios items (cada item = un producto + cantidad)
  - [ ] Al confirmar pedido: guardar en tabla `pedidos` y `items_pedido`

- [ ] Ejemplo flujo: Usuario añade productos al carrito → Checkout → Se crea Pedido con items_pedido relacionados

---

### F03 — Ajax para acciones asíncronas

- [ ] **Alta con Ajax**
  - [ ] Formulario envía POST vía fetch() en vez de submit tradicional
  - [ ] Respuesta JSON: `{success: true/false, message: "..."}`
  - [ ] Actualización de UI sin recarga de página

- [ ] **Eliminar con Ajax**
  - [ ] Botón eliminar envía DELETE vía fetch()
  - [ ] Fila se elimina del DOM tras respuesta exitosa

- [ ] **Modificar con Ajax**
  - [ ] Formulario pre-rellenados con datos actuales
  - [ ] Envío de cambios vía fetch() PUT/PATCH
  - [ ] Feedback visual al usuario

---

### F04 — Paginación en listados

- [ ] **Parámetros de paginación**
  - [ ] `?page=X` y `?limit=Y` en URLs de listado

- [ ] **Lógica backend**
  - [ ] `LIMIT :limit OFFSET :offset` en queries SQL
  - [ ] Contador total para calcular páginas totales

- [ ] **Controles de paginación en UI**
  - [ ] Botones "Anterior" / "Siguiente"
  - [ ] Números de página
  - [ ] Deshabilitar cuando no haya más páginas

---

### F05 — CRUD 4ª tabla (alta/baja/listado)

- [ ] Tabla adicional ya existe: `pedidos` o `items_pedido`
- [ ] **Alta pedido**: desde carrito → checkout → POST /checkout
- [ ] **Listado pedidos**: GET /list-pedidos (cliente ve suyos, admin ve todos)
- [ ] **Vista detalle pedido**: GET /detalle-pedido?id=X
- [ ] **Dar de baja pedido**: DELETE o soft-delete (cambiar estado)

---

### F06 — Zona privada: perfil usuario

- [ ] **Ver perfil**
  - [ ] GET /profile o /mi-perfil
  - [ ] Muestra: nombre, email, fecha registro

- [ ] **Modificar datos personales**
  - [ ] Formulario con nombre y email actuales pre-rellenados
  - [ ] POST actualiza en BBDD

- [ ] **Cambiar contraseña**
  - [ ] Campos: contraseña actual + nueva contraseña + confirmar
  - [ ] Validación: nueva contraseña mínimo 8 caracteres
  - [ ] Hash SHA-256 antes de guardar

---

### F07 — Confirmación JS antes de modify/delete

- [ ] **Confirmación antes de eliminar**
  - [ ] `onclick="return confirm('¿Estás seguro de eliminar este elemento?')"`
  - [ ] Aplicado a TODOS los botones de eliminar

- [ ] **Confirmación antes de modificar (opcional)**
  - [ ] Mensaje tipo "Vas a guardar cambios. ¿Continuar?"

---

### F08 — Docker compose con BBDD precargada

- [ ] **docker-compose.yml configurado**
  - [ ] Servicio `app`: aplicación web
  - [ ] Servicio `db`: MariaDB

- [ ] **Datos precargados**
  - [ ] Scripts en `/docker-entrypoint-initdb.d/` para ejecutar al iniciar
  - [ ] Datos de ejemplo ya insertados (categorías, productos)

- [ ] **Volúmenes**
  - [ ] Persistencia de datos MariaDB
  - [ ] Persistencia de uploads

- [ ] **Puertos expuestos**
  - [ ] App en 4008:8080
  - [ ] DB en 4009:3306

---

### F09 — Despliegue AWS

- [ ] **EC2 o similar**
  - [ ] Instancia EC2 con Docker instalado
  - [ ] Seguridad: puertos 80/443 abiertos

- [ ] **Despliegue**
  - [ ] Subir código y docker-compose a la instancia
  - [ ] `docker-compose up -d` para запустить

- [ ] **Base de datos**
  - [ ] RDS MariaDB o contenedor local en EC2
  - [ ] Datos precargados

- [ ] **URL accesible**
  - [ ] DNS público o IP pública de la instancia
  - [ ] Aplicación funcionando en puerto 80

---

### F10 — Base de datos PostgreSQL

- [ ] **Adaptar schema**
  - [ ] Tipos PostgreSQL equivalentes (SERIAL en vez de AUTO_INCREMENT)
  - [ ] Ajustar driver: `org.postgresql.Driver`
  - [ ] Connection string: `jdbc:postgresql://host:5432/db`

- [ ] **DAO compatibility**
  - [ ] Queries SQL compatibles (Jdbi funciona con ambos motores)
  - [ ] Test de conexión exitoso

- [ ] **Docker compose alternativo**
  - [ ] Imagen PostgreSQL en vez de MariaDB
  - [ ] Datos precargados同样

---

### F11 — Base de datos MongoDB

- [ ] **Diseño de colecciones**
  - [ ] Colecciones: usuarios, productos, categorias, pedidos
  - [ ] Documentos con estructura equivalente

- [ ] **Driver MongoDB**
  - [ ] Añadir dependencia: `mongodb-driver-sync`
  - [ ] Connection string: `mongodb://host:27017/db`

- [ ] **DAO rewrite**
  - [ ] Queries MongoDB en vez de SQL
  - [ ]find(), insertOne(), updateOne(), deleteOne()

- [ ] **Dualidad de código**
  - [ ] Mantener versión SQL (MariaDB) y NoSQL (MongoDB)
  - [ ] Interfaz común o branches separadas

---

## 📊 RESUMEN DE ESTADO

| Categoría | Total | Estado |
|-----------|-------|--------|
| **Obligatorios** | **5** | 🔲 0/5 |
| **Adicionales** | **11** | 🔲 0/11 |
| **TOTAL** | **16** | **0/16** |

### Mínimo para aprobar: 5/5 obligatorios = 1 pto
### Completo: 5/5 + 11/11 = ~11 ptos