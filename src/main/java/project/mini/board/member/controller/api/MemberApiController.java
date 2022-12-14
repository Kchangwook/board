package project.mini.board.member.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.mini.board.member.model.Member;
import project.mini.board.member.service.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController {
	private final MemberService memberService;

	@PostMapping
	public void addMember(@ModelAttribute Member member) {
		memberService.addMember(member);
	}

	@GetMapping("/duplicate-check")
	public boolean isExistsMember(@RequestParam String memberId) {
		return memberService.getMemberById(memberId) != null;
	}
}
