package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.LoginService;
import com.ggh_dev.AroundBook.web.SessionConst;
import com.ggh_dev.AroundBook.web.login.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member member = loginService.login(loginForm.getUserId(), loginForm.getPassword());

        //로그인 실패
        if (member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지않습니다.");
            return "login/loginForm";
        }

        //로그인 성공
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return "redirect:"+ redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
