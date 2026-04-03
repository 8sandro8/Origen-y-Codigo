<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Origen &amp; Código - Tienda de Café</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <link rel="stylesheet"
                    href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
                <style>
                    .price-tag {
                        background: linear-gradient(135deg, #D2691E, #8B4513);
                    }

                    .navbar-custom {
                        height: 100px;
                    }
                </style>
            </head>

            <body class="bg-light">

                <nav class="navbar navbar-expand-lg navbar-dark bg-dark navbar-custom">
                    <div class="container position-relative h-100 d-flex align-items-center">

                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarNav">
                            <span class="navbar-toggler-icon"></span>
                        </button>

                        <a class="position-absolute top-50 start-50 translate-middle"
                            href="${pageContext.request.contextPath}/" style="z-index: 10;">
                            <img src="${pageContext.request.contextPath}/images/logo-transparente.png"
                                alt="Origen y Código"
                                style="height: 120px; object-fit: contain; transform: scale(1.5);">
                        </a>

                        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                            <ul class="navbar-nav">
                                <li class="nav-item"><a class="nav-link fs-5"
                                        href="${pageContext.request.contextPath}/"><i class="bi bi-house-door"></i>
                                        Inicio</a></li>
                                <li class="nav-item ms-3"><a class="nav-link fs-5"
                                        href="${pageContext.request.contextPath}/list-productos"><i
                                            class="bi bi-grid-3x3-gap"></i> Catálogo</a></li>
                                <li class="nav-item ms-3">
                                    <a class="nav-link fs-5 position-relative" href="#">
                                        <i class="bi bi-cart"></i>
                                        <c:if test="${sessionScope.carrito != null && sessionScope.carrito.size() > 0}">
                                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                                ${sessionScope.carrito.size()}
                                            </span>
                                        </c:if>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>

                <div class="position-relative vh-100"
                    style="background-image: url('${pageContext.request.contextPath}/images/hero-bg.png'); background-size: cover; background-position: center; margin-top: -5px;">
                    <div class="position-absolute top-0 start-0 w-100 h-100" style="background-color: rgba(0,0,0,0.6);">
                    </div>
                    <div class="container position-relative text-center d-flex align-items-end justify-content-center pb-5"
                        style="min-height: 100vh;">
                        <a href="#productos" class="btn btn-warning btn-lg px-5 py-3 fs-4 fw-bold shadow"><i
                                class="bi bi-arrow-down-circle me-2"></i> Explorar Catálogo</a>
                    </div>
                </div>

                <section id="productos" class="py-5 bg-light">
                    <div class="container mt-5">
                        <div class="text-center mb-5">
                            <h2 class="text-warning display-5">✨ Selección de Cafés</h2>
                            <p class="text-muted fs-5">Calidad premium de los mejores orígenes del mundo</p>
                        </div>

                        <div class="row g-4">
                            <c:forEach var="producto" items="${productos}">
                                <div class="col-md-6 col-lg-3">
                                    <div class="card h-100 shadow-sm">
                                        <c:choose>
                                            <c:when test="${not empty producto.imagenUrl}">
                                                <img src="${producto.imagenUrl}" class="card-img-top"
                                                    alt="${producto.nombre}" style="height: 180px; object-fit: cover;">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="d-flex align-items-center justify-content-center bg-secondary"
                                                    style="height: 180px;">
                                                    <i class="bi bi-cup-hot fs-1 text-white"></i>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="card-body">
                                            <h5 class="card-title mb-2">${producto.nombre}</h5>
                                            <p class="card-text small text-muted mb-3">${producto.descripcion}</p>
                                            <div class="d-flex justify-content-between align-items-center mb-3">
                                                <span class="badge bg-secondary"><i class="bi bi-geo-alt-fill"></i>
                                                    ${producto.origen}</span>
                                                <span class="badge price-tag fs-6">
                                                    <fmt:formatNumber value="${producto.precio}" minFractionDigits="2"
                                                        maxFractionDigits="2" /> €
                                                </span>
                                            </div>
                                            <c:choose>
                                                <c:when test="${producto.stockDisponible}">
                                                    <form action="${pageContext.request.contextPath}/add-to-cart" method="post">
                                                        <input type="hidden" name="id" value="${producto.id}">
                                                        <button type="submit" class="btn btn-warning w-100 fw-bold"><i
                                                                class="bi bi-cart-plus me-2"></i>Añadir</button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-secondary w-100 fw-bold" disabled><i
                                                            class="bi bi-x-circle me-2"></i>Sin stock</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <c:if test="${empty productos}">
                            <div class="text-center py-5 my-5">
                                <i class="bi bi-cup-hot text-muted" style="font-size: 4rem;"></i>
                                <p class="text-muted mt-3 fs-5">Aún no hay productos disponibles en el catálogo.</p>
                            </div>
                        </c:if>
                    </div>
                </section>

                <footer class="bg-dark py-4">
                    <div class="container text-center">
                        <p class="mb-0 text-muted">
                            <img src="${pageContext.request.contextPath}/images/logo-transparente.png" alt="Logo"
                                style="height: 25px; margin-right: 5px; opacity: 0.5;">
                            <span class="text-warning">Origen &amp; Código</span> &copy; 2026
                        </p>
                    </div>
                </footer>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>