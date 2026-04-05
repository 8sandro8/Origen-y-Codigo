<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Usuarios - Origen &amp; Código</title>
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
                        <a class="nav-link active" href="${pageContext.request.contextPath}/list-usuarios">Usuarios</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0">Usuarios</h2>
            <a href="${pageContext.request.contextPath}/add-usuario" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Añadir Usuario
            </a>
        </div>

        <!-- Buscador con 2 criterios -->
        <div class="card mb-4">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/search-usuarios" method="get" class="row g-3">
                    <div class="col-md-5">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" 
                               placeholder="Buscar por nombre..." value="${busquedaNombre}">
                    </div>
                    <div class="col-md-5">
                        <label for="email" class="form-label">Email</label>
                        <input type="text" class="form-control" id="email" name="email" 
                               placeholder="Buscar por email..." value="${busquedaEmail}">
                    </div>
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-outline-primary w-100">
                            <i class="bi bi-search"></i> Buscar
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Alerts -->
        <c:if test="${param.success == 'borrado'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle"></i> Usuario eliminado correctamente.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${param.error == 'en_uso'}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i> No se puede eliminar. El usuario tiene pedidos asociados.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${param.error == 'bd'}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i> Error de base de datos. Inténtalo de nuevo.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="card shadow-sm">
            <div class="card-body p-0">
                <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Fecha Registro</th>
                            <th>Rol</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usuario" items="${usuarios}">
                            <tr>
                                <td>${usuario.id}</td>
                                <td><strong>${usuario.nombre}</strong></td>
                                <td>${usuario.email}</td>
                                <td>${usuario.fechaRegistro}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${usuario.esAdmin}">
                                            <span class="badge bg-danger">Admin</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">Usuario</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/view-usuario?id=${usuario.id}" 
                                       class="btn btn-sm btn-info text-white" title="Ver detalle">
                                        <i class="bi bi-eye"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty usuarios}">
                            <tr>
                                <td colspan="6" class="text-center text-muted py-4">
                                    <i class="bi bi-inbox fs-1 d-block mb-2"></i>
                                    No hay usuarios disponibles
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
