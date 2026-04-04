<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle Categoría - Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .price-tag {
            background: linear-gradient(135deg, #D2691E, #8B4513);
        }
    </style>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/list-categorias">Categorías</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0"><i class="bi bi-folder"></i> ${categoria.nombre}</h2>
            <a href="${pageContext.request.contextPath}/list-categorias" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Volver al listado
            </a>
        </div>

        <!-- Info de la categoría -->
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <h5 class="card-title">Descripción</h5>
                <p class="card-text">${categoria.descripcion}</p>
            </div>
        </div>

        <!-- Productos de esta categoría -->
        <div class="card shadow-sm">
            <div class="card-header bg-light">
                <h5 class="mb-0"><i class="bi bi-box-seam"></i> Productos en esta categoría (${productos.size()})</h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty productos}">
                        <div class="row g-4">
                            <c:forEach var="producto" items="${productos}">
                                <div class="col-md-6 col-lg-3">
                                    <div class="card h-100 shadow-sm">
                                        <a href="${pageContext.request.contextPath}/detalle-producto?id=${producto.id}" class="text-decoration-none">
                                            <c:choose>
                                                <c:when test="${not empty producto.imagenUrl}">
                                                    <img src="${pageContext.request.contextPath}/uploads/${producto.imagenUrl}" 
                                                         class="card-img-top" alt="${producto.nombre}" 
                                                         style="height: 140px; object-fit: cover;">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="d-flex align-items-center justify-content-center bg-secondary" 
                                                         style="height: 140px;">
                                                        <i class="bi bi-cup-hot fs-1 text-white"></i>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="card-body">
                                                <h6 class="card-title text-dark mb-1">${producto.nombre}</h6>
                                                <p class="card-text small text-muted mb-2">${producto.origen}</p>
                                                <span class="badge price-tag">
                                                    <fmt:formatNumber value="${producto.precio}" minFractionDigits="2" /> €
                                                </span>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center py-4 text-muted">
                            <i class="bi bi-inbox fs-1 d-block mb-2"></i>
                            No hay productos en esta categoría
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>