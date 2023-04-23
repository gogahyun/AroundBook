package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.ChatRoom;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.ChatRoomService;
import com.ggh_dev.AroundBook.service.ChatService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    /**
     * 회원별 채팅방 리스트 조회
     */
    @GetMapping("/rooms")
    public String rooms(Model model, @Login Member member) {
        List<ChatRoom> chatRooms = chatRoomService.findChatRooms(member.getId());

        model.addAttribute("rooms", chatRoomService.findChatRooms(member.getId()));

        return "chat/chatList";
    }

    /**
     * 채팅방 생성
     */
    @PostMapping("/new")
    public String createChatRoom(@RequestParam("itemId") Long itemId,
                                @RequestParam("sellerId") Long sellerId,
                                @Login Member member){
        String roomId = Long.toString(chatRoomService.saveChatRoom(sellerId, member.getId(),itemId));
        return "redirect:/chat/room?roomId="+roomId;
    }

    /**
     * 채팅방 조회
     */
    @GetMapping("/room")
    public String getRoom(Long roomId, @Login Member member, Model model) {
        model.addAttribute("chatList", chatService.getChatList(roomId));
        model.addAttribute("room", chatRoomService.findChatRoom(roomId));
        model.addAttribute("member", member);

        return "chat/room";

    }
}

