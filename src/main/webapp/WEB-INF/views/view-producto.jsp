<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle Producto - Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="bi bi-cup-hot"></i> Origen &amp; Código
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/list-productos">Productos</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card shadow-sm p-4">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2 class="mb-0">Detalle del Producto</h2>
                        <a href="${pageContext.request.contextPath}/list-productos" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left"></i> Volver
                        </a>
                    </div>

                    <div class="row">
                        <div class="col-md-5">
                            <c:choose>
                                <c:when test="${not empty producto.imagenUrl}">
                                    <img src="${pageContext.request.contextPath}/uploads/${producto.imagenUrl}" alt="${producto.nombre}" class="img-fluid rounded" style="max-height: 400px; object-fit: contain;">
                                </c:when>
                                <c:otherwise>
                                    <div class="bg-secondary d-flex align-items-center justify-content-center text-white rounded" style="height: 300px;">
                                        <i class="bi bi-image fs-1"></i>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col-md-7">
                            <table class="table table-borderless">
                                <tr>
                                    <th class="text-muted" style="width: 140px;">ID:</th>
                                    <td>${producto.id}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted">Nombre:</th>
                                    <td><strong>${producto.nombre}</strong></td>
                                </tr>
                                <tr>
                                    <th class="text-muted">Descripción:</th>
                                    <td>${producto.descripcion}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted">Precio:</th>
                                    <td class="text-success fw-bold">${producto.precio} €</td>
                                </tr>
                                <tr>
                                    <th class="text-muted">Origen:</th>
                                    <td>${producto.origen}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted">Categoría ID:</th>
                                    <td>${producto.categoriaId}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted">Stock:</th>
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
                                </tr>
                            </table>

                            <div class="mt-4">
                                <a href="${pageContext.request.contextPath}/edit-producto?id=${producto.id}" class="btn btn-warning">
                                    <i class="bi bi-pencil"></i> Editar
                                </a>
                                <form method="POST" action="${pageContext.request.contextPath}/delete-producto" style="display:inline;">
                                    <input type="hidden" name="id" value="${producto.id}">
                                    <button type="submit" class="btn btn-danger" onclick="return confirm('¿Estás totally seguro de que deseas eliminar este elemento? Esta acción no se puede deshacer.');">
                                        <i class="bi bi-trash"></i> Eliminar
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
