<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Producto - Origen &amp; Código</title>
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
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card shadow-sm p-4">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2 class="mb-0"><i class="bi bi-pencil"></i> Editar Producto</h2>
                        <a href="${pageContext.request.contextPath}/list-productos" class="btn btn-outline-secondary">
                            <i class="bi bi-x-lg"></i> Cancelar
                        </a>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            <i class="bi bi-exclamation-triangle"></i> ${error}
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/edit-producto" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="id" value="${producto.id}">
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="nombre" class="form-label">Nombre *</label>
                                <input type="text" class="form-control" id="nombre" name="nombre" value="${producto.nombre}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="precio" class="form-label">Precio *</label>
                                <div class="input-group">
                                    <span class="input-group-text">€</span>
                                    <input type="number" step="0.01" min="0" class="form-control" id="precio" name="precio" value="${producto.precio}" required>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="descripcion" class="form-label">Descripción</label>
                            <textarea class="form-control" id="descripcion" name="descripcion" rows="3">${producto.descripcion}</textarea>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="origen" class="form-label">Origen *</label>
                                <input type="text" class="form-control" id="origen" name="origen" value="${producto.origen}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="categoriaId" class="form-label">Categoría *</label>
                                <select class="form-select" id="categoriaId" name="categoriaId" required>
                                    <option value="1" ${producto.categoriaId == 1 ? 'selected' : ''}>Café</option>
                                    <option value="2" ${producto.categoriaId == 2 ? 'selected' : ''}>Accesorios</option>
                                    <option value="3" ${producto.categoriaId == 3 ? 'selected' : ''}>Infusiones</option>
                                    <option value="4" ${producto.categoriaId == 4 ? 'selected' : ''}>Equipos</option>
                                </select>
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="stockDisponible" name="stockDisponible" ${producto.stockDisponible ? 'checked' : ''}>
                                <label class="form-check-label" for="stockDisponible">
                                    Producto disponible en stock
                                </label>
                            </div>
                        </div>

                        <!-- Imagen actual -->
                        <div class="mb-3">
                            <label class="form-label">Imagen actual</label>
                            <div class="border rounded p-2 d-inline-block">
                                <c:choose>
                                    <c:when test="${not empty producto.imagenUrl}">
                                        <img src="${pageContext.request.contextPath}/uploads/${producto.imagenUrl}" 
                                             alt="${producto.nombre}" style="max-height: 150px; object-fit: contain;">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="bg-secondary d-flex align-items-center justify-content-center text-white rounded" 
                                             style="width: 150px; height: 100px;">
                                            <i class="bi bi-image fs-3"></i>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Nueva imagen -->
                        <div class="mb-4">
                            <label for="imagen" class="form-label">Cambiar imagen</label>
                            <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
                            <div class="form-text">Deja este campo vacío para mantener la imagen actual. Formatos: JPG, PNG, GIF</div>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-lg"></i> Guardar Cambios
                            </button>
                            <a href="${pageContext.request.contextPath}/list-productos" class="btn btn-secondary">
                                Cancelar
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>