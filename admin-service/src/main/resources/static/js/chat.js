var stompClient = null;

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/chat', function (chatMessage) {
            respondMessage(JSON.parse(chatMessage.body));
        });
    });
}

function sendMessage() {
    const message = $('#input-message').val();
    const senderName = $('#user-profile').text();
    const sendTime = getCurrentDateTime();

    const chatMessage = {
        'senderName' : senderName,
        'message' : message,
        'sendTime' : sendTime
    };

    $('.direct-chat-messages').append(`
            <div class="direct-chat-msg right">
                <div class="direct-chat-infos clearfix">
                    <span class="direct-chat-name float-right ml-1">${senderName}</span>
                    <span class="direct-chat-timestamp float-right">${sendTime}</span>
                </div>
                <div class="direct-chat-text">
                    ${message}
                </div>
            </div>
    `)


    stompClient.send("/app/bot", {}, JSON.stringify(chatMessage));
}

function respondMessage(chatMessage) {
    $('.direct-chat-messages').append(`
            <div class="direct-chat-msg">
                <div class="direct-chat-infos clearfix">
                    <span class="direct-chat-name float-left mr-1">${chatMessage.senderName}</span>
                    <span class="direct-chat-timestamp float-left">${chatMessage.sendTime}</span>
                </div>
                <div class="direct-chat-text">
                    ${chatMessage.message}
                </div>
            </div>
    `);
}

function getCurrentDateTime() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = String(currentDate.getMonth() + 1).padStart(2, '0');
    var day = String(currentDate.getDate()).padStart(2, '0');
    var hours = String(currentDate.getHours()).padStart(2, '0');
    var minutes = String(currentDate.getMinutes()).padStart(2, '0');

    return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes;
}

$(function () {
    $("#chat-form").on('submit', function (e) {
        e.preventDefault();
        sendMessage();
    });
    $("#chat-form button").click(function () {
        sendMessage()
    })
    $("#send").click(function () {
        sendMessage();
    });
});