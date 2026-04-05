<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.origencodigo.model.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark position-relative" style="height: 120px;">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>

            <a class="navbar-brand position-absolute top-50 start-50 translate-middle" href="${pageContext.request.contextPath}/" style="z-index: 2;">
                <img src="${pageContext.request.contextPath}/images/logo-transparente.png" alt="Origen y Código" style="height: 100px; object-fit: contain;">
            </a>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto" style="z-index: 1;">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/list-productos"><i class="bi bi-grid-3x3-gap"></i> Catálogo</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/"><i class="bi bi-house-door"></i> Inicio</a>
                    </li>
                    <%
                        if (usuario != null) {
                    %>
                        <%
                            if (usuario.isEsAdmin()) {
                        %>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                    <i class="bi bi-gear"></i> Admin
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li><a class="dropdown-item" href="<%= request.getContextPath() %>/list-usuarios">
                                        <i class="bi bi-people me-2"></i>Usuarios</a></li>
                                    <li><a class="dropdown-item" href="<%= request.getContextPath() %>/list-categorias">
                                        <i class="bi bi-tags me-2"></i>Categorías</a></li>
                                    <li><a class="dropdown-item" href="<%= request.getContextPath() %>/add-producto">
                                        <i class="bi bi-plus-circle me-2"></i>Nuevo Producto</a></li>
                                </ul>
                            </li>
                        <%
                            }
                        %>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                <i class="bi bi-person-circle"></i> <%= usuario.getNombre() %>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><span class="dropdown-item-text text-muted">
                                    <small><%= usuario.getEmail() %></small></span></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="<%= request.getContextPath() %>/logout">
                                    <i class="bi bi-box-arrow-right me-2"></i>Cerrar sesión</a></li>
                            </ul>
                        </li>
                    <%
                        } else {
                    %>
                        <li class="nav-item">
                            <a class="nav-link" href="<%= request.getContextPath() %>/login">
                                <i class="bi bi-box-arrow-in-right me-1"></i>Login
                            </a>
                        </li>
                    <%
                        }
                    %>
                </ul>
            </div>
        </div>
    </nav>
