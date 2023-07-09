package com.ggh_dev.AroundBook.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


public class MemberDTO {
    @Data
    @NoArgsConstructor
    public static class CreateMemberDTO{
        private Long id;

        @NotEmpty(message = "아이디는 필수 입력입니다!")
        private String userId;
        @NotEmpty(message = "비밀번호는 필수 입력입니다!")
        private String password;
        @NotEmpty(message = "이름은 필수 입력입니다!")
        private String name;

        @NotEmpty(message = "주소는 필수 입력입니다!")
        private String zipcode;
        private String address;
    }

    @Data
    @AllArgsConstructor
    public static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    public static class MemberListDto{
        private String name;
    }
}
