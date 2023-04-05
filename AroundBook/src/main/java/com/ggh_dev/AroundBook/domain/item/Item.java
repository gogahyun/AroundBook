package com.ggh_dev.AroundBook.domain.item;

import com.ggh_dev.AroundBook.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //현재는 BOOK만 존재
@DiscriminatorColumn(name="dtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member; //판매 회원

    private int price;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemImage> images= new ArrayList<>();

    private int views;

    private int likes;

    @Enumerated(EnumType.STRING)
    private SaleStatus status; //판매 상태 [SALE, RESERVATION, SOLDOUT]

    private String content;

    private LocalDateTime createDate;

    //--연관관계 메서드--//
    public void setMember(Member member) {
        this.member=member;
        member.getItems().add(this);
    }

    //--비지니스 로직--//
    /**
     * 관심 상품 증가
     */
    public void addLikes() {
        this.likes++;
    }

    /**
     * 관심 상품 감소
     */
    public void removeLikes() {
        this.likes--;
    }

}
