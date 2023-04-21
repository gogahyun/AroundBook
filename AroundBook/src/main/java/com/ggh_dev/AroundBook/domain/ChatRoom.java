package com.ggh_dev.AroundBook.domain;

import com.ggh_dev.AroundBook.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatRoom {
    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member participants1;


}
