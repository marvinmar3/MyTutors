<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 15/04/25
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Inicio | MyTutors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="dark-bg">

<header class="header">
  <h1>MyTutors</h1>
  <p>Conectando estudiantes con tutores de la UV</p>
</header>

<main class="usuarios-container">
    <c:if test="${empty temas}">
        <p style="color: #118acb; text-align: center;">⚠️ No hay temas disponibles por el momento.</p>
    </c:if>

    <c:forEach var="tema" items="${temas}">
        <div class="tarjeta-usuario">
            <img src="<c:out value='${tema.tutor.rutaFoto != null ? tema.tutor.rutaFoto : "/img/default-user.png"}'/>"
                 alt="Foto de perfil"
                 class="foto-perfil"
                 width="80" height="80">
            <h3>${tema.nombre}</h3>
            <p><strong>Materia:</strong> ${tema.materia.nombre}</p>
            <p><strong>Descripción:</strong> ${tema.descripcion}</p>
            <p><strong>Tutor:</strong> ${tema.tutor.nombre}</p>

            <a href="${pageContext.request.contextPath}/perfil-tutor?id=${tema.tutor.id}" class="btn-ver">Ver Perfil</a>
        </div>
    </c:forEach>
</main>

</body>
</html>
