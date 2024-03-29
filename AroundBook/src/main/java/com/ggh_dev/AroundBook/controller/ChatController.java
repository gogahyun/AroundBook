package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.service.ChatService;
import com.ggh_dev.AroundBook.web.dto.ChatMessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate template;
    private final ChatService chatService;

    //메세지 발행 요청 -> 해당 메세지 처리
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageForm message){
        message.setMessage("채팅방에 입장하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
    @MessageMapping("/chat/message/{roomId}")
    @SendTo("/sub/chat/{roomNo}")
    //구독하고 있는 주소 메세지 전달
    //@MessageMapping(value = "/chat/message")
    public void message(ChatMessageForm message) {
        chatService.saveChat(message); //채팅 DB 저장

        template.convertAndSend("/sub/chat/"+message.getRoomId(),message);
    }
}
