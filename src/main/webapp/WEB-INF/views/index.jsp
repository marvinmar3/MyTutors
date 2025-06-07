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

    <!-- Ícono para iOS (pantalla de inicio) -->
    <link rel="apple-touch-icon" sizes="192x192" href="${pageContext.request.contextPath}/img/logo.png">

    <!-- Ícono general (favicon) -->
    <link rel="icon" type="image/png" sizes="192x192" href="${pageContext.request.contextPath}/img/logo.png">

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

<%--<section class="info-seccion">--%>
<%--    <div class="info-bloque">--%>
<%--        <h2>Nuestro Objetivo</h2>--%>
<%--        <p>Mytutors busca conectar a estudiantes y profesores de la Universidad Veracruzana en--%>
<%--        un entorno colaborativo de enseñanza.</p>--%>
<%--    </div>--%>
<%--    <div class="info-bloque">--%>
<%--        <h2>¿Para quién es?</h2>--%>
<%--        <p>Está diseñado para alumnos que necesitan reforsar conocimientos, y docentes que deseen compartir tutorias adicionales.</p>--%>
<%--    </div>--%>
<%--    <div class="info-bloque">--%>
<%--        <h2>¿Que puedes hacer?</h2>--%>
<%--        <p>Ofrece o recibe tutorias, administra tus cursos, chatea con otros usuarios y potencia tu aprendizaje.</p>--%>
<%--    </div>--%>
<%--</section>--%>




</body>
</html>
