package com.ggh_dev.AroundBook.domain;

import com.ggh_dev.AroundBook.domain.item.Item;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class LikeItem {
    @Id
    @GeneratedValue
    @Column(name = "like_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    Item item;

    //--연관관계 메서드--//
    public void setMember(Member member) {
        this.member=member;
        member.getLikeItems().add(this);
    }

    public void setItem(Item item) {
        this.item=item;
        item.addLikes();
    }

    //--생성 메서드--//
    /**
     * 관심 상품 추가
     */
    public static LikeItem addLikeItem(Member member, Item item) {
        LikeItem likeItem = new LikeItem();
        likeItem.setMember(member);
        likeItem.setItem(item);
        return likeItem;
    }

    //--비지니스 로직--//
    /**
     * 상품의 관심 상품 감소
     */
    public void removeLikes(){
        this.item.removeLikes();
    }
}
