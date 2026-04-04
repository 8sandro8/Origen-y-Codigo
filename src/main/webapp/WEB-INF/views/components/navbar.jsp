<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark navbar-custom">
    <div class="container position-relative h-100 d-flex align-items-center">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="position-absolute top-50 start-50 translate-middle" href="${pageContext.request.contextPath}/" style="z-index: 10;">
            <img src="${pageContext.request.contextPath}/images/logo-transparente.png" alt="Origen y Código" style="max-height: 70px; object-fit: contain;">
        </a>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/"><i class="bi bi-house-door"></i> Inicio</a></li>
                <li class="nav-item ms-3"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/list-productos"><i class="bi bi-grid-3x3-gap"></i> Catálogo</a></li>
                <c:if test="${sessionScope.usuario != null}">
                    <li class="nav-item ms-3"><a class="nav-link fs-5" href="${pageContext.request.contextPath}/list-pedidos"><i class="bi bi-bag"></i> Mis Pedidos</a></li>
                </c:if>
                <li class="nav-item ms-3">
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