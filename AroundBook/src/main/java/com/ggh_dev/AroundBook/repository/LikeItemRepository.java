package com.ggh_dev.AroundBook.repository;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.LikeItem;
import com.ggh_dev.AroundBook.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeItemRepository {
    private final EntityManager em;
    // 관심 상품품 등록
    public void save(LikeItem likeItem) {
        if (likeItem.getId() == null) {
            em.persist(likeItem);   //신규 등록 객체
        }else{
            em.merge(likeItem); //이미 등록된 객체
        }
    }

    //관심 상품 여부 조회
    public boolean exists(Member member, Item item){
        List resultList = em.createQuery("select l from LikeItem l where l.member = :member and l.item = :item")
                .setParameter("member", member)
                .setParameter("item", item)
                .getResultList();

        if (resultList.isEmpty()) return false;

        return true;
    }

    //관심 상품 단건 조회
    public LikeItem findOne(Long likeItemId) {
        return em.find(LikeItem.class, likeItemId);
    }

    //관심 상품 리스트 조회 - 엔티티 아이디
    public List<LikeItem> findByMember(Member member) {
        return em.createQuery("select l from LikeItem l where l.member = :member", LikeItem.class)
                .setParameter("member", member)
                .getResultList();
    }

    //관심 상품 삭제
    @Modifying
    public int delete(Member member, Item item) {
        return em.createQuery("delete from LikeItem l where l.member = :member and l.item = :item")
                .setParameter("member", member)
                .setParameter("item", item).executeUpdate();

    }
}
