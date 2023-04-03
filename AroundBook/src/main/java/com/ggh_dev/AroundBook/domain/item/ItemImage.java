package com.ggh_dev.AroundBook.domain.item;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ItemImage {
    @Id @GeneratedValue
    @Column(name = "item_image_id")
    private Long id;

    private String filePath;

    private String fileName;
}
