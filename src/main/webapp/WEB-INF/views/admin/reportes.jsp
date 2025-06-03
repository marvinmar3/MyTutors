<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 02/06/25
  Time: 4:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reportes Pendientes</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>

<a href="${pageContext.request.contextPath}/admin" class="btn-volver">⬅ Volver</a>

  <h1>Reportes Pendientes</h1>

  <c:choose>
    <c:when test="${empty reportes}">
      <p>No hay reportes pendientes.</p>
    </c:when>
    <c:otherwise>
      <table class="tabla-usuarios">
        <thead>
        <tr>
          <th>ID</th>
          <th>Motivo</th>
          <th>Descripción</th>
          <th>Fecha</th>
          <th>Usuario Emisor</th>
          <th>ID Tutoría</th>
          <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="r" items="${reportes}">
          <tr>
            <td>${r.id}</td>
            <td>${r.motivo}</td>
            <td>${r.descripcion}</td>
            <td>${r.fechaCreado}</td>
            <td>${r.emisor.nombre}</td>
            <td>
              <c:choose>
                <c:when test="${r.temaReportado != null}">
                  ${r.temaReportado.id}
                </c:when>
                <c:otherwise>-</c:otherwise>
              </c:choose>
            </td>
            <td>
              <form action="${pageContext.request.contextPath}/admin/reportes/cambiarEstado" method="post" style="display:inline;">
                <input type="hidden" name="idReporte" value="${r.id}" />
                <select name="nuevoEstado">
                  <option value="REVISADO">Revisado</option>
                  <option value="DESCARTADO">Descartado</option>
                </select>
                <button type="submit">Actualizar</button>
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
