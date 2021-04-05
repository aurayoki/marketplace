'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

const userID = document.getElementById('userID').value;
let channelID;
let toUserID;
let advertisementID;

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect() {
    username = document.getElementById('username').value.trim();

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

connect();


async function onConnected() {
    let valueByUrl = window.location.href.split('/messages/')[1];
    toUserID = valueByUrl.split('-')[0];
    advertisementID = valueByUrl.split("-")[1];

    let response = await fetch("/profile/messenger/channel/channelID/" + userID + '/' + toUserID + '/' + advertisementID);
    if (response.ok) {
        await response.text().then(function (text) {
            channelID = text;
        });
    } else {
        alert("Ошибка получения id чата");
    }

    let responseMsg = await fetch("/profile/messenger/channel/" + channelID);
    let messages;
    if (responseMsg.ok) {
        messages = await responseMsg.json();
    } else {
        alert("Ошибка получения сообщений");
    }

    for (let i = 0; i < messages.length; i++) {
        onMessageReceived(undefined, messages[i]);
    }
    stompClient.subscribe("/topic/personal." + channelID, onMessageReceived);

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            username: username,
            recipient: toUserID,
            content: messageInput.value,
            channelID: channelID,
            timeSent: new Date()
        };

        stompClient.send("/app/personal." + channelID, {}, JSON.stringify(chatMessage));

        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload, msg) {
    var message;
    if (payload !== undefined) {
        message = JSON.parse(payload.body);
    } else {
        message = msg;
    }

    var messageElement = document.createElement('li');

    messageElement.classList.add('chat-message');

    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(message.username[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.username);

    messageElement.appendChild(avatarElement);

    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.username);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);


    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

function buttonBackMessenger() {
    window.location.href = '/profile/messenger';
}


messageForm.addEventListener('submit', sendMessage, true)
