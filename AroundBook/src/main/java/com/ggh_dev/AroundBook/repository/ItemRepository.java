package com.ggh_dev.AroundBook.repository;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    /**
     * 상품 등록
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);   //신규 등록 객체
        }else{
            em.merge(item); //이미 등록된 객체
        }
        em.flush();
    }

    //--전체 상품 조회--//
    /**
     * 상품 단건 조회
     */
    public Item findOne(Long id) {
        return em.find(Item.class,id);
    }

    /**
     * 상품 전체 조회
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }

    //--회원별 상품 조회--//
    /**
     * 상품 전체 조회
     */
    public List<Item> findMemberAll(Member member) {
        return em.createQuery("select i from Item i where i.member = :member",Item.class)
                .setParameter("member",member)
                .getResultList();
    }
}
