package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.Chat;
import com.ggh_dev.AroundBook.domain.ChatRoom;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.ChatRepository;
import com.ggh_dev.AroundBook.repository.ChatRoomRepository;
import com.ggh_dev.AroundBook.repository.MemberRepository;
import com.ggh_dev.AroundBook.web.Chat.ChatMessageForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    /**
     * 채팅 저장
     */
    public void saveChat(ChatMessageForm message) {
        log.info(message.getWriter().toString());
        Long writerId = message.getWriter();
        Member member = memberRepository.findOne(writerId);
        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(message.getRoomId());

        Chat chat = new Chat();
        chat.createChat(chatRoom, member, message.getMessage());

        chatRepository.save(chat);
    }
}
