package project.mini.board.member.resolver;

import java.util.Arrays;

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
import project.mini.board.cipher.enumeration.AesKey;
import project.mini.board.member.constant.MemberConstant;
import project.mini.board.member.annotation.LoginMember;
import project.mini.board.member.service.MemberService;
import project.mini.board.cipher.util.Aes256Cipher;

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
		String encryptedMember = Arrays.stream(httpServletRequest.getCookies())
			.filter(cookie -> StringUtils.equals(MemberConstant.LOGIN_MEMBER_COOKIE_NAME, cookie.getName()))
			.findFirst()
			.map(Cookie::getValue)
			.orElse(StringUtils.EMPTY);

		if (StringUtils.equals(encryptedMember, StringUtils.EMPTY)) {
			log.error("{} path는 로그인이 필요합니다.", httpServletRequest.getPathInfo());
			throw new IllegalAccessException();
		}

		String loginMemberId = Aes256Cipher.decrypt(AesKey.MEMBER_LOGIN, encryptedMember);
		return memberService.getMemberById(loginMemberId);
	}
}
