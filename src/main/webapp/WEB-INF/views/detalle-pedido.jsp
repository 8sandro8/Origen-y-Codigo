<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/components/navbar.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle del Pedido #${pedido.id} - Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

    <div class="container py-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="bi bi-receipt"></i> Detalle del Pedido #${pedido.id}</h2>
            <a href="${pageContext.request.contextPath}/list-pedidos" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Volver a Pedidos
            </a>
        </div>

        <!-- Info del pedido -->
        <div class="row mb-4">
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h6 class="card-subtitle mb-2 text-muted"><i class="bi bi-calendar"></i> Fecha</h6>
                        <p class="card-text fs-5"><fmt:formatDate value="${pedido.fechaPedido}" pattern="dd/MM/yyyy HH:mm" /></p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h6 class="card-subtitle mb-2 text-muted"><i class="bi bi-tag"></i> Estado</h6>
                        <p class="card-text">
                            <c:choose>
                                <c:when test="${pedido.estado == 'pendiente'}">
                                    <span class="badge bg-warning fs-6">Pendiente</span>
                                </c:when>
                                <c:when test="${pedido.estado == 'procesando'}">
                                    <span class="badge bg-info fs-6">Procesando</span>
                                </c:when>
                                <c:when test="${pedido.estado == 'enviado'}">
                                    <span class="badge bg-primary fs-6">Enviado</span>
                                </c:when>
                                <c:when test="${pedido.estado == 'entregado'}">
                                    <span class="badge bg-success fs-6">Entregado</span>
                                </c:when>
                                <c:when test="${pedido.estado == 'cancelado'}">
                                    <span class="badge bg-danger fs-6">Cancelado</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary fs-6">${pedido.estado}</span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h6 class="card-subtitle mb-2 text-muted"><i class="bi bi-currency-euro"></i> Total</h6>
                        <p class="card-text fs-4 fw-bold text-success">
                            <fmt:formatNumber value="${pedido.total}" minFractionDigits="2" /> €
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Items del pedido -->
        <div class="card shadow-sm">
            <div class="card-header bg-light">
                <h5 class="mb-0"><i class="bi bi-box-seam"></i> Productos</h5>
            </div>
            <div class="card-body p-0">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>Producto</th>
                            <th class="text-center">Cantidad</th>
                            <th class="text-end">Precio Unit.</th>
                            <th class="text-end">Subtotal</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${items}" varStatus="status">
                            <tr>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <c:choose>
                                            <c:when test="${not empty productos[status.index].imagenUrl}">
                                                <img src="${productos[status.index].imagenUrl}" 
                                                     alt="${productos[status.index].nombre}"
                                                     style="width: 50px; height: 50px; object-fit: cover;" 
                                                     class="rounded me-3">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="bg-secondary rounded d-flex align-items-center justify-content-center me-3" 
                                                     style="width: 50px; height: 50px;">
                                                    <i class="bi bi-cup-hot text-white"></i>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <div>
                                            <strong>${productos[status.index].nombre}</strong>
                                            <br>
                                            <small class="text-muted">${productos[status.index].origen}</small>
                                        </div>
                                    </div>
                                </td>
                                <td class="text-center align-middle">
                                    <span class="badge bg-secondary fs-6">${item.cantidad}</span>
                                </td>
                                <td class="text-end align-middle">
                                    <fmt:formatNumber value="${item.precioUnitario}" minFractionDigits="2" /> €
                                </td>
                                <td class="text-end align-middle fw-bold">
                                    <fmt:formatNumber value="${item.cantidad * item.precioUnitario}" minFractionDigits="2" /> €
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot class="table-light">
                        <tr>
                            <td colspan="3" class="text-end fw-bold">Total:</td>
                            <td class="text-end fw-bold fs-5 text-success">
                                <fmt:formatNumber value="${pedido.total}" minFractionDigits="2" /> €
                            </td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>