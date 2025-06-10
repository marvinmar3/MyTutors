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
<jsp:useBean id="usuarioSesion" class="com.mytutors.mytutors.model.Usuario" scope="session" />

<html>
<head>
  <title>Mis Aprendizajes</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aprendizaje.css">
</head>

<body class="dark-bg">

<header>
  <a href="${pageContext.request.contextPath}/home" class="btn-volver">← Volver a inicio</a>
  <h1 class="titulo-aprendizaje">Mis Aprendizajes</h1>
  <span style="width:130px;"></span>
</header>

<main class="usuarios-container">
  <c:if test="${empty aprendizajes}">
    <p class="mensaje-vacio">⚠️ No tienes aprendizajes asignados todavía.</p>
  </c:if>

  <c:forEach var="vista" items="${aprendizajes}">
    <c:set var="tema" value="${vista.tema}" />
    <c:set var="tutor" value="${vista.tutor}" />
    <c:set var="tutorado" value="${vista.tutorado}" />
    <c:set var="esCompleto" value="${not empty tutor and not empty tutorado}" />

    <div class="tarjeta-usuario ${esCompleto ? 'completo' : ''}">

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
          <c:when test="${not empty tema.materia}">${tema.materia.nombre}</c:when>
          <c:otherwise><em>Sin asignar</em></c:otherwise>
        </c:choose>
      </p>

      <p><strong>Descripción:</strong> ${tema.descripcion}</p>

      <p><strong>Tutor:</strong>
        <c:choose>
          <c:when test="${not empty tutor}">${tutor.nombre}</c:when>
          <c:otherwise>No asignado</c:otherwise>
        </c:choose>
      </p>

      <p><strong>Tutorado:</strong>
        <c:choose>
          <c:when test="${not empty tutorado}">${tutorado.nombre}</c:when>
          <c:otherwise>No asignado</c:otherwise>
        </c:choose>
      </p>

      <a href="${pageContext.request.contextPath}/temas/ver?idTema=${tema.id}&origen=aprendizaje" class="btn-ver">Ver tema</a>

      <c:if test="${esCompleto and (usuarioSesion.id == tutor.id or usuarioSesion.id == tutorado.id)}">
        <c:choose>
          <c:when test="${usuarioSesion.id == tutorado.id}">
            <button class="btn-chat"
                    onclick="abrirChatMessenger(${vista.conversacion.id}, 'Chat con ${tutor.nombre}')">
              Conversar con el tutor
            </button>
          </c:when>
          <c:when test="${usuarioSesion.id == tutor.id}">
            <button class="btn-chat"
                    onclick="abrirChatMessenger(${vista.conversacion.id}, 'Chat con ${tutorado.nombre}')">
              Conversar con el tutorado
            </button>
          </c:when>
        </c:choose>
      </c:if>
    </div>
  </c:forEach>
</main>
</body>
</html>
