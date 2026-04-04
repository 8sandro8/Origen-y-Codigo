<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/components/navbar.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito - Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

    <div class="container">
        <h2 class="mb-4"><i class="bi bi-cart"></i> Tu Carrito</h2>

        <c:choose>
            <c:when test="${empty sessionScope.carrito}">
                <div class="text-center py-5">
                    <i class="bi bi-cart-x text-muted" style="font-size: 4rem;"></i>
                    <p class="text-muted mt-3 fs-5">Tu carrito está vacío</p>
                    <a href="${pageContext.request.contextPath}/list-productos" class="btn btn-primary">
                        <i class="bi bi-grid-3x3-gap"></i> Ver Catálogo
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="card shadow-sm">
                    <div class="card-body p-0">
                        <table class="table table-hover mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>Producto</th>
                                    <th>Precio</th>
                                    <th>Cantidad</th>
                                    <th>Subtotal</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${sessionScope.carrito}">
                                    <tr>
                                        <td><strong>${item.producto.nombre}</strong></td>
                                        <td><fmt:formatNumber value="${item.producto.precio}" minFractionDigits="2" /> €</td>
                                        <td>${item.cantidad}</td>
                                        <td><fmt:formatNumber value="${item.subtotal}" minFractionDigits="2" /> €</td>
                                    </tr>
                                </c:forEach>
                                <tr class="table-secondary">
                                    <td colspan="3" class="text-end"><strong>Total:</strong></td>
                                    <td><strong>
                                        <c:set var="total" value="0" />
                                        <c:forEach var="item" items="${sessionScope.carrito}">
                                            <c:set var="total" value="${total + item.subtotal}" />
                                        </c:forEach>
                                        <fmt:formatNumber value="${total}" minFractionDigits="2" /> €
                                    </strong></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="mt-4 text-end">
                    <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left"></i> Seguir Comprando
                    </a>
                    <form action="${pageContext.request.contextPath}/checkout" method="POST" class="d-inline ms-2">
                        <button type="submit" class="btn btn-success">
                            <i class="bi bi-credit-card"></i> Finalizar Compra
                        </button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>