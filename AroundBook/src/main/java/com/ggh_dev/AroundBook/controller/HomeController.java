package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.MemberRepository;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

    @RequestMapping("/")
    public String home(@Login Member member, Model model) {
        //세션에 회원 데이터가 없는 경우
        if (member == null) {
            return "home";
        }

        //데이터 있을 경우 - 로그인
        model.addAttribute("member",member);
        return "login/loginHome";
    }
}
