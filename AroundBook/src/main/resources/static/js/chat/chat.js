var chat_people=document.getElementsByClassName('chat_list');
$(document).ready(function() {
      $("#sendArea").hide();
      init();
});

function handleClick(event) {
     var parentTrTag = event.target;
     while(parentTrTag.className!="chat_list"&&parentTrTag.className!="chat_list active_chat"){parentTrTag=parentTrTag.parentElement;    }

      for (var i = 0; i < chat_people.length; i++) {
        chat_people[i].classList.remove("active_chat");
      }
      parentTrTag.classList.add("active_chat");
};

function init() {
    for (var i = 0; i < chat_people.length; i++) {
      chat_people[i].addEventListener("click", handleClick);
    }
};


//기존 채팅 불러오기
function getChats(memberId, roomId){
    $('#chat').remove();
    $('#chatArea').append('<div id="chat"></div>')
    $.ajax({
           url: "/api/chat/"+roomId,
           type: "get",
           contentType: "application/json; charset=utf-8;",
           dataType:"json",
           success: function(data) {
            var response = '';
            for (var i = 0; i < data.chats.length; i++) {
                if (data.chats[i].mem==memberId){
                response += '<div class="outgoing_msg"><div class="sent_msg"><p>'+
                     data.chats[i].message + '</div></div>'

                }else{
                response += '<div class="incoming_msg"><div class="received_msg"><div class="received_withd_msg"><p>'+
                     data.chats[i].message + '</div></div></div>'
                }
            }

            $('#chat').append(response);
            $("#sendArea").show();
            connect(roomId,memberId);
            return response
           }
       });

 };
//실시간 채팅 연결
function connect(roomId,memberId){
     console.log( roomId + ", " + memberId);

     //소켓 생성
     var sock = new SockJS("/stomp/chat");
     //1. Sock를 내부에 들고있는 stomp를 내어줌
     var stomp = Stomp.over(sock);

     //2. connection이 맺어지면 실행
     stomp.connect({}, function (){
        console.log("STOMP Connection")

        //4. subscribe(path, callback)으로 메세지를 받을 수 있음
        stomp.subscribe("/sub/chat/" + roomId, function (chat) {

            var content = JSON.parse(chat.body);

            var writer = content.writer;
            var message = content.message;

            var str = '';
            var chat = document.getElementById("chat");
            var newDiv = document.createElement('div');

             //송신측
            if(writer.toString() === memberId){
                str = '<div class="outgoing_msg">';
                 str += '<div class="sent_msg">';
                str += '<p><b>' + message + '</b></p>';
                str += '</div></div>';
                newDiv.innerHTML=str;
                chat.appendChild(newDiv);
            }
            //수신측
            else{
                str = '<div class="incoming_msg">';
                str += '<div class="received_msg">';
                str += '<p><b>' + message + '</b></p>';
                str += '</div></div>';
                newDiv.innerHTML=str;
                chat.appendChild(newDiv);
            }

            newDiv.innerHTML=str;
            chat.appendChild(newDiv);
        });

        //3. send(path, header, message)로 메세지를 보낼 수 있음
        stomp.send('/pub/chat/enter', {}, JSON.stringify({roomId: roomId, writer: memberId}))
     });

     $("#send").on("click", function(e){
         var msg = document.getElementById("message");

         console.log(memberId + ":" + msg.value);
         stomp.send('/pub/chat/message/'+roomId, {}, JSON.stringify({roomId: roomId, message: msg.value, writer: memberId}));
         msg.value = '';
     });
 };
 function popup(){
     var url = "popup.html";
     var name = "popup test";
     var option = "width = 500, height = 500, top = 100, left = 200, location = no"
     window.open(url, name, option);
 }
//채팅 연결 종료
function disconnect(){
     window.location.reload();
}