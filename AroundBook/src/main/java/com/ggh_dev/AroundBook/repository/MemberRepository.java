package com.ggh_dev.AroundBook.repository;

import com.ggh_dev.AroundBook.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    //멤버 저장
    public void save(Member member) {
        em.persist(member);
    }

    //단건 멤버 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //전체 멤버 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m ", Member.class)
                .getResultList();
    }

    //멤버 리스트 조회 - 유저아이디
    public List<Member> findByUserID(String userId) {
        return em.createQuery("select m from Member m where m.userId = :user_id", Member.class)
                .setParameter("user_id", userId)
                .getResultList();
    }
}
