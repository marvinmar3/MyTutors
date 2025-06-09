<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 21/05/25
  Time: 5:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Detalle del Tema | MyTutors</title>
    <link rel="stylesheet" href="/css/verTema.css">
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
                    <c:when test="${tema.tutor != null }"> <!-- && tema.tutorado != null -->
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
                        <a class="btn-chat" href="/chat/tema/${tema.id}">Conversar con el tutorado</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn-chat" href="/chat/tema/${tema.id}">Conversar con el tutor</a>
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
</body>
</html>
