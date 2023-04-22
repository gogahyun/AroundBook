package com.ggh_dev.AroundBook.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class ChatRoom {
    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @Column(name = "m1_id")
    private Long member1Id;

    @Column(name = "m2_id")
    private Long member2Id;

    public void createChatRoom(Long member1Id, Long member2Id) {
        this.member1Id=member1Id;
        this.member2Id=member2Id;
    }
}
