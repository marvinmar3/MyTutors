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

<!-- Botón hamburguesa -->
<button class="menu-btn" onclick="toggleSidebar()">☰</button>
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
    <%--<select id="filtroTutor">
        <option value="">-- Filtrar por tutor --</option>
        <c:forEach var="t" items="${tutores}">
            <option value="${t.nombre}">${t.nombre}</option>
        </c:forEach>
    </select>--%>
    <select id="filtroRol">
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
    <label><input type="checkbox" id="filtroSinTutor"> Sin tutor</label>
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

            <a href="${pageContext.request.contextPath}/temas/detalle?id=${tema.id}" class="btn-ver">Ver tema</a>
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

<div class="contenedor-crear-tutoria">
    <button onclick="mostrarFormularioTutoria()" class="btn btn-primary" id="btn-crear-tutoria">
        ➕ Crear tutoría
    </button>
</div>

<div id="formularioTutoria" style="display: none; margin-top: 20px;">
    <form action="${pageContext.request.contextPath}/temas/crear" method="post">
        <input type="hidden" name="idUsuario" value="${usuario.id}" />

        <label>Título:</label>
        <input type="text" name="nombre" required class="form-control"/>

        <label>Descripción:</label>
        <textarea name="descripcion" class="form-control" required></textarea>

        <label>Facultad:</label>
        <select name="id_facultad" id="facultad" required>
            <option value="">Selecciona una facultad</option>
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

<script>
    let listenersAgregados = false;

    function mostrarFormularioTutoria() {
        const formulario = document.getElementById("formularioTutoria");
        formulario.style.display = formulario.style.display === "none" ? "block" : "none";

        // Solo agregamos los listeners una vez
        if (!listenersAgregados) {
            agregarListenersEncadenamiento();
            listenersAgregados = true;
        }
    }

    function agregarListenersEncadenamiento() {
        const facultadSelect = document.getElementById("facultad");
        const carreraSelect = document.getElementById("carrera");
        const materiaSelect = document.getElementById("materia");
        const divNueva = document.getElementById("materiaNuevaDiv");
        const nuevaMateriaInput = document.getElementById("nuevaMateria");

        facultadSelect.addEventListener("change", function () {
            const idFacultad = this.value;

            // Verificamos que no esté vacío
            if (!idFacultad || idFacultad === "") {
                console.warn("Facultad no seleccionada.");
                return;
            }

            carreraSelect.innerHTML = '<option value="">Cargando carreras...</option>';
            materiaSelect.innerHTML = '<option value="">Selecciona una materia</option>';

            fetch(`/temas/carreras?id_facultad=${idFacultad}`)
                .then(resp => resp.json())
                .then(data => {
                    carreraSelect.innerHTML = '<option value="">Selecciona una carrera</option>';

                    if (!Array.isArray(data)) {
                        console.error("Respuesta de carreras no es un arreglo:", data);
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
                    console.error("Error cargando carreras:", err);
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
                }).catch(err => console.error("Error cargando materias:", err));
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

</script>


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

</body>
</html>