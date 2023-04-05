package project.mini.board.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class MemberController {
	@GetMapping("join")
	public String getJoinPageView() {
		return "/member/join";
	}

	@GetMapping("login")
	public String getLoginPageView() {
		return "/member/login";
	}

	@GetMapping("my-page")
	public String getMyPageView() {
		return "/member/my-page/main";
	}

	@GetMapping("account")
	public String getUpdateAccountView() {
		return "/member/my-page/update-account";
	}
}
