package com.ggh_dev.AroundBook.repository;

import com.ggh_dev.AroundBook.domain.ChatRoom;
import com.ggh_dev.AroundBook.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final EntityManager em;
    /**
     * 채팅방 등록
     */
    public void save(ChatRoom chatRoom) {
        if (chatRoom.getId() == null) {
            em.persist(chatRoom);
        }else{
            em.merge(chatRoom);
        }
    }

    /**
     * 채팅방 전체 목록 조회 - 회원별
     */
    public List<ChatRoom> findChatRoomsByMemberId(Member member) {

        List<ChatRoom> result = em.createQuery("select c from ChatRoom c where c.seller = :member or c.buyer = :member",ChatRoom.class)
                .setParameter("member",member)
                .getResultList();
        Collections.reverse(result); //채팅방 생성 최근 순으로 변경

        return result;
    }

    /**
     * 채팅방 조회
     */
    public ChatRoom findChatRoomById(Long id) {
        return em.find(ChatRoom.class, id);
    }
}
