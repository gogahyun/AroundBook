package com.ggh_dev.AroundBook.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "아이디는 필수 입력입니다!")
    private String userId;
    @NotEmpty(message = "비밀번호는 필수 입력입니다!")
    private String password;
    @NotEmpty(message = "이름은 필수 입력입니다!")
    private String name;
}
