package com.ggh_dev.AroundBook.domain.item;

import com.ggh_dev.AroundBook.domain.member.Member;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //현재는 BOOK만 존재
@DiscriminatorColumn(name="dtype")
@Getter
@DynamicInsert
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member; //판매 회원

    private String title;

    private String content;

    private int price;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> images = new ArrayList<>();

    private int views;

    private int likes;

    @Enumerated(EnumType.STRING)
    private SaleStatus status; //판매 상태 [SALE, RESERVATION, SOLDOUT]

    private LocalDateTime createDate;


    //--연관관계 메서드--//
    public void setMember(Member member) {
        this.member=member;
        member.getItems().add(this);
    }


    //--생성 메서드--//
    public void createItem(List<ItemImage> images, int price, String title, String content) {
        this.likes=0;
        this.status=SaleStatus.SALE;

        for (ItemImage image : images) {
            this.images.add(image);
        }
        this.price=price;
        this.title=title;
        this.content=content;
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

    /**
     * 상품 수정 - 1)가격 2)판매상태
     * @param price
     * @param status
     */
    public void update(int price, SaleStatus status,String content) {
        this.price=price;
        this.status=status;
        this.content=content;
    }
}
