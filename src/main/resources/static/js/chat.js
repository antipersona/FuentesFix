"use strict"

/**
 * WebSocket API, which only works once initialized
 */
const ws = {

    /**
     * Number of retries if connection fails
     */
    retries: 3,

    /**
     * Default action when message is received. 
     */
    receive: (text) => {
        console.log(text);
        let p = document.querySelector("#nav-unread");
        if (p) {
            p.textContent = +p.textContent + 1;
        }
    },

    headers: { 'X-CSRF-TOKEN': config.csrf.value },

    /**
     * Attempts to establish communication with the specified
     * web-socket endpoint. If successfull, will call 
     */
    initialize: (endpoint, subs = []) => {
        try {
            ws.stompClient = Stomp.client(endpoint);
            ws.stompClient.reconnect_delay = 2000;
            // only works on modified stomp.js, not on original from mantainer's site
            ws.stompClient.reconnect_callback = () => ws.retries-- > 0;
            ws.stompClient.connect(ws.headers, () => {
                ws.connected = true;
                console.log('Connected to ', endpoint, ' - subscribing:');
                while (subs.length != 0) {
                    let sub = subs.pop();
                    console.log(` ... to ${sub} ...`)
                    ws.subscribe(sub);
                }
            });
            console.log("Connected to WS '" + endpoint + "'")
        } catch (e) {
            console.log("Error, connection to WS '" + endpoint + "' FAILED: ", e);
        }
    },

    subscribe: (sub) => {
        try {
            ws.stompClient.subscribe(sub,
                (m) => ws.receive(JSON.parse(m.body))); // fails if non-json received!
            console.log("Hopefully subscribed to " + sub);
        } catch (e) {
            console.log("Error, could not subscribe to " + sub, e);
        }
    }
}

















document.addEventListener("DOMContentLoaded", function() {
    const messageArea = document.getElementById("messageArea");
    const messageForm = document.getElementById("sendmsg");
    const messageInput = document.getElementById("message");

    // Fetch messages when page loads
    fetchMessages();

    // Function to fetch messages from server
    function fetchMessages() {
        fetch("/chat")
            .then(response => response.json())
            .then(messages => {
                // Clear existing messages
                messageArea.innerHTML = "";

                // Populate messageArea with fetched messages
                messages.forEach(message => {
                    const li = document.createElement("li");
                    li.textContent = message.text;
                    messageArea.appendChild(li);
                });
            })
            .catch(error => console.error("Error fetching messages:", error));
    }

    // Function to submit new message to server
    function sendMessage(messageContent) {
        fetch("/chat", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ content: messageContent })
        })
        .then(fetchMessages)
        .catch(error => console.error("Error sending message:", error));
    }

    // Add submit event listener to messageForm
    messageForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const messageContent = messageInput.value.trim();
        if (messageContent !== "") {
            sendMessage(messageContent);
            messageInput.value = "";
        }
    });
});
