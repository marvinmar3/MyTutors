<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 08/06/25
  Time: 1:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="now" class="java.util.Date" scope="page" />

<html>
<head>
  <title>Mis Aprendizajes</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <style>
    .completo {
      background-color: #dbeafe; /* azul claro */
      border: 1px solid #3b82f6;
    }
  </style>
</head>
<body class="dark-bg">
<h1 style="text-align:center;">Mis aprendizajes</h1>

<main class="usuarios-container">
  <c:if test="${empty aprendizajes}">
    <p style="color: #118acb; text-align: center;">⚠️ No tienes aprendizajes asignados todavía.</p>
  </c:if>

  <c:forEach var="vista" items="${aprendizajes}">
    <c:set var="tema" value="${vista.tema}" />
    <c:set var="tutor" value="${vista.tutor}" />
    <c:set var="tutorado" value="${vista.tutorado}" />
    <c:set var="esCompleto" value="${not empty tutor and not empty tutorado}" />

    <div class="tarjeta-usuario ${esCompleto ? 'completo' : ''}">

      <!-- Imagen del tutor si existe -->
      <c:choose>
        <c:when test="${not empty tutor and not empty tutor.rutaFoto}">
          <img class="foto-perfil" src="${pageContext.request.contextPath}${tutor.rutaFoto}?v=${now.time}" alt="Foto del tutor">
        </c:when>
        <c:otherwise>
          <img class="foto-perfil" src="${pageContext.request.contextPath}/img/default-user.jpeg" alt="Foto por defecto">
        </c:otherwise>
      </c:choose>

      <h3>${tema.nombre}</h3>

      <p><strong>Materia:</strong>
        <c:choose>
          <c:when test="${not empty tema.materia}">
            ${tema.materia.nombre}
          </c:when>
          <c:otherwise><em>Sin asignar</em></c:otherwise>
        </c:choose>
      </p>

      <p><strong>Descripción:</strong> ${tema.descripcion}</p>

      <p><strong>Tutor:</strong>
        <c:choose>
          <c:when test="${not empty tutor}">
            ${tutor.nombre}
          </c:when>
          <c:otherwise>No asignado</c:otherwise>
        </c:choose>
      </p>

      <p><strong>Tutorado:</strong>
        <c:choose>
          <c:when test="${not empty tutorado}">
            ${tutorado.nombre}
          </c:when>
          <c:otherwise>No asignado</c:otherwise>
        </c:choose>
      </p>

      <a href="${pageContext.request.contextPath}/temas/ver?idTema=${tema.id}" class="btn-ver">Ver tema</a>
    </div>
  </c:forEach>
</main>
</body>
</html>
