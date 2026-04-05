<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark py-3">
    <div class="container">
        <!-- Logo grande a la izquierda -->
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/images/logo-transparente.png" 
                 alt="Origen y Código" 
                 style="height: 100px !important; width: auto !important;">
        </a>
        
        <!-- Botón toggler -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        <!-- Menú a la derecha -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/"><i class="bi bi-house-door"></i> Inicio</a></li>
                <c:if test="${sessionScope.usuario != null && sessionScope.usuario.esAdmin}">
                    <li class="nav-item ms-2 dropdown">
                        <a class="nav-link fs-5 dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"><i class="bi bi-gear"></i> Admin</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/list-productos">Gestión Productos</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/list-categorias">Categorías</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/list-usuarios">Usuarios</a></li>
                        </ul>
                    </li>
                    <li class="nav-item ms-2"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/profile"><i class="bi bi-person"></i> Mi Perfil</a></li>
                    <li class="nav-item ms-2"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/list-pedidos"><i class="bi bi-bag"></i> Mis Pedidos</a></li>
                    <li class="nav-item ms-2"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right"></i> Cerrar Sesión</a></li>
                </c:if>
                <c:if test="${sessionScope.usuario != null && !sessionScope.usuario.esAdmin}">
                    <li class="nav-item ms-2"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/profile"><i class="bi bi-person"></i> Mi Perfil</a></li>
                    <li class="nav-item ms-2"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/list-pedidos"><i class="bi bi-bag"></i> Mis Pedidos</a></li>
                    <li class="nav-item ms-2"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right"></i> Cerrar Sesión</a></li>
                </c:if>
                <c:if test="${sessionScope.usuario == null}">
                    <li class="nav-item ms-2"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/login"><i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión</a></li>
                    <li class="nav-item ms-2"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/add-usuario"><i class="bi bi-person-plus"></i> Registrarse</a></li>
                </c:if>
                <li class="nav-item ms-2">
                    <a class="nav-link fs-5 position-relative" href="${pageContext.request.contextPath}/cart">
                        <i class="bi bi-cart"></i>
                        <c:if test="${sessionScope.carrito != null && sessionScope.carrito.size() > 0}">
                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">${sessionScope.carrito.size()}</span>
                        </c:if>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>