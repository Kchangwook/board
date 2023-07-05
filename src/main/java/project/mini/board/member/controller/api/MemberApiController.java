package project.mini.board.member.controller.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import project.mini.board.constant.MemberConstant;
import project.mini.board.member.annotation.LoginMember;
import project.mini.board.member.model.Member;
import project.mini.board.member.service.MemberService;
import project.mini.board.util.Aes256Util;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController {
	private static final int MINUTES_30 = 30 * 60 * 1000;
	private static final String BASE_PATH = "/";

	private final MemberService memberService;

	@Value("${aes256.encrypt-key.member-login}")
	private String memberLoginEncryptKey;

	@PostMapping
	public void addMember(@ModelAttribute Member member) {
		memberService.addMember(member);
	}

	@GetMapping("/duplicate-check")
	public boolean isExistsMember(@RequestParam String memberId) {
		return memberService.getMemberById(memberId) != null;
	}

	@PostMapping("/login")
	public void login(HttpServletResponse response,  @ModelAttribute Member member) throws Exception {
		Member savedMember = memberService.getMemberById(member.getId());
		if (isNotSavedMember(savedMember, member)) {
			throw new NotFoundException("회원 정보가 일치하지 않습니다.");
		}

		response.addCookie(createLoginMemberCookie(member));
	}

	private boolean isNotSavedMember(Member savedMember, Member member) {
		String encodedPassword = DigestUtils.sha3_256Hex(member.getPassword());
		return savedMember == null
			|| member == null
			|| StringUtils.equals(savedMember.getId(), member.getId()) == false
			|| StringUtils.equals(savedMember.getPassword(), encodedPassword) == false;
	}

	private Cookie createLoginMemberCookie(Member member) throws Exception {
		Cookie cookie = new Cookie(MemberConstant.LOGIN_MEMBER_COOKIE_NAME, Aes256Util.encrypt(memberLoginEncryptKey, member.getId()));
		cookie.setHttpOnly(true);
		cookie.setPath(BASE_PATH);
		cookie.setMaxAge(MINUTES_30);
		return cookie;
	}

	@PutMapping
	public void modifyMember(@ModelAttribute Member member, @LoginMember Member loginMember) {
		member.setId(loginMember.getId());
		memberService.modifyMember(member);
	}

	@PutMapping("/password")
	public boolean modifyMemberPassword(@ModelAttribute Member member, @LoginMember Member loginMember) {
		member.setId(loginMember.getId());
		return memberService.modifyMemberPassword(member);
	}
}
