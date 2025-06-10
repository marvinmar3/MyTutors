<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 12/04/25
  Time: 11:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%--@ taglib prefix="c" uri="jakarta.tags.core" --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro | MyTutors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body class="dark-bg">
<div class="login-glow-container">
    <div class="login-circle"></div>
    <div class="login-box">
        <h2>Registro</h2>
        <c:if test="${not empty error}">
            <div style="color: rgba(255,13,13,0.56); font-weight: bold;">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/registro" method="post">
            <input type="text" name="nombre" placeholder="Nombre completo" required>

            <input type="email" id="correo" name="correo" class="form-control" required
                   placeholder="Correo institucional (@estudiantes.uv.mx)"
                   title="Debe ser un correo institucional válido (@estudiantes.uv.mx)">

            <select id="facultad" name="facultad" required>
                <option value="">Selecciona tu facultad</option>
                <c:if test="${not empty facultades}">
                    <c:forEach var="fac" items="${facultades}">
                        <option value="${fac.id}">${fac.nombre}</option>
                    </c:forEach>
                </c:if>
            </select>

            <select id="carrera" name="idCarrera" required>
                <option value="">Selecciona una carrera</option>
            </select>

            <input type="password" id="password" name="password" placeholder="Contraseña" required minlength="6">

            <select id="rol_en_app" name="rolEnApp" required>
                <option value="">Selecciona tu rol en la app</option>
                <option value="tutor">Tutor</option>
                <option value="tutorado">Tutorado</option>
                <option value="ambos">Ambos</option>
            </select>

            <button type="submit">Registrarse</button>
        </form>

        <div class="login-links">
            <a href="${pageContext.request.contextPath}/login">¿Ya tienes cuenta? Inicia sesión</a>
        </div>
    </div>
</div>

<script>
    // Limpieza de espacios en el correo
    document.querySelector("form").addEventListener("submit", function (e) {
        const email = document.getElementById("correo");
        email.value = email.value.trim();
    });

    // AJAX para cargar carreras por facultad
    $(document).ready(function () {
        $('#facultad').on('change', function () {
            let facultadId = $(this).val();
            $('#carrera').empty().append('<option> Cargando... </option>');

            $.get('${pageContext.request.contextPath}/carreras', {id_facultad: facultadId}, function (data) {
                $('#carrera').empty();
                if (data.length === 0) {
                    $('#carrera').append('<option value="">No hay carreras disponibles</option>');
                } else {
                    $('#carrera').append('<option value="">Selecciona una carrera</option>');
                    $.each(data, function (i, carrera) {
                        $('#carrera').append('<option value="' + carrera.id + '">' + carrera.nombre + '</option>');
                    });
                }
            });
        });
    });
</script>
</body>
</html>
