package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.ItemRepository;
import com.ggh_dev.AroundBook.web.item.BookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 상품 전체 조회
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 상품 단건 조회
     * @param itemId
     */
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    /**
     * 상품 수정
     * @param itemId
     * @param form
     */
    @Transactional
    public void updateItem(Long itemId, BookForm form) {
        Item item = itemRepository.findOne(itemId);
        item.update(form.getPrice(), form.getStatus(),form.getContent());
    }

    //--회원 별 상품 조회--//
    /**
     * 상품 전체 조회
     */
    public List<Item> findMemberItems(Member member) {
        return itemRepository.findMemberAll(member);
    }
}
