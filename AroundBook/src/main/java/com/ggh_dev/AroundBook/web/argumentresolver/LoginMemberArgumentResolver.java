package com.ggh_dev.AroundBook.web.argumentresolver;

import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.web.SessionConst;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * ArgumentResolver 사용 시점
     * @Login 어노테이션 존재 && Member 타입일 경우
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnno = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnno && hasMemberType;
    }

    /**
     * 필요한 파라미터 정보 생성
     * 세션에 있는 로그인 회원 정보 member 객체로 반환
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();

        if (session == null) {
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
