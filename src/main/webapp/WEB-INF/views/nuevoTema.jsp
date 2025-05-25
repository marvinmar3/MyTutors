<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 25/05/25
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Nuevo Tema | MyTutors</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>

  <div id="formularioTutoria">
    <form action="${pageContext.request.contextPath}/temas/crear" method="post">
      <input type="hidden" name="idUsuario" value="${usuario.id}" />

      <label>Título:</label>
      <input type="text" name="nombre" required class="form-control"/>

      <label>Descripción:</label>
      <textarea name="descripcion" class="form-control" required></textarea>

      <label>Facultad:</label>
      <select name="id_facultad" id="facultad" required>
        <option value="0">Selecciona una facultad</option>
        <c:forEach var="f" items="${facultades}">
          <option value="${f.id}">${f.nombre}</option>
        </c:forEach>
      </select>

      <label>Carrera:</label>
      <select id="carrera" name="id_carrera" required>
        <option value="">Selecciona una carrera</option>
      </select>


      <label>Materia:</label>
      <select id="materia" name="id_materia" required>
        <option value="">Selecciona una materia</option>
        <c:forEach var="m" items="${materias}">
          <option value="${m.id}">${m.nombre}</option>
        </c:forEach>
        <option value="otra">Otra...</option>
      </select>

      <!-- Mostrar solo si tipoUsuario es "ambos" -->
      <c:if test="${usuario.rolEnApp == 'ambos'}">
        <label>Rol en esta tutoría:</label>
        <select name="rol" class="form-control">
          <option value="tutor">Tutor</option>
          <option value="tutorado">Tutorado</option>
        </select>
      </c:if>

      <div id="materiaNuevaDiv" style="display:none;">
        <label>Nombre de la nueva materia:</label>
        <input type="text" name="nuevaMateria" id="nuevaMateria" class="form-control"/>
      </div>

      <!-- Si NO es "ambos", mandamos el rol directamente -->
      <c:if test="${usuario.tipoUsuario != 'ambos'}">
        <input type="hidden" name="rol" value="${usuario.rolEnApp}" />
      </c:if>
      <br/>
      <br>
      <button type="submit" class="btn btn-success">Crear</button>
    </form>
  </div>
  <c:if test="${not empty success}">
    <div class="alert alert-success">${success}</div>
  </c:if>
  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>


  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script>
    $(document).ready(function () {
      $('#facultad').on('change', function () {
        let facultadId = $(this).val();
        $('#carrera').empty().append('<option>Cargando...</option>');
        $('#materia').html('<option value="">Selecciona una materia</option>');

        $.get('/temas/carreras', { id_facultad: facultadId }, function (data) {
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

      $('#carrera').on('change', function () {
        let carreraId = $(this).val();
        $('#materia').html('<option value="">Cargando materias...</option>');

        $.get('/temas/materias', { id_carrera: carreraId }, function (data) {
          $('#materia').empty().append('<option value="">Selecciona una materia</option>');
          $.each(data, function (i, materia) {
            $('#materia').append('<option value="' + materia.id + '">' + materia.nombre + '</option>');
          });
          $('#materia').append('<option value="otra">Otra...</option>');
        });
      });

      $('#materia').on('change', function () {
        if ($(this).val() === 'otra') {
          $('#materiaNuevaDiv').show();
          $('#nuevaMateria').prop('required', true);
        } else {
          $('#materiaNuevaDiv').hide();
          $('#nuevaMateria').prop('required', false);
        }
      });
    });
  </script>


  <%--  <script>
    function agregarListenersEncadenamiento() {
      const facultadSelect = document.getElementById("facultad");
      const carreraSelect = document.getElementById("carrera");
      const materiaSelect = document.getElementById("materia");
      const divNueva = document.getElementById("materiaNuevaDiv");
      const nuevaMateriaInput = document.getElementById("nuevaMateria");

      let ultimaFacultad = null;

      facultadSelect.addEventListener("change", function () {
        const idFacultad = Number(this.value);

        console.log("🔍 Facultad seleccionada:", idFacultad);

        if (!idFacultad || isNaN(idFacultad) || idFacultad <= 0 || idFacultad === ultimaFacultad) {
          console.warn("⚠️ Facultad no válida o ya procesada:", idFacultad);
          return;
        }

        ultimaFacultad = idFacultad;

        carreraSelect.innerHTML = '<option value="">Cargando carreras...</option>';
        materiaSelect.innerHTML = '<option value="">Selecciona una materia</option>';

        // ✅ Aquí está bien interpolado
        fetch(`/temas/carreras?idFacultad=${idFacultad}`)
                .then(resp => resp.json())
                .then(data => {
                  carreraSelect.innerHTML = '<option value="">Selecciona una carrera</option>';

                  if (!Array.isArray(data)) {
                    console.error("❌ Respuesta de carreras no es un arreglo:", data);
                    return;
                  }

                  data.forEach(c => {
                    const opt = document.createElement("option");
                    opt.value = c.id;
                    opt.textContent = c.nombre;
                    carreraSelect.appendChild(opt);
                  });
                })
                .catch(err => {
                  console.error("❌ Error cargando carreras:", err);
                });
      });

      carreraSelect.addEventListener("change", function () {
        const idCarrera = this.value;

        if (!idCarrera) {
          materiaSelect.innerHTML = '<option value="">Selecciona una materia</option>';
          return;
        }

        materiaSelect.innerHTML = '<option value="">Cargando materias...</option>';

        fetch(`/temas/materias?id_carrera=${idCarrera}`)
                .then(resp => resp.json())
                .then(data => {
                  materiaSelect.innerHTML = '<option value="">Selecciona una materia</option>';

                  data.forEach(m => {
                    const opt = document.createElement("option");
                    opt.value = m.id;
                    opt.textContent = m.nombre;
                    materiaSelect.appendChild(opt);
                  });

                  const otra = document.createElement("option");
                  otra.value = "otra";
                  otra.textContent = "Otra...";
                  materiaSelect.appendChild(otra);
                })
                .catch(err => console.error("Error cargando materias:", err));
      });

      materiaSelect.addEventListener("change", function () {
        if (this.value === "otra") {
          divNueva.style.display = "block";
          nuevaMateriaInput.required = true;
        } else {
          divNueva.style.display = "none";
          nuevaMateriaInput.required = false;
        }
      });
    }

    window.addEventListener("DOMContentLoaded", agregarListenersEncadenamiento);
  </script>--%>

</body>
</html>
