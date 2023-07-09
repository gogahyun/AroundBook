package com.ggh_dev.AroundBook.domain;

import com.ggh_dev.AroundBook.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Chat {
    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member writer;

    private String message;

    public void createChat(ChatRoom chatRoom, Member member, String message) {
        this.chatRoom=chatRoom;
        this.writer=member;
        this.message=message;
    }
}
