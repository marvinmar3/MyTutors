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
    <title>Registro de Usuario</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Formulario de Registro</h2>
        <form action="/registro" method="post">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre completo</label>
                <input type="text" class="form-control" id="nombre" name="nombre" required>
            </div>
            <div class="mb-3">
                <label for="correo" class="form-label">Correo institucional (@estudiantes.uv.mx)</label>
                <input type="email" class="form-control" id="correo" name="correo" required
                pattern="^[a-zA-Z0-9._%+-]+@estudiantes\\.uv\\.mx$" title="Debe ser un correo institucional válido (@estudiantes.uv.mx)">
            </div>
            <div class="mb-3">
                <label for="facultad" class="form-label">Facultad</label>
                <select id="facultad" name="facultad" class="form-select" required>
                    <option value="">Selecciona tu facultad</option>
                    <c:if test="${not empty facultades}">
                        <c:forEach var="fac" items="${facultades}">
                            <option value="${fac.id}">${fac.nombre}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            <script>
                document.querySelector("form").addEventListener("submit", function (e){
                    const email=document.getElementById("correo");
                    email.value=email.value.trim(); //elimina esacios
                });
            </script>
            <div class="mb-3">
                <label for="carrera" class="form-label">Carrera</label>
                <select id="carrera" name="idCarrera" class="form-select" required>
                    <option value="">Selecciona una carrera</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Contraseña</label>
                <input type="password" class="form-control" id="password" name="password" required minlength="6">
            </div>
            <div class="mb-3">
                <label for="tipoUsuario" class="form-label">Tipo de usuario</label>
                <select class="form-select" id="tipoUsuario" name="tipoUsuario" required>
                    <option value="">Selecciona tu tipo</option>
                    <option value="estudiante">Estudiante</option>
                    <option value="profesor">Profesor</option>
                    <option value="egresado">Egresado</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Registrarse</button>
        </form>
    </div>
    <script>
        $(document).ready(function () {
            $('#facultad').on('change', function () {
                let facultadId = $(this).val();
                $('#carrera').empty().append('<option> Cargando... </option>');

                $.get('/carreras', {id_facultad: facultadId}, function (data) {
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
