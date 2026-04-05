# Origen & Código 🏪☕

Tienda online de café de especialidad - Proyecto de fin de módulo.

## Descripción

Este proyecto es un ejercicio académico para practicar desarrollo web con Java. Es una tienda online donde los usuarios pueden:

- Registrarse e iniciar sesión
- Ver el catálogo de cafés
- Añadir productos al carrito
- Hacer pedidos y ver su historial

Los administradores pueden gestionar productos y categorías desde el panel de admin.

## Tecnologías

- **Backend:** Java 17, Maven, Servlets, JSP
- **Base de datos:** MariaDB
- **Despliegue:** Docker (contenedor en NAS Synology)

## Cómo ejecutarlo

1. Asegúrate de tener Docker instalado
2. Ejecuta el contenedor con docker-compose
3. Accede a `http://localhost:4008`

## Estructura del proyecto

```
src/main/
├── java/com/origencodigo/
│   ├── controller/   # Servlets
│   ├── dao/          # Acceso a datos
│   └── model/        # Entidades
└── webapp/
    └── WEB-INF/views/  # Páginas JSP
```

## Notas

- El carrito se gestiona con la sesión de HTTP
- La autenticación usa filtros de Servlet
- Usa Jdbi para acceso a la base de datos