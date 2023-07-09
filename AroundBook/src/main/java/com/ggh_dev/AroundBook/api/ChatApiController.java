package com.ggh_dev.AroundBook.api;

import com.ggh_dev.AroundBook.domain.Chat;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.ChatService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatApiController {
    private final ChatService chatService;
    @GetMapping("/{roomId}")
    public Map getChatList(@PathVariable Long roomId, @Login Member member){

        List<Chat> chatList = chatService.getChatList(roomId);

        Map<String, Object> result = new HashMap();
        result.put("member",Long.toString(member.getId()));

        ArrayList<HashMap<String, String>> chats = new ArrayList<>();
        for(Chat chat : chatList){
            HashMap<String, String> map = new HashMap<>();
            map.put("mem",Long.toString(chat.getWriter().getId()));
            map.put("message",chat.getMessage());

            chats.add(map);
        }
        result.put("chats",chats);

        return result;
    }
}
