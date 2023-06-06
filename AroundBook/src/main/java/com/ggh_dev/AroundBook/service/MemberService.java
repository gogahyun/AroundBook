package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.MemberRepository;
import com.ggh_dev.AroundBook.web.member.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@Transactional(readOnly = true)//JPA의 모든 데이터 로직은 트랜젝션 안에서
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(MemberForm memberForm) {
        Member member = modelMapper.map(memberForm, Member.class);
        validateDuplicateMember(member); //중복 회원 검증
        member.setLocation(memberForm.getZipcode(),memberForm.getAddress());

        memberRepository.save(member);//DB 저장
        return member.getId();
    }

    /**
     * 중복회원 검증 - userid
     */
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMembers = memberRepository.findByUserID(member.getUserId());
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
