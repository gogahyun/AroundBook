package com.ggh_dev.AroundBook.repository;

import com.ggh_dev.AroundBook.domain.item.*;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ggh_dev.AroundBook.domain.item.QBook.*;

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

    //--상품 검색 조회--//
    public List<Book> searchItemsByTitle(ItemSearch search) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.select(book)
                .from(book)
                .where(searchConditionEq(search.getCondition(), search.getText()) //제목 검색
                ,saleStatusEq()) //판매 중 상품만 검색
               .limit(1000)
               .fetch();
    }

    private BooleanExpression saleStatusEq() {
        return book.status.eq(SaleStatus.SALE);
    }
    private BooleanExpression searchConditionEq(SearchCondition condition, String text) {
        if(condition.equals(SearchCondition.TITLE)) {
            return book.title.like(text);
        }
        if(condition.equals(SearchCondition.AUTHOR)) {
            return book.author.like(text);
        }
        if(condition.equals(SearchCondition.PUBLISHER)) {
            return book.publisher.like(text);
        }
        return null;
    }

}
