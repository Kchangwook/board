package project.mini.board.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.mini.board.member.annotation.LoginMember;
import project.mini.board.member.model.Member;

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
	public String getUpdateAccountView(Model model, @LoginMember Member member) {
		model.addAttribute("loginMember", member);
		return "/member/my-page/update-account";
	}

	@GetMapping("password")
    public String getUpdatePasswordView(Model model, @LoginMember Member member) {
		model.addAttribute("loginMember", member);
		return "/member/my-page/update-password";
	}
}
