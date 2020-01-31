var stompClient = null;
var chatTextArea;

var socket = new SockJS('/websocket-chat');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/topic/message', function (message) {
        if (message != null) {
            showMessage(JSON.parse(message.body));
        } else {
            alert('Ваша сессия истекла! Пожалуйста авторизуйтесь заново');
            window.location = "/login";
        }
    });
});

function sendMessage() {
    let newMessage = {
        'id': null,
        'fromUser': currentUser,
        'toUser': $('#toUser').val(),
        'content': $("#message").val(),
        'created': null
    };
    stompClient.send("/app/message", {}, JSON.stringify (newMessage));
    $.ajax({
        type : "POST",
        contentType: "application/json; charset=utf-8",
        url : window.location.origin + "/chat",
        data : JSON.stringify(newMessage),
        success : function(response) {

        },
        error: function(response) {

        },
        complete: function() {
        }
    }).done(function() {
    });
}

function showMessage(message) {
    let id = message.id;
    let fromUser = message.fromUser;
    let toUser = message.toUser;
    let content = message.content;
    let created = message.created;
    let localDateTime = created.substring(8,10) + "." + created.substring(5,7) + "." + created.substring(0,4) + " "  + created.substring(11,19);
    toUser = toUser.length !== 0 ? " пишет пользователю " + toUser.toUpperCase() : " пишет";
    chatTextArea.value += fromUser.toUpperCase()  + toUser + " (" + localDateTime + ")" + ":\r\n"  + content + " " + "\r\n\r\n";
    chatTextArea.scrollTop = chatTextArea.scrollHeight;
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send-message-button" ).click(function() { sendMessage(); });
    chatTextArea = document.getElementById('chatTextArea');
});