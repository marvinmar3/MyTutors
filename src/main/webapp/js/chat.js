let stompClient = null;
let ventanasAbiertas = [];

console.log("📌 ID del usuario en chat.js:", usuarioIdGlobal);


function conectarWebSocket(idConversacion) {
    const socket = new SockJS('/chat-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log('✅ Conectado WebSocket');

        stompClient.subscribe('/topic/mensajes/' + idConversacion, function (mensaje) {
            const msg = JSON.parse(mensaje.body);
            mostrarMensajeChat(msg, idConversacion);
        });

        $.getJSON("/api/mensajes/" + idConversacion, function (mensajes) {
            const chat = document.getElementById(`chat-body-${idConversacion}`);
            chat.innerHTML = "";
            mensajes.forEach(m => mostrarMensajeChat(m, idConversacion));
        }).fail(function () {
            console.error("❌ No se pudieron cargar los mensajes");
        });
    });
}

function abrirChatMessenger(idConversacion, nombre) {
    if (document.getElementById(`chat-messenger-${idConversacion}`)) return;

    const contenedor = document.getElementById("ventanas-messenger-container");
    const div = document.createElement("div");
    div.className = "ventana-chat";
    div.id = `chat-messenger-${idConversacion}`;

    const offsetRight = 20 + (ventanasAbiertas.length * 320);
    div.style.right = `${offsetRight}px`;

    div.innerHTML = `
        <div class="chat-top-bar">
            <span><strong>${nombre}</strong></span>
            <button onclick="cerrarChatMessenger(${idConversacion})">✖</button>
        </div>
        <div class="chat-body" id="chat-body-${idConversacion}">Cargando...</div>
        <div class="chat-footer">
            <input type="text" id="input-${idConversacion}" placeholder="Escribe..." onkeypress="if(event.key==='Enter') enviarMensajeChat(${idConversacion})">
            <button onclick="enviarMensajeChat(${idConversacion})">Enviar</button>
        </div>
    `;

    contenedor.appendChild(div);
    conectarWebSocket(idConversacion);
    ventanasAbiertas.push({ id: idConversacion, dom: div });
}

function cerrarChatMessenger(idConversacion) {
    const ventana = document.getElementById(`chat-messenger-${idConversacion}`);
    if (ventana) ventana.remove();
    ventanasAbiertas = ventanasAbiertas.filter(v => v.id !== idConversacion);
}

function mostrarMensajeChat(msg, idConversacion) {
    const contenedor = document.getElementById(`chat-body-${idConversacion}`);
    const div = document.createElement("div");
    div.classList.add("mensaje");

    const hora = new Date(msg.fechaEnvio || new Date()).toLocaleTimeString("es-MX", {
        hour: '2-digit',
        minute: '2-digit'
    });

    if (parseInt(msg.idEmisor) === parseInt(usuarioIdGlobal)) {
        div.classList.add("mensaje-propio");
        div.innerHTML = `<div class="contenido-mensaje">${msg.contenido}</div><div class="detalle-mensaje">${hora}</div>`;
    } else {
        div.classList.add("mensaje-ajeno");
        div.innerHTML = `<div class="contenido-mensaje">${msg.contenido}</div><div class="detalle-mensaje"><strong>${msg.nombreEmisor}</strong> · ${hora}</div>`;
    }

    contenedor.appendChild(div);
    contenedor.scrollTop = contenedor.scrollHeight;
}

function enviarMensajeChat(idConversacion) {
    //validacion paraa eevitar errores por id inválido
    if(!idConversacion|| isNaN(idConversacion)){
        console.error("Id de conversacion invalido:", idConversacion);
        return;
    }
    const input = document.getElementById(`input-${idConversacion}`);
    if(!input){
        console.error("Inmput no encontrado para la conversación: ", idConversacion);
    }
    const contenido = input.value.trim();
    if (!contenido) return;

    const mensaje = {
        idConversacion: idConversacion,
        idEmisor: usuarioIdGlobal,
        contenido: contenido,
        fechaEnvio: new Date().toISOString()
    };

    console.log("📨 Enviando mensaje:", mensaje);
    console.log("🧪 JSON.stringify:", JSON.stringify(mensaje));

    $.ajax({
        url: "/api/mensajes",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(mensaje),
        success: function () {
            input.value = "";
        },
        error: function () {
            console.error("❌ Error al enviar mensaje");
        }
    });
}

function abrirChatDesdeTema(event, idTema, nombreConversacion) {
    const btn = event.target;
    const original = btn.innerText;
    btn.disabled = true;
    btn.innerText = "Abriendo...";

    $.get("/api/conversacion/por-tema/" + idTema, function (resp) {
        if (!resp || !resp.id) {
            alert("No se pudo obtener la conversación.");
            return;
        }
        abrirChatMessenger(resp.id, nombreConversacion);
    }).fail(function () {
        alert("No tienes acceso o la tutoría aún no está completa.");
    }).always(function () {
        btn.disabled = false;
        btn.innerText = original;
    });
}
