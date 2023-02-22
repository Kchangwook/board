package project.mini.board.member.resolver;

import java.util.Arrays;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.mini.board.constant.MemberConstant;
import project.mini.board.member.annotation.LoginMember;
import project.mini.board.member.service.MemberService;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
	private final MemberService memberService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginMember.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		String loginMemberId = Arrays.stream(httpServletRequest.getCookies())
			.filter(cookie -> StringUtils.equals(MemberConstant.LOGIN_MEMBER_COOKIE_NAME, cookie.getName()))
			.findFirst()
			.map(Cookie::getValue)
			.orElse(StringUtils.EMPTY);

		if (StringUtils.equals(loginMemberId, StringUtils.EMPTY)) {
			log.error("{} path는 로그인이 필요합니다.", httpServletRequest.getPathInfo());
			throw new IllegalAccessException();
		}

		return memberService.getMemberById(loginMemberId);
	}
}
