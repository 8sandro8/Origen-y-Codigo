# Origen & Código 🏪☕

Tienda online de café de especialidad - Proyecto final del módulo.

---

## Qué es esto

Es una página web donde puedes comprar café por internet. Los usuarios normales pueden registrarse, ver productos, añadir al carrito y hacer pedidos. Los administradores pueden gestionar productos, categorías y ver todos los pedidos.

---

## Qué puedes hacer

**Si eres cliente:**
- Crear una cuenta e iniciar sesión
- Ver el catálogo de cafés
- Buscar productos por nombre y origen
- Añadir productos al carrito
- Hacer pedidos y ver tu historial
- Cambiar tu contraseña

**Si eres administrador:**
- Todo lo que puede hacer un cliente
- Añadir, modificar y eliminar productos
- Añadir, modificar y eliminar categorías
- Ver pedidos de cualquier usuario
- Ver todos los usuarios registrados

---

## Tecnologías que hemos usado

| Parte | Tecnología |
|-------|------------|
| Lenguaje | Java 17 |
| Páginas web | JSP + JSTL + Bootstrap |
| Servidores web | Apache Tomcat (incluido en imagen Docker) |
| Base de datos | MariaDB |
| Acceso a datos | Jdbi 3 (nos ahorra escribir SQL a mano) |
| Imágenes | Thumbnailator (para redimensionar fotos) |
| Construcción | Maven |
| Despliegue | Docker + Docker Compose |

---

## Cómo ponerlo en marcha

**1. Clonar o descargar el proyecto**

**2. Arrancar con Docker Compose:**
```bash
docker-compose up -d
```

**3. Abrir en el navegador:**
```
http://localhost:4008
```

---

## Usuarios de prueba

| Rol | Email | Contraseña |
|-----|-------|------------|
| Administrador | sandro88zgz@gmail.com | admin |
| Cliente | sandro89zgz@gmail.com | admin |

---

## Cómo está organizado el código

```
src/main/
├── java/com/origencodigo/
│   ├── controller/    # Los servlets (controlan qué pasa cuando pulsas algo)
│   ├── dao/           # Las consultas a la base de datos
│   ├── model/         # Las clases de datos (Usuario, Producto, etc.)
│   └── filter/        # Filtro de autenticación
└── webapp/
    ├── WEB-INF/views/ # Las páginas JSP (lo que ves)
    └── uploads/        # Las fotos de los productos
```

**Base de datos:**
```
db/
├── schema.sql   # Crea las tablas
└── init.sql    # Datos de ejemplo
```

---

## Funcionalidades que hemos implementado

### Obligatorias
- ✅ Registro e inicio de sesión
- ✅ Alta de usuarios, productos y categorías
- ✅ Listados y vista detalle de todo
- ✅ Búsqueda con 2 criterios (nombre + origen por ejemplo)
- ✅ Modificar y eliminar desde cualquier sitio
- ✅ Login con 2 roles (cliente/admin)

### Extras
- ✅ Relations entre tablas (pedidos → productos)
- ✅ Sistema de carrito con sesiones
- ✅ Paginación de productos
- ✅ Confirmación antes de borrar
- ✅ Base de datos precargada con datos de ejemplo
- ✅ Gestión completa de pedidos
- ✅ Zona privada para cambiar datos y contraseña

---

## Problemas que hemos resuelto

- El driver de MariaDB no se cargaba bien → lo copiamos manualmente al classpath de Tomcat
- Los pedidos se mezclaban → ahora cada admin ve los pedidos del usuario correcto
- Las fechas en la vista de usuario fallaban → simplificamos el formato

---

## Autores

Proyecto realizado por **Sandro** como ejercicio académico de 1º DAM.

---

## Capturas

Puedes ver capturas funcionando en la carpeta `capturas/` del proyecto.