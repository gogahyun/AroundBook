package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.ItemImage;
import com.ggh_dev.AroundBook.repository.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImageService {
    private final ItemImageRepository itemImageRepository;

    /**
     * 상품 이미지 저장
     */
    public void saveItemImages(Item book, List<ItemImage> images){
        for (ItemImage image : images) {
            image.setItem(book);     //상품 - 이미지 연관관계 설정
            itemImageRepository.save(image);
        }
    }

    public List<ItemImage> findItemImages(Item item) {
        return itemImageRepository.findItemImagesByItem(item);
    }
}
