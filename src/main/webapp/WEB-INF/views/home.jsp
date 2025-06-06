<%--
  Created by IntelliJ IDEA.
  User: marvin
  Date: 15/04/25
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="true" %>
<%@ page import="com.mytutors.mytutors.model.Usuario" %>


<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login");
        return;
    }
%>
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

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


<!-- Botón hamburguesa -->
<button class="menu-btn" onclick="toggleSidebar()">☰</button>
<button class="btn-chat" onclick="toggleChatPanel()">💬</button>

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
                    <img class="perfil-imagen" src="${pageContext.request.contextPath}${usuario.rutaFoto}" alt="Perfil">
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
            <a href="${pageContext.request.contextPath}/temas/nuevo">➕ Crear tutoría</a>
            <a href="${pageContext.request.contextPath}/configuracion">Configuración</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Cerrar sesión</a>
        </nav>
    </div>
</c:if>

<div class="filtros-container">
    <input type="text" id="buscador" placeholder="Buscar por tema o descripción">
    <select id="filtroMateria">
        <option value="">-- Filtrar por materia --</option>
        <c:forEach var="m" items="${materias}">
            <option value="${m.nombre}">${m.nombre}</option>
        </c:forEach>
    </select>
    <select id="filtroRol", autocomplete="off">
        <option value="">-- Filtrar por rol --</option>
        <option value="asignado">Ya tienen tutor</option>
        <option value="disponible">Sin tutor</option>
    </select>
    <select id="filtroFacultad">
        <option value="">-- Filtrar por facultad --</option>
        <c:forEach var="f" items="${facultades}">
            <option value="${f.nombre}">${f.nombre}</option>
        </c:forEach>
    </select>
    <select id="filtroCarrera">
        <option value="">-- Filtrar por carrera --</option>
        <c:forEach var="c" items="${carreras}">
            <option value="${c.nombre}">${c.nombre}</option>
        </c:forEach>
    </select>
    <label><input type="checkbox" id="filtroSinTutor", autocomplete="off"> Sin tutor</label>
</div>



<!-- Tarjetas de temas -->
<main class="usuarios-container">
    <c:if test="${empty temas}">
        <p style="color: #118acb; text-align: center;">⚠️ No hay temas disponibles por el momento.</p>
    </c:if>

    <c:forEach var="tema" items="${temas}">
        <div class="tarjeta-usuario"
             data-rol="${tema.rol}"
             data-tiene-tutor="<c:out value='${tema.tutor != null}'/>"
             data-carrera="${tema.materia.carrera.nombre}"
             data-facultad="${tema.materia.facultad.nombre}">

        <c:choose>
                <c:when test="${not empty tema.tutor.rutaFoto}">
                    <img class="foto-perfil" src="${pageContext.request.contextPath}${tema.tutor.rutaFoto}?v=${now}" alt="Foto de perfil">
                </c:when>
                <c:otherwise>
                    <img class="foto-perfil" src="${pageContext.request.contextPath}/img/default-user.jpeg" alt="Foto de perfil">
                </c:otherwise>
            </c:choose>


            <h3>${tema.nombre}</h3>
            <p>
                <strong>Materia:</strong>
                <c:choose>
                    <c:when test="${not empty tema.materia}">
                        ${tema.materia.nombre}
                    </c:when>
                    <c:otherwise><em>Sin asignar</em></c:otherwise>
                </c:choose>
            </p>
            <p><strong>Descripción:</strong> ${tema.descripcion}</p>

            <p><strong>Tutor:</strong>
                <c:choose>
                    <c:when test="${not empty tema.tutor}">
                        ${tema.tutor.nombre}
                    </c:when>
                    <c:when test="${tema.rol == 'tutor'}">
                        ${tema.creador.nombre}
                    </c:when>
                    <c:otherwise>Sin asignar</c:otherwise>
                </c:choose>
            </p>

            <p><strong>Tutorado:</strong>
                <c:choose>
                    <c:when test="${tema.rol == 'tutorado'}">
                        ${tema.creador.nombre}
                    </c:when>
                    <c:otherwise>Sin asignar</c:otherwise>
                </c:choose>
            </p>

            <a href="${pageContext.request.contextPath}/temas/ver?idTema=${tema.id}" class="btn-ver">Ver tema</a>
        </div>
    </c:forEach>
</main>

<!-- websoket y funcionalidad del  chat -->

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

<script>
    let stompClient = null;
    let idConversacionActual = null;

    function conectarWebSocket(idConversacion) {
        idConversacionActual = idConversacion;

        const socket = new SockJS('/chat-websocket');

        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('✅ Conectado al WebSocket');

            // Suscribirse al canal específico de la conversación
            stompClient.subscribe('/topic/mensajes/' + idConversacion, function (mensaje) {
                const msg = JSON.parse(mensaje.body);
                mostrarMensaje(msg);
            });

            // Cargar mensajes anteriores vía jQuery
            $.getJSON("/api/mensajes/"+ idConversacion, function(mensajes) {
                if (!Array.isArray(mensajes)) {
                    console.error("❌ Respuesta inválida del servidor:", mensajes);
                    return;
                }

                const chat = document.getElementById(`chat-body-${idConversacion}`);
                chat.innerHTML = ''; // Limpia contenido anterior

                mensajes.forEach(m => {
                    console.log("✅ mensaje cargado vía API:", m); // este es CLAVE
                    mostrarMensajeChat(m, idConversacion);
                });


            }).fail(function(jqXHR, textStatus, errorThrown) {
                console.error("❌ Error al obtener mensajes:", textStatus, errorThrown);
            });
        });
    }



    function mostrarMensajeChat(mensaje, idConversacion) {
        const contenedor = document.getElementById(`chat-body-${idConversacion}`);
        console.log("Contendor chat encontrado: ", contenedor);
        if (!contenedor) {
            console.warn(`❌ No se encontró chat-body-${idConversacion}`);
            return;
        }
        const div = document.createElement("div");
        div.classList.add("mensaje");

        const horaFormateada = formatearHora(mensaje.fechaEnvio || new Date().toISOString());

        if (parseInt(mensaje.idEmisor) === parseInt(usuarioIdGlobal)) {
            div.classList.add("mensaje-propio");
            div.innerHTML = `
            <div class="contenido-mensaje">${mensaje.contenido}</div>
            <div class="detalle-mensaje"><small>${horaFormateada}</small></div>
        `;
        } else {
            div.classList.add("mensaje-ajeno");
            div.innerHTML = `
            <div class="contenido-mensaje">${mensaje.contenido}</div>
            <div class="detalle-mensaje"><small><strong>${mensaje.nombreEmisor}</strong> · ${horaFormateada}</small></div>
        `;
        }
        console.log("📦 Mostrando mensaje:", mensaje);

        contenedor.appendChild(div);
        contenedor.scrollTop=contenedor.scrollHeight;

    }

    function mostrarMensaje(msg){
        console.log("📩 mensaje recibido por websocket: ",msg);
        console.log("contenido: ", msg.contenido);
        if(!msg || !msg.idConversacion || !msg.contenido) {
            console.log.warm("‼️Mensaje invalido recibido", msg);
            return;
        }
        if(!msg.contenido || msg.contenido.trim()===""){
            console.warn("El mensaje no tiene contenido", msg);
        }
        console.log("📦Mensaje recibido: ", msg);

        mostrarMensajeChat(msg, msg.idConversacion);
    }


    function enviarMensajeChat(idConversacion) {
        const input = document.getElementById(`input-${idConversacion}`);
        const contenido = input.value.trim();
        if (!contenido) return;

        const mensaje = {
            idConversacion: idConversacion,
            idEmisor: usuarioIdGlobal,
            contenido: contenido,
            fechaEnvio: new Date().toISOString()
        };

        $.ajax({
            url: "/api/mensajes",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(mensaje),
            success: function () {
                input.value = "";
                actualizarMensajes(idConversacion);
            },
            error: function (xhr, status, error) {
                console.error("❌ Error al enviar mensaje:", error);
            }
        });
    }


    function cerrarChatMessenger(idConversacion) {
        const ventana = document.getElementById(`chat-messenger-${idConversacion}`);
        if (ventana) {
            ventana.remove();
        }

        //detener el setinterval asociado
        const index = ventanasAbiertas.findIndex(v => v.id === idConversacion);
        if (index !== -1) {
            clearInterval(ventanasAbiertas[index].intervalId);
            ventanasAbiertas.splice(index, 1);
        }

    }


    function enviarSiEnter(e) {
        if (e.key === "Enter") enviarMensajeChat();
    }
</script>

<!-- filtros -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const filtros = {
            texto: document.getElementById("buscador"),
            materia: document.getElementById("filtroMateria"),
            //tutor: document.getElementById("filtroTutor"),
            rol: document.getElementById("filtroRol"),
            facultad: document.getElementById("filtroFacultad"),
            carrera: document.getElementById("filtroCarrera"),
            sinTutor: document.getElementById("filtroSinTutor")
        };

        const tarjetas = document.querySelectorAll(".tarjeta-usuario");

        function filtrar() {
            const texto = filtros.texto.value.toLowerCase();
            const materia = filtros.materia.value;
            //const tutor = filtros.tutor.value;
            const rol = filtros.rol.value;
            const facultad = filtros.facultad.value;
            const carrera = filtros.carrera.value;
            const sinTutor = filtros.sinTutor.checked;

            tarjetas.forEach(tarjeta => {
                const nombre = tarjeta.querySelector("h3").textContent.toLowerCase();
                const desc = tarjeta.querySelector("p:nth-of-type(2)").textContent.toLowerCase();
                const mat = tarjeta.querySelector("p:nth-of-type(1)").textContent.toLowerCase();
                const tutorTexto = tarjeta.querySelector("p:nth-of-type(3)").textContent.toLowerCase();
                const fac = tarjeta.dataset.facultad;
                const car = tarjeta.dataset.carrera;

                const cumple = (
                    (texto === "" || nombre.includes(texto) || desc.includes(texto)) &&
                    (materia === "" || mat.includes(materia.toLowerCase())) &&
                    //(tutor === "" || tutorTexto.includes(tutor.toLowerCase())) &&
                    (rol === "" || (rol === "asignado" && tarjeta.dataset.tieneTutor === "true") ||
                        (rol === "disponible" && tarjeta.dataset.tieneTutor === "false")) &&
                    (facultad === "" || fac === facultad) &&
                    (carrera === "" || car === carrera) &&
                    (!sinTutor || tarjeta.dataset.tieneTutor === "false")

                );

                tarjeta.style.display = cumple ? "block" : "none";
            });
        }

        Object.values(filtros).forEach(input => {
            input.addEventListener("input", filtrar);
            input.addEventListener("change", filtrar);
        });

        filtrar(); // Mostrar todo al inicio
    });
</script>

<!--este se queda en Home si es que se llega a migrar -->
<script>
    var usuarioIdGlobal = ${usuario.id};
</script>
<!-- Panel flotante de conversaciones -->
<div id="panel-conversaciones" class="chat-panel hidden">
    <div class="chat-panel-header">
        <input type="text" id="buscarConversacion" placeholder="Buscar conversaciones..." />
    </div>
    <div class="chat-lista" id="listaConversaciones">
        <!-- Se carga dinámicamente -->
    </div>
</div>

<script>
    function toggleChatPanel() {
        const panel = document.getElementById("panel-conversaciones");
        panel.classList.toggle("hidden");
        if (!panel.classList.contains("loaded")) {
            cargarConversaciones();
            panel.classList.add("loaded");
        }
    }

    function cargarConversaciones() {
        $.getJSON("/mis-conversaciones", function (data) {
            const contenedor = document.getElementById("listaConversaciones");
            contenedor.innerHTML = "";

            if (data.length === 0) {
                contenedor.innerHTML = "<p style='padding:10px;'>No tienes conversaciones.</p>";
                return;
            }

            data.forEach(conv => {
                console.log("👉 Conversación cargada:", conv);

                const nombreValido = conv.nombre && typeof conv.nombre === 'string' && conv.nombre !== "false";
                const nombreMostrado = nombreValido ? conv.nombre : "Desconocido";

                const item = document.createElement("div");
                item.className = "chat-item";
                item.onclick = () => abrirChatMessenger(conv.id, nombreMostrado);

                const img = document.createElement("img");
                img.src = conv.avatar && typeof conv.avatar === 'string' && conv.avatar !== "false"
                    ? conv.avatar
                    : "/img/default-user.jpeg";

                const info = document.createElement("div");
                info.className = "chat-info";

                const mensajeMostrado = conv.ultimoMensaje ? conv.ultimoMensaje : "Sin mensajes";

                const fecha = conv.fechaUltimoMensaje ? new Date(conv.fechaUltimoMensaje) : null;
                const horaMostrada = fecha ? fecha.toLocaleTimeString("es-MX", { hour: '2-digit', minute: '2-digit' }) : "";




                info.innerHTML = `
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <strong>${nombreMostrado}</strong>
                        <small class="hora" style="font-size: 11px; color: #999;">${horaMostrada}</small>
                    </div>
                    <p style="font-size: 12px; color: gray;">${mensajeMostrado}</p>
                `;



                item.appendChild(img);
                item.appendChild(info);
                contenedor.appendChild(item);
            });
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error("❌ Error al cargar conversaciones:", textStatus, errorThrown);
        });
    }

    function formatearHora(fechaIso){
        if (!fechaIso) return "";
        const fecha = new Date(fechaIso);
        return fecha.toLocaleTimeString("es-MX", {
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    function formatearFecha(fechaIso) {
        if (!fechaIso) return "";
        const fecha = new Date(fechaIso);
        return fecha.toLocaleString("es-MX", {
            hour: '2-digit',
            minute: '2-digit',
            day: '2-digit',
            month: 'short'
        });
    }

    const ventanasAbiertas = [];

    function abrirChatMessenger(idConversacion, nombre) {
        if(!idConversacion || isNaN(idConversacion)){
            console.error("ID de conversacion inválido: ", idConversacion);
            return;
        }
        // Verifica si ya está abierto
        if (document.getElementById(`chat-messenger-${idConversacion}`)) return;

        const contenedor = document.getElementById("ventanas-messenger-container");

        const div = document.createElement("div");
        div.className = "ventana-chat";
        div.id = `chat-messenger-${idConversacion}`;

        const index = ventanasAbiertas.length;
        const spacing = 320;
        const offsetRight = 20 + (index * spacing);
        div.style.right = `${offsetRight}px`;

        div.innerHTML = `
            <div class="chat-top-bar">
                <span><strong>${nombre}</strong></span>
                <button onclick="cerrarChatMessenger(${idConversacion})">✖</button>
            </div>
            <div class="chat-body" id="chat-body-${idConversacion}">Cargando mensajes...</div>
            <div class="chat-footer">
                <input type="text" id="input-${idConversacion}" placeholder="Escribe...">
                <button onclick="enviarMensajeChat(${idConversacion})">Enviar</button>
            </div>
        `;

        contenedor.appendChild(div);

        conectarWebSocket(idConversacion);

        //cargar los mensajes inicialmenente
        actualizarMensajes(idConversacion);

        //establecer polling con setInterval
        const intervalId = setInterval(() => {
            console.log("⏱️ Ejecutando actualizarMensajes para ID: ", idConversacion);
            if (!idConversacion || isNaN(idConversacion)) {
                console.warn("⚠️ ID de conversacion inválido en polling:", idConversacion);
                return;
            }
            actualizarMensajes(idConversacion);
        }, 2000);
        ventanasAbiertas.push({ id: idConversacion, dom: div, intervalId });


    }
    const ultimoMensajePorConversacion={};

    function actualizarMensajes(idConversacion){
        if(!idConversacion || isNaN(idConversacion)){
            console.warn("❌ID de conversacion inválido en polling: ",idConversacion);
            return;
        }
        console.log("🔁 Ejecutando actualizarMensajes para ID:", idConversacion);

        $.getJSON("/api/mensajes/" + idConversacion, function (mensajes) {
            console.log("📨 Respuesta del servidor:", mensajes);
            console.log("ID de la conversacion: ", idConversacion);
            const contenedor = document.getElementById(`chat-body-${idConversacion}`);
            if(!contenedor) return;

            const ultimoIdMostrado= ultimoMensajePorConversacion[idConversacion] || -1;

            contenedor.innerHTML = '';

            mensajes.forEach(m => {
                mostrarMensajeChat(m, idConversacion);
                ultimoMensajePorConversacion[idConversacion]= m.id;
            });
        }).fail(function () {
            console.error("❌ Error al cargar mensajes por polling.");
        });

    }

</script>

<!-- Contenedor de ventanas flotantes tipo Messenger -->
<div id="ventanas-messenger-container" style="position: fixed; bottom: 0; right: 0; display: flex; flex-direction: row-reverse; gap: 20px; z-index: 2000;"></div>

</body>
</html>