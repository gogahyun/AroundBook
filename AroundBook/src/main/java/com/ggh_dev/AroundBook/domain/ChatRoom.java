package com.ggh_dev.AroundBook.domain;

import com.ggh_dev.AroundBook.domain.item.Item;
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
    @JoinColumn
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    public void createChatRoom(Member seller, Member buyer,Item item) {
        this.seller=seller;
        this.buyer=buyer;
        this.item=item;
    }
}
