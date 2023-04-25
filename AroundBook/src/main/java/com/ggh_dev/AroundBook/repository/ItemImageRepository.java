package com.ggh_dev.AroundBook.repository;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.ItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemImageRepository {
    private final EntityManager em;

    /**
     * 상품 이미지 저장
     */
    public void save(ItemImage itemImage) {
        if (itemImage.getId() == null) {
            em.persist(itemImage);
        }else{
            em.merge(itemImage);
        }
    }

    /**
     * 상품의 이미지 리스트 조회
     */
    public List<ItemImage> findItemImagesByItem(Item item) {
        return em.createQuery("select i from ItemImage i where i.item = : item", ItemImage.class)
                .setParameter("item", item)
                .getResultList();
    }

}
