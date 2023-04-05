package com.ggh_dev.AroundBook.repository;

import antlr.StringUtils;
import com.ggh_dev.AroundBook.domain.LikeItem;
import lombok.RequiredArgsConstructor;
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

    //관심 상품 단건 조회
    public LikeItem findOne(Long likeItemId) {
        return em.find(LikeItem.class, likeItemId);
    }

    //관심 상품 리스트 조회 - 엔티티 아이디
    public List<LikeItem> findById(Long memberId) {
        return em.createQuery("select l from LikeItem l where l.member.id = :member_id", LikeItem.class)
                .setParameter("member_id", memberId)
                .getResultList();
    }

    //관심 상품 삭제
    public void delete(LikeItem likeItem) {
        em.remove(likeItem);
    }
}
