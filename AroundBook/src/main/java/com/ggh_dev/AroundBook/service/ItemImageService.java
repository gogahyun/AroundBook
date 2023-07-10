package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.ItemImage;
import com.ggh_dev.AroundBook.repository.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemImageService {
    private final ItemImageRepository itemImageRepository;

    /**
     * 상품 이미지 저장
     */
    @Transactional
    public void saveItemImages(Item item, List<ItemImage> images){
        for (ItemImage image : images) {
            image.setItem(item);     //상품 - 이미지 연관관계 설정
            itemImageRepository.save(image);
        }
    }

    /**
     * 상품 이미지 삭제
     */
    @Transactional
    public List<ItemImage> deleteByItem(Item item){
        return itemImageRepository.deleteByItem(item);
    }

    /**
     * 단건 상품의 이미지 리스트
     */
    public List<ItemImage> findItemImages(Item item) {
        return itemImageRepository.findItemImagesByItem(item);
    }

    /**
     * 상품 리스트의 이미지 - 첫번째 등록 이미지 기준
     */
    public List<ItemImage> findItemListImages(List<Item> items) {
        List<ItemImage> list= new ArrayList<>();
        for (Item item : items) {
            list.add(itemImageRepository.findItemFirstImageByItem(item));
        }
        return list;
    }
}
