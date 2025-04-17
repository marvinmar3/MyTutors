<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 12/04/25
  Time: 11:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>MyTutors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
  <h1>📚MyTutors📚</h1>
  <div class="typewriter-container">
      <p class="typewriter">Porque aprender también es ayudar: descubre tu tutor ideal.</p>
  </div>

  <div class="btn-container">
      <a href="${pageContext.request.contextPath}/login" class="btn">Iniciar Sesión</a>
      <a href="${pageContext.request.contextPath}/registro" class="btn">Registrarse</a>
  </div>
</body>
</html>
