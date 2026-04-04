<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/components/navbar.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi Perfil - Origen &amp; Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

    <div class="container py-4">
        <h2 class="mb-4"><i class="bi bi-person-circle"></i> Mi Perfil</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i>
                ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Tabs Bootstrap -->
        <ul class="nav nav-tabs" id="perfilTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="datos-tab" data-bs-toggle="tab" data-bs-target="#datos" type="button" role="tab">
                    <i class="bi bi-person"></i> Datos Personales
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="password-tab" data-bs-toggle="tab" data-bs-target="#password" type="button" role="tab">
                    <i class="bi bi-key"></i> Cambiar Contraseña
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="pedidos-tab" data-bs-toggle="tab" data-bs-target="#pedidos" type="button" role="tab">
                    <i class="bi bi-bag"></i> Mis Pedidos
                </button>
            </li>
        </ul>

        <div class="tab-content" id="perfilTabsContent">
            <!-- Pestaña 1: Datos Personales -->
            <div class="tab-pane fade show active" id="datos" role="tabpanel">
                <div class="card shadow-sm mt-3">
                    <div class="card-body">
                        <h5 class="card-title mb-4"><i class="bi bi-person-lines-fill"></i> Información Personal</h5>
                        
                        <form method="post" action="${pageContext.request.contextPath}/profile">
                            <input type="hidden" name="accion" value="actualizarPerfil">
                            
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <div class="input-group">
                                    <span class="input-group-text">
                                        <i class="bi bi-person"></i>
                                    </span>
                                    <input type="text" class="form-control" id="nombre" name="nombre" 
                                           value="${sessionScope.usuario.nombre}" required>
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <div class="input-group">
                                    <span class="input-group-text">
                                        <i class="bi bi-envelope"></i>
                                    </span>
                                    <input type="email" class="form-control" id="email" name="email" 
                                           value="${sessionScope.usuario.email}" required>
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Fecha de Registro</label>
                                <div class="input-group">
                                    <span class="input-group-text">
                                        <i class="bi bi-calendar"></i>
                                    </span>
                                    <input type="text" class="form-control" value="${sessionScope.usuario.fechaRegistro}" readonly>
                                </div>
                            </div>
                            
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-check-lg me-2"></i>
                                    Guardar Cambios
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Pestaña 2: Cambiar Contraseña -->
            <div class="tab-pane fade" id="password" role="tabpanel">
                <div class="card shadow-sm mt-3">
                    <div class="card-body">
                        <h5 class="card-title mb-4"><i class="bi bi-shield-lock"></i> Cambiar Contraseña</h5>
                        
                        <form method="post" action="${pageContext.request.contextPath}/profile">
                            <input type="hidden" name="accion" value="cambiarContrasena">
                            
                            <div class="mb-3">
                                <label for="contrasenaActual" class="form-label">Contraseña Actual</label>
                                <div class="input-group">
                                    <span class="input-group-text">
                                        <i class="bi bi-lock"></i>
                                    </span>
                                    <input type="password" class="form-control" id="contrasenaActual" name="contrasenaActual" required>
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="nuevaContrasena" class="form-label">Nueva Contraseña</label>
                                <div class="input-group">
                                    <span class="input-group-text">
                                        <i class="bi bi-key"></i>
                                    </span>
                                    <input type="password" class="form-control" id="nuevaContrasena" name="nuevaContrasena" 
                                           required minlength="8" placeholder="Mínimo 8 caracteres">
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="confirmarContrasena" class="form-label">Confirmar Nueva Contraseña</label>
                                <div class="input-group">
                                    <span class="input-group-text">
                                        <i class="bi bi-key-fill"></i>
                                    </span>
                                    <input type="password" class="form-control" id="confirmarContrasena" name="confirmarContrasena" required>
                                </div>
                            </div>
                            
                            <div class="d-grid">
                                <button type="submit" class="btn btn-warning">
                                    <i class="bi bi-arrow-repeat me-2"></i>
                                    Cambiar Contraseña
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Pestaña 3: Mis Pedidos -->
            <div class="tab-pane fade" id="pedidos" role="tabpanel">
                <div class="card shadow-sm mt-3">
                    <div class="card-body">
                        <h5 class="card-title mb-4"><i class="bi bi-bag-fill"></i> Historial de Pedidos</h5>
                        
                        <iframe src="${pageContext.request.contextPath}/list-pedidos" 
                                style="width: 100%; height: 400px; border: 1px solid #dee2e6; border-radius: 4px;"
                                title="Mis Pedidos"></iframe>
                    </div>
                </div>
            </div>
        </div>

        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Volver al Inicio
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
