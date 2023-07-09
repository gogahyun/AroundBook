package com.ggh_dev.AroundBook.web.argumentresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Login 어노테이션
 * 자동으로 세션에 있는 로그인 회원을 찾고, 없으면 null 반환
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
