<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/components/navbar.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Pedidos - Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

    <div class="container">
        <h2 class="mb-4"><i class="bi bi-bag"></i> ${empty titulo ? 'Mis Pedidos' : titulo}</h2>

        <c:if test="${param.success == 'pedido_creado'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle"></i> Tu pedido ha sido creado correctamente. Número de pedido: ${param.pedidoId}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${param.error == 'carrito_vacio'}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i> Tu carrito está vacío. Añade productos antes de comprar.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${empty pedidos}">
            <div class="text-center py-5">
                <i class="bi bi-bag-x text-muted" style="font-size: 4rem;"></i>
                <p class="text-muted mt-3 fs-5">No tienes pedidos todavía</p>
                <a href="${pageContext.request.contextPath}/list-productos" class="btn btn-primary">
                    <i class="bi bi-grid-3x3-gap"></i> Ver Catálogo
                </a>
            </div>
        </c:if>

        <c:if test="${not empty pedidos}">
            <div class="card shadow-sm">
                <div class="card-body p-0">
                    <table class="table table-hover mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Fecha</th>
                                <th>Total</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pedido" items="${pedidos}">
                                <tr>
                                    <td><strong>#${pedido.id}</strong></td>
                                    <td>${pedido.fechaFormateada}</td>
                                    <td><fmt:formatNumber value="${pedido.total}" minFractionDigits="2" /> €</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pedido.estado == 'pendiente'}">
                                                <span class="badge bg-warning">Pendiente</span>
                                            </c:when>
                                            <c:when test="${pedido.estado == 'procesando'}">
                                                <span class="badge bg-info">Procesando</span>
                                            </c:when>
                                            <c:when test="${pedido.estado == 'enviado'}">
                                                <span class="badge bg-primary">Enviado</span>
                                            </c:when>
                                            <c:when test="${pedido.estado == 'entregado'}">
                                                <span class="badge bg-success">Entregado</span>
                                            </c:when>
                                            <c:when test="${pedido.estado == 'cancelado'}">
                                                <span class="badge bg-danger">Cancelado</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">${pedido.estado}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/detalle-pedido?id=${pedido.id}" class="btn btn-sm btn-outline-primary" title="Ver detalles">
                                            <i class="bi bi-eye"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>

        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Volver al Inicio
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
