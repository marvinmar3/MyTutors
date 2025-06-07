<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 18/04/25
  Time: 6:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Mi Perfil | MyTutors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/perfil.css">
    <script>
        function mostrarFormularioEdicion()
        {
            const formEditar = document.getElementById('form-editar');
            const modoLectura = document.getElementById('modo-lectura');

            formEditar.style.visibility = 'visible';
            formEditar.style.height = 'auto';
            formEditar.style.overflow = 'visible';

            modoLectura.style.display = 'none';
        }
    </script>
</head>
<body>
    <c:if test="${empty sessionScope.usuario}">
        <c:redirect url="/login"/>
    </c:if>
    <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/home'" class="btn-volver">← Volver</button>
    <h2>Mi perfil</h2>

    <div id="modo-lectura">
        <c:choose>
            <c:when test="${not empty usuario.rutaFoto}">
                <img class="perfil" src="${pageContext.request.contextPath}${usuario.rutaFoto}" alt="Foto de perfil">
            </c:when>
            <c:otherwise>
                <img class="perfil" src="${pageContext.request.contextPath}/img/default-user.jpeg" alt="Foto por defecto">
            </c:otherwise>
        </c:choose>

        <p><strong>Nombre Completo: </strong> ${usuario.nombre}</p>
        <p><strong>Correo:</strong> ${usuario.correo}</p>
        <p>
            <strong>Tipo de usuario:</strong>
            <c:choose>
                <c:when test="${usuario.tipoUsuario == 'profesor'}">👨‍🏫 Profesor</c:when>
                <c:when test="${usuario.tipoUsuario == 'estudiante'}">🎓 Estudiante</c:when>
                <c:otherwise>👤 ${usuario.tipoUsuario}</c:otherwise>
            </c:choose>
        </p>

        <p><strong>Carrera: </strong> ${usuario.carrera.nombre}</p>
        <p><strong>Facultad: </strong> ${usuario.facultad.nombre}</p>
        <p>
            <strong>Rol en la app:</strong>
            <c:choose>
                <c:when test="${usuario.rolEnApp == 'tutor'}">📘 Tutor</c:when>
                <c:when test="${usuario.rolEnApp == 'tutorado'}">📗 Tutorado</c:when>
                <c:otherwise>📚 Tutor/Tutorado</c:otherwise>
            </c:choose>
        </p>



        <button onclick="mostrarFormularioEdicion()">Modificar</button>
    </div>


    <div id="form-editar" style="visibility: hidden; height: 0; overflow: hidden">

        <form method="post" action="${pageContext.request.contextPath}/perfil/actualizar" enctype="multipart/form-data">
            <label>Nombre completo:</label>

            <input type="text" name="nombre" value="${usuario.nombre}" required />

            <label>Correo institucional:</label>
            <input type="text" value="${usuario.correo}" readonly />

            <label>Tipo de usuario:</label>
            <input type="text" value="${usuario.tipoUsuario}" readonly />

            <label>Carrera:</label>
            <select id="carrera" name="carreraId">
                <c:forEach var="carrera" items="${carreras}">
                    <option value="${carrera.id}" <c:if test="${carrera.id == usuario.carrera.id}">selected</c:if>>
                            ${carrera.nombre}
                    </option>
                </c:forEach>
            </select>

            <label>Facultad:</label>
            <select name="facultadId" id="facultad">
                <c:forEach var="facultad" items="${facultades}">
                    <option value="${facultad.id}" <c:if test="${facultad.id == usuario.facultad.id}">selected</c:if>>
                            ${facultad.nombre}
                    </option>
                </c:forEach>
            </select>

            <label>Rol en la app:</label>
            <input type="text" value="${usuario.rolEnApp}" readonly />

            <label>Imagen de perfil:</label>
            <input type="file" name="imagen" accept="image/*" />

            <input type="submit" value="Guardar cambios" />
        </form>
    </div>


</body>
</html>
