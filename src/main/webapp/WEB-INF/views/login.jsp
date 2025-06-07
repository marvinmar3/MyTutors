<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 12/04/25
  Time: 11:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login | MyTutors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body class="dark-bg">
  <div class="login-glow-container">
    <div class="login-circle"></div>
    <div class="login-box">
      <h2>Login</h2>
      <c:if test="${not empty error}">
        <div style="color: rgba(255,13,13,0.56); font-weight: bold;">${error}</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="email" name="correo" placeholder="Correo institucional" required
<%--               pattern="^[\\w.-]+@estudiantes\\.uv\\.mx$"--%>
               title="Ingresa tu correo institucional.">
        <input type="password" name="password" placeholder="Contraseña" required>
        <button type="submit">Ingresar</button>
      </form>
      <div class="login-links">
        <a href="#">¿Olvidaste tu contraseña?</a>
        <a href="${pageContext.request.contextPath}/registro">Registrarse</a>
      </div>
    </div>

  </div>

</body>
</html>