<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 15/04/25
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Inicio | MyTutors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script>
        function toggleSidebar() {
            document.getElementById('sidebar').classList.toggle('sidebar-open');
        }
    </script>
</head>
<body>

<!-- Botón hamburguesa -->
<button class="menu-btn" onclick="toggleSidebar()">☰</button>
<!-- <button class="btn-chat" onclick="toggleChatPanel()">💬 Mis Conversaciones</button> -->
<button class="btn-chat" onclick="cargarConversaciones()">💬 Mis Conversaciones</button>

<!-- Encabezado -->
<header class="header">
    <h1>MyTutors</h1>
    <p>Conectando estudiantes con tutores de la UV</p>
</header>

<!-- Sidebar oculto -->
<c:if test="${not empty sessionScope.usuario}">
    <div class="sidebar" id="sidebar">
        <div class="sidebar-user">
            <c:choose>
                <c:when test="${not empty usuario.rutaFoto}">
                    <img class="perfil-imagen" src="${pageContext.request.contextPath}/img/usuarios/${usuario.rutaFoto}" alt="Perfil">
                </c:when>
                <c:otherwise>
                    <img class="perfil-imagen" src="${pageContext.request.contextPath}/img/default-user.jpeg" alt="Perfil">
                </c:otherwise>
            </c:choose>
            <p><strong>${nombreUsuario}</strong></p>
        </div>
        <nav class="sidebar-nav">
            <a href="${pageContext.request.contextPath}/perfil">Mi perfil</a>
            <a href="${pageContext.request.contextPath}/aprendizaje">Mi aprendizaje</a>
            <a href="${pageContext.request.contextPath}/grupos">Mis grupos</a>
            <a href="${pageContext.request.contextPath}/configuracion">Configuración</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Cerrar sesión</a>
        </nav>
    </div>
</c:if>
<!-- Tarjetas de temas -->
<main class="usuarios-container">
    <c:if test="${empty temas}">
        <p style="color: #118acb; text-align: center;">⚠️ No hay temas disponibles por el momento.</p>
    </c:if>

    <c:forEach var="tema" items="${temas}">
        <div class="tarjeta-usuario">
            <c:choose>
                <c:when test="${not empty tema.tutor.rutaFoto}">
                    <img class="foto-perfil" src="${pageContext.request.contextPath}/img/usuarios/${tema.tutor.rutaFoto}?v=${now}" alt="Foto de perfil">
                </c:when>
                <c:otherwise>
                    <img class="foto-perfil" src="${pageContext.request.contextPath}/img/default-user.jpeg" alt="Foto de perfil">
                </c:otherwise>
            </c:choose>


            <h3>${tema.nombre}</h3>
            <p><strong>Materia:</strong> ${tema.materia.nombre}</p>
            <p><strong>Descripción:</strong> ${tema.descripcion}</p>
            <p><strong>Tutor:</strong> ${tema.tutor.nombre}</p>
            <a href="${pageContext.request.contextPath}/perfil-tutor?id=${tema.tutor.id}" class="btn-ver">Ver Perfil</a>
        </div>
    </c:forEach>
</main>

<!-- Panel de conversaciones -->
<div id="panel-chats" class="hidden">
    <h3>Mis Conversaciones</h3>
    <ul id="lista-conversaciones">
        <c:forEach var="conv" items="${conversaciones}">
            <li onclick="abrirChat(${conv.id}, '${conv.nombre}')">
                <span>${conv.nombre}</span>
            </li>
        </c:forEach>
    </ul>
</div>

<!-- Ventana flotante de chat -->
<!-- Ventana flotante de chat -->
<div id="chat-window" class="hidden">
    <div class="chat-header">
        <span>Mensajes</span>
        <button onclick="toggleChatWindow()">✖</button>
    </div>
    <div class="chat-body" id="chat-mensajes">
        <!-- Aquí se renderizan los mensajes -->
    </div>
    <div class="chat-input">
        <input type="text" id="mensajeInput" placeholder="Escribe un mensaje..." onkeypress="enviarSiEnter(event)">
        <button onclick="enviarMensaje()">Enviar</button>
    </div>
</div>

<!-- Scripts para abrir y cerrar ventanas -->
<script>

    function cargarConversaciones() {
        // Hacemos la petición al backend
        fetch("/mis-conversaciones")
            .then(response => response.json())
            .then(data => {
                const lista = document.getElementById("lista-conversaciones");
                lista.innerHTML = ""; // Limpiar la lista

                if (data.length > 0) {
                    data.forEach(conv => {
                        const li = document.createElement("li");
                        li.textContent = conv.nombre;
                        li.onclick = () => abrirChat(conv.id, conv.nombre);
                        lista.appendChild(li);
                    });
                    document.getElementById("panel-chats").classList.remove("hidden");
                } else {
                    console.log("No hay conversaciones");
                }
            })
            .catch(error => {
                console.error("Error al obtener conversaciones:", error);
            });
    }
    function toggleChatPanel() {
        const panel = document.getElementById("panel-chats");
        if(panel) {
            if (panel.classList.contains("hidden")) {
                panel.style.display = "block";
                panel.classList.remove("hidden");
            } else {
                panel.style.display = "none";
                panel.classList.add("hidden");
            }
        }else{
            console.error("No se encontró el panel de conversaciones");
        }
    }

    function toggleChatWindow() {
        const ventana = document.getElementById("chat-window");
        ventana.classList.toggle("hidden");
    }

    function enviarSiEnter(e) {
        if (e.key === "Enter") {
            enviarMensaje();
        }
    }

    function abrirChat(idConversacion, nombre) {
        // Carga el nombre del chat
        document.querySelector('.chat-header span').innerText = nombre;
        idConversacionActiva = idConversacion;

        // Llama al backend para obtener los mensajes
        fetch(`/mensajes/${idConversacion}`)
            .then(response => response.json())
            .then(data => {
                const chat = document.getElementById("chat-mensajes");
                chat.innerHTML = "";
                data.forEach(mensaje => {
                    const div = document.createElement("div");
                    div.innerText = mensaje.idEmisor + ": " + mensaje.contenido;
                    chat.appendChild(div);
                });
            });

        // Muestra la ventana flotante
        document.getElementById("chat-window").classList.remove("hidden");
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

<script>
    let stompClient = null;
    let idConversacionActiva = null;

    function conectar() {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log("Conectado: " + frame);

            if (idConversacionActiva) {
                stompClient.subscribe(`/topic/mensajes/${idConversacionActiva}`, function (mensaje) {
                    mostrarMensaje(JSON.parse(mensaje.body));
                });
            }
        });
    }

    function mostrarMensaje(mensaje) {
        const chat = document.getElementById("chat-mensajes");
        const div = document.createElement("div");
        div.innerText = mensaje.idEmisor + ": " + mensaje.contenido;
        chat.appendChild(div);
        chat.scrollTop = chat.scrollHeight;
    }

    function enviarMensaje() {
        const input = document.getElementById("mensajeInput");
        const mensaje = input.value.trim();
        if (mensaje !== "" && idConversacionActiva) {
            stompClient.send("/app/chat.privado", {}, JSON.stringify({
                contenido: mensaje,
                idConversacion: idConversacionActiva,
                idEmisor: ${usuario.id}
            }));
            input.value = "";
        }
    }

    window.addEventListener("load", conectar);
</script>

</body>
</html>