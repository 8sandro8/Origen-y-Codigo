<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error 500 - Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .error-icon { animation: pulse 2s infinite; }
        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.1); }
            100% { transform: scale(1); }
        }
    </style>
</head>
<body class="bg-light">

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-6 text-center">
                <div class="card shadow-sm p-5">
                    <i class="bi bi-cloud-slash-fill text-danger error-icon" style="font-size: 5rem;"></i>
                    <h2 class="mt-4 text-warning">¡Uy! Algo ha fallado</h2>
                    <p class="text-muted mt-3 fs-5">
                        Nuestro equipo ya está trabajando para solucionarlo.<br>
                        Disculpa las molestias.
                    </p>
                    <c:if test="${pageContext.errorData != null}">
                        <div class="alert alert-secondary mt-3 text-start">
                            <small><strong>Código:</strong> ${pageContext.errorData.statusCode}</small><br>
                            <small><strong>URI:</strong> ${pageContext.errorData.requestURI}</small>
                        </div>
                    </c:if>
                    <div class="mt-4">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-warning btn-lg">
                            <i class="bi bi-house-door"></i> Volver al Inicio
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
