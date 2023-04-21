package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.ChatRoomRepository;
import com.ggh_dev.AroundBook.service.ChatRoomService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 리스트 조회
     */
    @GetMapping("/rooms")
    public String rooms(Model model) {
        //model.addAttribute("rooms", chatRoomRepository.findAllRooms());
        model.addAttribute("rooms", chatRoomService.findChatRooms());

        return "chat/chatList";
    }

    /**
     * 채팅방 생성
     */
    @PostMapping("/new")
    public String createChatRoom(@Login Member member){
        //chatRoomRepository.createChatRoom();
        chatRoomService.saveChatRoom();
        return "redirect:/chat/rooms";
    }

    /**
     * 채팅방 조회
     */
    @GetMapping("/room")
    public String getRoom(Long roomId, @Login Member member, Model model) {
        //model.addAttribute("room", chatRoomRepository.findRoomById(roomId));
        model.addAttribute("room", chatRoomService.findChatRoom(roomId));

        model.addAttribute("member", member);

        return "chat/room";

    }
}

