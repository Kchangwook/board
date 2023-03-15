package project.mini.board.config;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import project.mini.board.member.interceptor.LoginCheckInterceptor;
import project.mini.board.member.resolver.LoginMemberArgumentResolver;

@RequiredArgsConstructor
@Component
public class WebApplicationConfig implements WebMvcConfigurer {
	private final LoginCheckInterceptor loginCheckInterceptor;
	private final LoginMemberArgumentResolver loginMemberArgumentResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor)
			.excludePathPatterns("/member/login", "/member/join", "/", "/assets/**", "/css/**", "/fonts/**", "/js/**", "/libs/**", "/api/member/login");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginMemberArgumentResolver);
	}
}
