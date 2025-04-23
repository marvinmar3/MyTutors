<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 15/04/25
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Inicio | MyTutors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script>
        function toggleSidebar() {
            document.getElementById('sidebar').classList.toggle('sidebar-open');
        }
    </script>
</head>
<body>

<!-- Botón hamburguesa -->
<button class="menu-btn" onclick="toggleSidebar()">☰</button>

<!-- Encabezado -->
<header class="header">
    <h1>MyTutors</h1>
    <p>Conectando estudiantes con tutores de la UV</p>
</header>

<!-- Sidebar oculto -->
<sec:authorize access="isAuthenticated()">
    <div class="sidebar" id="sidebar">
        <div class="sidebar-user">
            <c:choose>
                <c:when test="${not empty usuario.rutaFoto}">
                    <img class="perfil-imagen" src="${pageContext.request.contextPath}${usuario.rutaFoto}" alt="Perfil">
                </c:when>
                <c:otherwise>
                    <img class="perfil-imagen" src="${pageContext.request.contextPath}/img/default-user.jpeg" alt="Perfil">
                </c:otherwise>
            </c:choose>
            <p><strong>${nombreUsuario}</strong></p>
        </div>
        <nav class="sidebar-nav">
            <a href="${pageContext.request.contextPath}/perfil">Mi perfil</a>
            <a href="${pageContext.request.contextPath}/aprendizaje">Mi aprendizaje</a>
            <a href="${pageContext.request.contextPath}/grupos">Mis grupos</a>
            <a href="${pageContext.request.contextPath}/configuracion">Configuración</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Cerrar sesión</a>
        </nav>
    </div>
</sec:authorize>

<!-- Tarjetas de temas -->
<main class="usuarios-container">
    <c:if test="${empty temas}">
        <p style="color: #118acb; text-align: center;">⚠️ No hay temas disponibles por el momento.</p>
    </c:if>

    <c:forEach var="tema" items="${temas}">
        <div class="tarjeta-usuario">
            <c:choose>
                <c:when test="${not empty tema.tutor.rutaFoto}">
                    <img class="foto-perfil" src="${tema.tutor.rutaFoto}" alt="Foto de perfil">
                </c:when>
                <c:otherwise>
                    <img class="foto-perfil" src="${pageContext.request.contextPath}/img/default-user.jpeg" alt="Foto de perfil">
                </c:otherwise>
            </c:choose>

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
