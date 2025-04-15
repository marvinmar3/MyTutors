<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 12/04/25
  Time: 11:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
  <h2>Iniciar Sesión</h2>
  <form action="${pageContext.request.contextPath}/procesar-login" method="post">
    Correo: <input type="text" name="username" /><br/>
    Contraseña: <input type="password" name="password" /><br/>
    <input type="submit" value="Ingresar" />
  </form>

</body>
</html>
