# Origen & Código - Estructura del Proyecto

Proyecto Java Web MVC/DAO para tienda online de café en grano.

## Estructura

```
src/
├── main/
│   ├── java/com/origencodigo/
│   │   ├── model/       # Entidades/POJOs
│   │   ├── dao/         # Interfaces DAO
│   │   ├── dao/impl/    # Implementaciones DAO
│   │   ├── controller/  # Servlets
│   │   ├── connection/  # Gestión conexión BBDD
│   │   └── util/        # Utilidades
│   └── webapp/
│       ├── WEB-INF/views/  # JSPs
│       ├── css/
│       ├── js/
│       └── images/
└── test/java/com/origencodigo/dao/  # Tests DAO
```

## Convenciones

- Basado en las convenciones de java.codeandcoke.com
- Patrón MVC + DAO
- MariaDB como motor de BBDD
