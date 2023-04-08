package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.Member;
import com.ggh_dev.AroundBook.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        if (result.hasErrors()) {   //에러가 있을 경우 폼으로 이동
            return "members/createMemberForm";
        }
        log.info(memberForm.getUserId());
        log.info(memberForm.getName());
        log.info(memberForm.getPassword());

        Member member = new Member();
        member.createMember(memberForm.getUserId(), memberForm.getName(), memberForm.getPassword());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
