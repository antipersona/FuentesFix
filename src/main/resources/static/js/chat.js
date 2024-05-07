document.addEventListener("DOMContentLoaded", function() {
    const messageArea = document.getElementById("messageArea");
    const messageForm = document.getElementById("sendmsg");
    
    
    if (ws.receive) {
        const oldFn = ws.receive; // guarda referencia a manejador anterior
         ws.receive = (m) => {
            //oldFn(m); // llama al manejador anterior
            messageArea.insertAdjacentHTML("beforeend", renderMsg(m));
        }
    }
    function renderMsg(msg) {
        console.log("rendering: ", msg);
        return `<div>${msg.from} : ${msg.text}</div>`;
    }
    messageForm.onclick = (e) => {
        e.preventDefault();
        const messageInput = document.getElementById("message").value;
        console.log("value of message input:", messageInput);
        go(messageForm.parentNode.action, 'POST', messageInput)
            .then(d => console.log("happy", d))
            .catch(e => console.log("sad", e))
    }
});
