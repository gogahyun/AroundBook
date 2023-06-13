package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.MemberService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import com.ggh_dev.AroundBook.web.dto.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        if (result.hasErrors()) {   //에러가 있을 경우 폼으로 이동
            return "members/createMemberForm";
        }
        memberService.join(memberForm);
        return "redirect:/";
    }

    @GetMapping
    public String memberInfo(@Login Member member, Model model) {
        model.addAttribute("member", member);
        return "members/memberInfo";
    }

    //  @ExceptionHandler를 사용하여 Exception 처리 메소드들을 작성
    // 회원 가입 시, 중복된 아이디 입력 경우
    @ExceptionHandler({IllegalStateException.class})
    public String validateDuplicateMemberError(BindingResult bindingResult) {
        bindingResult.reject("CreateMemberFail", "해당 아이디는 사용할 수 없습니다.");
        return "members/createMemberForm";  //폼으로 돌아감
    }

    //회원등록한 전체 회원 조회
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
