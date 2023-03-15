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
	public String getMyPageView(Model model, @LoginMember Member loginMember) {
		model.addAttribute("member", loginMember);
		return "/member/my-page";
	}
}
