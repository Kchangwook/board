package project.mini.board.member.interceptor;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import project.mini.board.member.constant.MemberConstant;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Optional<Cookie> loginMemberCookie = Arrays.stream(request.getCookies())
			.filter(cookie -> StringUtils.equals(MemberConstant.LOGIN_MEMBER_COOKIE_NAME, cookie.getName()))
			.findFirst();

		if (loginMemberCookie.isPresent() == false) {
			response.sendRedirect("/member/login");
			return false;
		}

		return true;
	}
}
