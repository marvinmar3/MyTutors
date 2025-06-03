<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 02/06/25
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestión de Tutotrías</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">

</head>
<body>

<a href="${pageContext.request.contextPath}/admin" class="btn-volver">⬅ Volver</a>

<br>
<br>
<h1>Gestión de Tutorías</h1>

<table class="tabla-usuarios">
  <thead>
  <tr>
    <th>ID</th>
    <th>Título</th>
    <th>Descripción</th>
    <th>Materia</th>
    <th>Facultad</th>
    <th>Carrera</th>
    <th>Rol</th>
    <th>Acciones</th>
  </tr>
  </thead>
  <tbody id="tablaTutorias">
  <c:forEach var="t" items="${tutorias}">
    <tr>
      <td>${t.id}</td>
      <td>${t.nombre}</td>
      <td>${t.descripcion}</td>
      <td>${t.materia.nombre}</td>
      <td>${t.materia.facultad.nombre}</td>
      <td>${t.materia.carrera.nombre}</td>
      <td>${t.rol}</td>
      <td>
        <form method="post" action="${pageContext.request.contextPath}/admin/tutorias/eliminar">
          <input type="hidden" name="id" value="${t.id}" />
          <button type="submit">Eliminar</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>
