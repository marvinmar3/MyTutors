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
</head>
<body>
    <h2>Mi perfil</h2>
    <img class="perfil" src="${usuario.imagenPerfil != null ? usuario.imagenPerfil : '/img/default-user.jpeg'}" alt="Foto de perfil"><br>

    <form method="post" action="${pageContext.request.contextPath}/perfil/actualizar" enctype="multipart/form-data">
        <label>Nombre completo:</label>
        <input type="text" name="nombreCompleto" value="${usuario.nombre}" required />

        <label>Facultad</label>
        <input type="text" name="facultad" value="${usuario.facultad}" required />

        <label>Carrera:</label>
        <input type="text" name="carrera" value="${usuario.carrera}" required />

        <label>Imagen de perfil:</label>
        <input type="file" name="imagen" accept="image/*" />

        <input type="submit" value="Guardar cambios" />
    </form>

</body>
</html>
