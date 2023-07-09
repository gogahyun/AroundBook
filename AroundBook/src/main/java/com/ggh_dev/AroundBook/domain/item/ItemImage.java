package com.ggh_dev.AroundBook.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ItemImage {
    @Id
    @GeneratedValue
    @Column(name = "item_image_id")
    private Long id;

    private String uploadFileName; //사용자 업로드 파일명
    private String storeFileName; //서버 관리 파일명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;


    public ItemImage(String originalFilename, String storeFileName) {
        this.uploadFileName=originalFilename;
        this.storeFileName=storeFileName;
    }

    public void setItem(Item item) {
        this.item=item;
        item.getImages().add(this);
    }
}
