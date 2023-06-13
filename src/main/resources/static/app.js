var stompClient = null;


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#secretID").prop("disabled", connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $('#spacecount').html("");
    $("#timeline").html("");
}

function connect() {
    var socket = new SockJS('/plug-your-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);

        var secretID = $("#secretID").val();
        stompClient.send("/app/connection/"+secretID, {}, $("#userConnectedID").val());

        stompClient.subscribe('/topic/conversation/'+secretID, function (message) {
            console.log(message.body);
            showMessages(message.body);
        });

        stompClient.subscribe('/topic/connectedUsersUpdate/'+secretID, function (message) {
            var message = JSON.parse(message.body);
            $('#spacecount').html(message.connectionList.length);
        });
    });
}

window.addEventListener('beforeunload', function() {
  disconnect();
  event.preventDefault();
});

function disconnect() {
    if (stompClient !== null) {
        var secretID = $("#secretID").val();
        stompClient.send("/app/disconnection/"+secretID, {}, $("#userConnectedID").val());
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    var secretID = $("#secretID").val()
    stompClient.send("/app/conversation/"+secretID, {}, JSON.stringify({
        'userConnectedID': $("#userConnectedID").val(),
        'message': $("#message").val()
    }));
    $("#message").val("");
}

function showMessages(message) {
    $("#timeline").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    const uniqueId = generateUniqueId();
    $("#userConnectedID").val(uniqueId);
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
    $( "#codeGeneration" ).click(function() { generateSpaceID(); });
});

$(document).ready(function() {
  document.title = "User - " + $("#userConnectedID").val();
});

function generateSpaceID(){
    const spaceUniqueId = generateSpaceUniqueId();
    $("#secretID").val(spaceUniqueId);
}

function generateSpaceUniqueId() {
  const timestamp = Date.now().toString(36); // Get the current timestamp in base 36
  const randomString = Math.random().toString(36).substring(2, 16); // Generate a random string
  return timestamp + randomString;
}


function generateUniqueId() {
  const timestamp = Date.now().toString(36); // Get the current timestamp in base 36
  const randomString = Math.random().toString(36).substring(2, 8); // Generate a random string
  return timestamp + randomString;
}

