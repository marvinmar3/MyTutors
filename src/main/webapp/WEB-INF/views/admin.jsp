<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 02/06/25
  Time: 12:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Panel de Administración</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-container">
    <h1 class="titulo">Bienvenido admin, ${usuario.nombre}</h1>

    <div class="nav-buttons">
        <a href="${pageContext.request.contextPath}/admin/usuarios">👥 Gestionar Usuarios</a>
        <a href="${pageContext.request.contextPath}/admin/tutorias">📚 Gestionar Tutorías</a>
        <a href="${pageContext.request.contextPath}/admin/reportes">📝 Reportes</a>
        <a href="${pageContext.request.contextPath}/logout">🚪 Cerrar Sesión</a>
    </div>

    <p class="indicacion">Selecciona una opción del menú para comenzar.</p>

    <div class="estadisticas">
        <div class="card">
            <h3>Total de Usuarios</h3>
            <p>${totalUsuarios}</p>
        </div>
        <div class="card">
            <h3>Tutorías Activas</h3>
            <p>${totalTutorias}</p>
        </div>
        <div class="card">
            <h3>Reportes Pendientes</h3>
            <p>${totalReportes}</p>
        </div>
    </div>
</div>
</body>
</html>

