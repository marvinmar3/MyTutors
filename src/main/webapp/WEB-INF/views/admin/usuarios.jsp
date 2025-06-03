<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 02/06/25
  Time: 4:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Gestion de Usuarios</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<a href="${pageContext.request.contextPath}/admin" class="btn-volver">⬅ Volver</a>

<br>
<br>
<h1>Gestión de Usuarios</h1>

<c:choose>
  <c:when test="${empty usuarios}">
    <p>No hay usuarios registrados.</p>
  </c:when>
  <c:otherwise>
    <table class="tabla-usuarios">
      <thead>
      <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Correo</th>
        <th>Rol</th>
        <th>Estado</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="u" items="${usuarios}">
        <tr>
          <td>${u.id}</td>
          <td>${u.nombre}</td>
          <td>${u.correo}</td>
          <td>${u.rolEnApp}</td>
          <td>
            <c:choose>
              <c:when test="${u.activo}">Activo</c:when>
              <c:otherwise>Inactivo</c:otherwise>
            </c:choose>
          </td>
          <td>
            <form action="${pageContext.request.contextPath}/admin/usuarios/cambiarEstado" method="post" style="display:inline;">
              <input type="hidden" name="idUsuario" value="${u.id}" />
              <input type="hidden" name="activo" value="${!u.activo}" />
              <button type="submit">
                <c:choose>
                  <c:when test="${u.activo}">Desactivar</c:when>
                  <c:otherwise>Activar</c:otherwise>
                </c:choose>
              </button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:otherwise>
</c:choose>
</body>
</html>
