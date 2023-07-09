package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.ChatRoom;
import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.ChatRoomRepository;
import com.ggh_dev.AroundBook.repository.ItemRepository;
import com.ggh_dev.AroundBook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 채팅방 등록
     */
    @Transactional
    public Long saveChatRoom(Long sellerId, Long buyerId,Long itemId) {
        //엔티티 조회
        Member seller = memberRepository.findOne(sellerId);
        Member buyer = memberRepository.findOne(buyerId);
        Item item = itemRepository.findOne(itemId);

        //채팅방 엔티티 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.createChatRoom(seller, buyer,item);
        chatRoomRepository.save(chatRoom);
        return chatRoom.getId();
    }

    /**
     * 회원별 채팅방 목록 조회
     */
    public List<ChatRoom> findChatRooms(Long memberId){
        Member member = memberRepository.findOne(memberId);
        return chatRoomRepository.findChatRoomsByMemberId(member);
    }

    /**
     * 채팅방 조회
     */
    public ChatRoom findChatRoom(Long id) {
        return chatRoomRepository.findChatRoomById(id);
    }

}
