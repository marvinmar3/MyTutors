<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 21/05/25
  Time: 5:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Detalle del Tema | MyTutors</title>
    <link rel="stylesheet" href="/css/verTema.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">

</head>
<body>
<div class="contenedor">
    <h1>Detalle del Tema</h1>

    <div class="tarjeta-detalle">
        <h2>${tema.nombre}</h2>
        <p><strong>Materia:</strong> ${tema.materia.nombre}</p>
        <p><strong>Descripción:</strong> ${tema.descripcion}</p>
        <p><strong>Facultad:</strong> ${tema.materia.facultad.nombre}</p>
        <p><strong>Carrera:</strong> ${tema.materia.carrera.nombre}</p>

        <p><strong>Tutor:</strong>
            <c:choose>
                <c:when test="${tema.tutor != null}">
                    ${tema.tutor.nombre}
                </c:when>
                <c:otherwise>
                    Sin asignar
                </c:otherwise>
            </c:choose>
        </p>

        <p><strong>Estado:</strong>
            <c:choose>
                <c:when test="${tema.tutor != null}">
                    En curso
                </c:when>
                <c:otherwise>
                    Disponible
                </c:otherwise>
            </c:choose>
        </p>

        <c:if test="${puedeChatear}">
            <c:choose>
                <c:when test="${rolUsuario == 'tutor'}">
                    <a href="javascript:void(0)" class="btn-ver"
                       onclick="abrirChatDesdeTema(event, ${tema.id}, '${fn:escapeXml(tutorado.nombre)}')">
                        Conversar con el tutorado
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="javascript:void(0)" class="btn-ver"
                       onclick="abrirChatDesdeTema(event, ${tema.id}, '${fn:escapeXml(tutor.nombre)}')">
                        Conversar con el tutor
                    </a>
                </c:otherwise>
            </c:choose>
        </c:if>

        <c:choose>
            <c:when test="${origen == 'aprendizaje'}">
                <a href="${pageContext.request.contextPath}/temas/aprendizaje" class="btn-volver">← Volver a aprendizajes</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/home" class="btn-volver">← Volver a inicio</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Contenedor de ventanas flotantes tipo Messenger -->
<div id="ventanas-messenger-container"
     style="position: fixed; bottom: 0; right: 0; display: flex; flex-direction: row-reverse; gap: 20px; z-index: 2000;">
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    var usuarioIdGlobal = ${usuario.id};
</script>

<!-- SockJS y STOMP -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

<!-- Tu script JS de chat -->
<script src="${pageContext.request.contextPath}/js/chat.js"></script>

<script>
    console.log("📦 chat.js cargado correctamente desde verTema.jsp");
</script>



</body>
</html>
