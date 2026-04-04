<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/components/navbar.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Productos - Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0">Productos</h2>
            <c:if test="${sessionScope.usuario != null && sessionScope.usuario.esAdmin}">
                <a href="${pageContext.request.contextPath}/add-producto" class="btn btn-primary">
                    <i class="bi bi-plus-circle"></i> Nuevo Producto
                </a>
            </c:if>
        </div>

        <div class="card mb-4">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/search-productos" method="get" class="row g-3">
                    <div class="col-md-4">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Buscar por nombre...">
                    </div>
                    <div class="col-md-4">
                        <label for="origen" class="form-label">Origen</label>
                        <input type="text" class="form-control" id="origen" name="origen" placeholder="Buscar por origen...">
                    </div>
                    <div class="col-md-4 d-flex align-items-end">
                        <button type="submit" class="btn btn-outline-primary w-100">
                            <i class="bi bi-search"></i> Buscar
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <div class="card shadow-sm">
            <div class="card-body p-0">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Origen</th>
                            <th>Categoría</th>
                            <th>Stock</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="producto" items="${productos}">
                            <tr>
                                <td>${producto.id}</td>
                                <td><strong>${producto.nombre}</strong></td>
                                <td>${producto.precio} €</td>
                                <td>${producto.origen}</td>
                                <td>${producto.categoriaId}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${producto.stockDisponible}">
                                            <span class="badge bg-success">Disponible</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">Sin stock</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/detalle-producto?id=${producto.id}" class="btn btn-sm btn-info text-white">
                                        <i class="bi bi-eye"></i>
                                    </a>
                                    <c:if test="${sessionScope.usuario != null && sessionScope.usuario.esAdmin}">
                                        <a href="${pageContext.request.contextPath}/edit-producto?id=${producto.id}" class="btn btn-sm btn-warning" title="Editar">
                                            <i class="bi bi-pencil"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/delete-producto?id=${producto.id}" class="btn btn-sm btn-danger" onclick="return confirm('¿Eliminar producto?')">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty productos}">
                            <tr>
                                <td colspan="7" class="text-center text-muted py-4">
                                    <i class="bi bi-inbox fs-1 d-block mb-2"></i>
                                    No hay productos disponibles
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Paginación -->
        <c:if test="${totalPaginas > 1}">
            <nav aria-label="Paginación de productos" class="mt-4">
                <ul class="pagination justify-content-center">
                    <!-- Anterior -->
                    <c:choose>
                        <c:when test="${paginaActual > 1}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${paginaActual - 1}">Anterior</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <span class="page-link">Anterior</span>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <!-- Números de página -->
                    <c:forEach begin="1" end="${totalPaginas}" var="i">
                        <c:choose>
                            <c:when test="${i == paginaActual}">
                                <li class="page-item active"><span class="page-link">${i}</span></li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link" href="?page=${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- Siguiente -->
                    <c:choose>
                        <c:when test="${paginaActual < totalPaginas}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${paginaActual + 1}">Siguiente</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <span class="page-link">Siguiente</span>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
            <div class="text-center text-muted mb-4">
                Mostrando ${productos.size()} de ${totalProductos} productos - Página ${paginaActual} de ${totalPaginas}
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
