package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.Member;
import com.ggh_dev.AroundBook.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//JPA의 모든 데이터 로직은 트랜젝션 안에서
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복회원 검증 - userid
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByUserID(member.getUserId());
        //EXCEPTION
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     * @param memberId
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
