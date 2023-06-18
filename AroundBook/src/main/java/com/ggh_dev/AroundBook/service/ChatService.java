package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.Chat;
import com.ggh_dev.AroundBook.domain.ChatRoom;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.ChatRepository;
import com.ggh_dev.AroundBook.repository.ChatRoomRepository;
import com.ggh_dev.AroundBook.repository.MemberRepository;
import com.ggh_dev.AroundBook.web.dto.ChatMessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    /**
     * 채팅 저장
     */
    public void saveChat(ChatMessageForm message) {
        Long writerId = message.getWriter();
        Member member = memberRepository.findOne(writerId);
        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(message.getRoomId());

        Chat chat = new Chat();
        chat.createChat(chatRoom, member, message.getMessage());

        chatRepository.save(chat);
    }

    /**
     * 채팅방 채팅 불러오기
     */
    @Transactional(readOnly = true)
    public List<Chat> getChatList(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(roomId);

        List<Chat> chatList = chatRepository.findAllByChatRoom(chatRoom);

        return chatList;
    }
}
