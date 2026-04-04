<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle Usuario - Origen &amp; Código</title>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/list-usuarios">Usuarios</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0"><i class="bi bi-person"></i> ${usuario.nombre}</h2>
            <a href="${pageContext.request.contextPath}/list-usuarios" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Volver al listado
            </a>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="card shadow-sm mb-4">
                    <div class="card-header bg-light">
                        <h5 class="mb-0"><i class="bi bi-person-lines-fill"></i> Información del usuario</h5>
                    </div>
                    <div class="card-body">
                        <table class="table table-borderless">
                            <tr>
                                <th width="30%">ID:</th>
                                <td>${usuario.id}</td>
                            </tr>
                            <tr>
                                <th>Nombre:</th>
                                <td><strong>${usuario.nombre}</strong></td>
                            </tr>
                            <tr>
                                <th>Email:</th>
                                <td>
                                    <i class="bi bi-envelope"></i> ${usuario.email}
                                </td>
                            </tr>
                            <tr>
                                <th>Fecha de registro:</th>
                                <td>
                                    <i class="bi bi-calendar"></i> 
                                    <fmt:formatDate value="${usuario.fechaRegistro}" pattern="dd/MM/yyyy" />
                                </td>
                            </tr>
                            <tr>
                                <th>Rol:</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${usuario.esAdmin}">
                                            <span class="badge bg-danger">
                                                <i class="bi bi-shield-fill"></i> Administrador
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">
                                                <i class="bi bi-person"></i> Usuario
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card shadow-sm mb-4">
                    <div class="card-header bg-light">
                        <h5 class="mb-0"><i class="bi bi-gear"></i> Acciones</h5>
                    </div>
                    <div class="card-body">
                        <div class="d-grid gap-2">
                            <a href="${pageContext.request.contextPath}/list-pedidos?usuarioId=${usuario.id}" class="btn btn-outline-primary">
                                <i class="bi bi-bag"></i> Ver pedidos de este usuario
                            </a>
                            <form method="POST" action="${pageContext.request.contextPath}/delete-usuario" style="display:inline;">
                                <input type="hidden" name="id" value="${usuario.id}">
                                <button type="submit" class="btn btn-outline-danger" onclick="return confirm('¿Estás totally seguro de que deseas eliminar este elemento? Esta acción no se puede deshacer.');">
                                    <i class="bi bi-trash"></i> Eliminar usuario
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>