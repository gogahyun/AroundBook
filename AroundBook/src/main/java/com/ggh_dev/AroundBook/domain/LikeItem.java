package com.ggh_dev.AroundBook.domain;

import com.ggh_dev.AroundBook.domain.item.Item;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class LikeItem {
    @Id @GeneratedValue
    @Column(name = "like_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private String content;

    private int depth; //댓글 깊이

    private Long parentId; //현재 댓글의 전댓글

}
