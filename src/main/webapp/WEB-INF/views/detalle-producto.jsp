<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/components/navbar.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${producto.nombre} - Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .price-tag {
            background: linear-gradient(135deg, #D2691E, #8B4513);
        }
        .product-image {
            max-height: 400px;
            object-fit: contain;
        }
    </style>
</head>
<body class="bg-light">

    <div class="container py-5">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb" class="mb-4">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Inicio</a></li>
                <li class="breadcrumb-item active">${producto.nombre}</li>
            </ol>
        </nav>

        <!-- Detalle del producto -->
        <div class="row mb-5">
            <div class="col-md-6 mb-4 mb-md-0">
                <div class="card shadow-sm h-100">
                    <div class="card-body d-flex align-items-center justify-content-center bg-white">
                        <c:choose>
                            <c:when test="${not empty producto.imagenUrl}">
                                <img src="${producto.imagenUrl}" class="img-fluid product-image" alt="${producto.nombre}">
                            </c:when>
                            <c:otherwise>
                                <div class="text-center py-5">
                                    <i class="bi bi-cup-hot text-secondary" style="font-size: 8rem;"></i>
                                    <p class="text-muted mt-3">Imagen no disponible</p>
                                    <p class="text-muted small">🎯 <strong>Placeholder:</strong> Aquí irá la foto del café</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <span class="badge bg-secondary mb-2">${producto.origen}</span>
                        <h1 class="display-5 fw-bold text-dark mb-3">${producto.nombre}</h1>
                        
                        <p class="lead text-muted mb-4">${producto.descripcion}</p>
                        
                        <div class="mb-4">
                            <span class="badge bg-secondary">
                                <i class="bi bi-tag"></i> Categoría: ${producto.categoriaId}
                            </span>
                            <c:choose>
                                <c:when test="${producto.stockDisponible}">
                                    <span class="badge bg-success ms-2">
                                        <i class="bi bi-check-circle"></i> En stock
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger ms-2">
                                        <i class="bi bi-x-circle"></i> Sin stock
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="d-flex align-items-center mb-4">
                            <span class="display-4 fw-bold text-success me-3">
                                <fmt:formatNumber value="${producto.precio}" minFractionDigits="2" maxFractionDigits="2" /> €
                            </span>
                            <span class="text-muted">/ unidad</span>
                        </div>

                        <c:choose>
                            <c:when test="${producto.stockDisponible}">
                                <form action="${pageContext.request.contextPath}/add-to-cart" method="post" class="d-flex gap-2">
                                    <input type="hidden" name="id" value="${producto.id}">
                                    <input type="number" name="cantidad" value="1" min="1" max="99" class="form-control" style="width: 80px;">
                                    <button type="submit" class="btn btn-warning btn-lg fw-bold flex-grow-1">
                                        <i class="bi bi-cart-plus me-2"></i>Añadir al Carrito
                                    </button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-secondary btn-lg w-100 fw-bold" disabled>
                                    <i class="bi bi-x-circle me-2"></i>No disponible
                                </button>
                            </c:otherwise>
                        </c:choose>

                        <div class="mt-4">
                            <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-left me-2"></i>Volver al catálogo
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Productos recomendados -->
        <c:if test="${not empty recomendados}">
            <div class="mt-5">
                <h3 class="mb-4"><i class="bi bi-stars text-warning"></i> También te puede interesar</h3>
                <div class="row g-4">
                    <c:forEach var="recomendado" items="${recomendados}">
                        <div class="col-md-6 col-lg-3">
                            <div class="card h-100 shadow-sm">
                                <a href="${pageContext.request.contextPath}/detalle-producto?id=${recomendado.id}" class="text-decoration-none">
                                    <c:choose>
                                        <c:when test="${not empty recomendado.imagenUrl}">
                                            <img src="${recomendado.imagenUrl}" class="card-img-top" alt="${recomendado.nombre}" style="height: 150px; object-fit: cover;">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="d-flex align-items-center justify-content-center bg-secondary" style="height: 150px;">
                                                <i class="bi bi-cup-hot fs-1 text-white"></i>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="card-body">
                                        <h6 class="card-title text-dark mb-1">${recomendado.nombre}</h6>
                                        <p class="card-text small text-muted mb-2">${recomendado.origen}</p>
                                        <span class="badge price-tag">
                                            <fmt:formatNumber value="${recomendado.precio}" minFractionDigits="2" /> €
                                        </span>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </div>

    <footer class="bg-dark py-4 mt-5">
        <div class="container text-center">
            <p class="mb-0 text-muted">
                <img src="${pageContext.request.contextPath}/images/logo-transparente.png" alt="Logo" style="height: 25px; margin-right: 5px; opacity: 0.5;">
                <span class="text-warning">Origen &amp; Código</span> &copy; 2026
            </p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>