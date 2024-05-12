const stompClient = new StompJs.Client({
  brokerURL: "ws://localhost:8080/qualgo-ws",
  connectHeaders: {
    Authorization: "Bearer " + sessionStorage.getItem("token"),
  },
});

stompClient.onConnect = (frame) => {
  setConnected(true);
  console.log("Connected: " + frame);
  stompClient.subscribe("/topic/new-message", (message) => {
    let newMsg = JSON.parse(message.body);
    if (newMsg.chatChannelId == channelId) {
      $("#greetings").append(
        `<tr id="msg${newMsg.id}"><td>${newMsg.contentBody}</td><td><button type="button" class="btn btn-warning btn-sm" onclick="deleteMsg(${newMsg.id})">delete</button></td></tr>`
      );
    }
  });
  stompClient.subscribe("/topic/message-deleted", (message) => {
    $(`#msg${message.body}`).remove();
  });
};

stompClient.onWebSocketError = (error) => {
  console.error("Error with websocket", error);
};

stompClient.onStompError = (frame) => {
  console.error("Broker reported error: " + frame.headers["message"]);
  console.error("Additional details: " + frame.body);
};

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  } else {
    $("#conversation").hide();
  }
  $("#connected").html(connected);
}

function connect() {
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

function showGreeting(message) {
  $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function checkLogin() {
  var token = sessionStorage.getItem("token");
  if (!token) {
    console.log("need login");
    const loginModal = new bootstrap.Modal(
      document.getElementById("loginModal")
    );
    loginModal.show();
    return false;
  }
  return true;
}

function login() {
  fetch("/auth/login", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      username: $("#loginUserName").val(),
      password: $("#loginPassword").val(),
    }),
  })
    .then((res) => res.json())
    .then((res) => {
      console.log(res);
      sessionStorage.setItem("token", res.token);
      const loginModal = new bootstrap.Modal(
        document.getElementById("loginModal")
      );
      window.location.reload();
    });
}

$(function () {
  $("form").on("submit", (e) => e.preventDefault());
  $("#connect").click(() => connect());
  $("#disconnect").click(() => disconnect());
  $("#loginButton").click(() => login());
});

function showChannels() {
  fetch("/channels", {
    method: "GET",
    headers: {
      Accept: "application/json",
      Authorization: "Bearer " + sessionStorage.getItem("token"),
      "Content-Type": "application/json",
    },
  })
    .then((res) => res.json())
    .then((res) => {
      $("#selectChannel").html("");
      $.each(res, function (i, item) {
        $("#selectChannel").append(
          `<button type="button" class="btn btn-primary" onclick="selectChannel(${item.id})">${item.name}</button>`
        );
      });
    })
    .catch((err) => {
      sessionStorage.removeItem("token");
    });
}

function deleteMsg(messageId) {
  fetch(`/channels/${channelId}/messages/${messageId}`, {
    method: "DELETE",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: "Bearer " + sessionStorage.getItem("token"),
    },
  })
    .then((res) => res.json())
    .then((res) => {
      $(`#msg${messageId}`).remove();
    });
}

var channelId = null;

function selectChannel(cid) {
  channelId = cid;
  fetch(`/channels/${channelId}/messages`, {
    method: "GET",
    headers: {
      Accept: "application/json",
      Authorization: "Bearer " + sessionStorage.getItem("token"),
      "Content-Type": "application/json",
    },
  })
    .then((res) => res.json())
    .then((res) => {
      $("#greetings").html("");
      res.data.reverse();
      $.each(res.data, function (i, item) {
        $("#greetings").append(
          `<tr id="msg${item.id}"><td>${item.contentBody}</td><td><button type="button" class="btn btn-warning btn-sm" onclick="deleteMsg(${item.id})">delete</button></td></tr>`
        );
      });
    })
    .catch((err) => {
      sessionStorage.removeItem("token");
    });
}

// main
var loggedIn = checkLogin();
if (loggedIn) {
  showChannels();
}

function sendMsg() {
  let text = $("#sendMsgInput").val();
  fetch(`/channels/${channelId}/send`, {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: "Bearer " + sessionStorage.getItem("token"),
    },
    body: JSON.stringify({
      content: text,
      contentType: "text/plain",
    }),
  })
    .then((res) => res.json())
    .then((res) => {
      $("#sendMsgInput").val("");
    })
    .catch((err) => {
      sessionStorage.removeItem("token");
    });
}

connect();
