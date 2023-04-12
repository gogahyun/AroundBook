package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    /**
     * 로그인
     * @return null 로그인 실패
     */
    public Member login(String userId, String password) {
        return memberRepository.findByUserID(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
