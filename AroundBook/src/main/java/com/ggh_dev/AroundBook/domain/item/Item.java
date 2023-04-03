package com.ggh_dev.AroundBook.domain.item;

import com.ggh_dev.AroundBook.domain.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //현재는 BOOK만 존재
@DiscriminatorColumn(name="dtype")
@Getter
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member seller; //판매 회원

    private int price;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemImage> images= new ArrayList<>();

    private int viewCount;

    @Enumerated(EnumType.STRING)
    private SaleStatus status; //판매 상태 [SALE, RESERVATION, SOLDOUT]

    private String content;

    private LocalDateTime createDate;

    /**
     * 연관관계 메서드
     */
    public void setSeller(Member member) {
        this.seller=member;
        member.getItems().add(this);
    }
}
