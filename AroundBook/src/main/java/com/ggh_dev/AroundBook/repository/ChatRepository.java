package com.ggh_dev.AroundBook.repository;

import com.ggh_dev.AroundBook.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final EntityManager em;

    /**
     * 채팅 저장
     */
    public void save(Chat chat) {
        if (chat.getId() == null) {
            em.persist(chat);   //신규 등록 객체
        }else{
            em.merge(chat); //이미 등록된 객체
        }
    }
}
