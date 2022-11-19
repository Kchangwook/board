package project.mini.board.member.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public void addMember(@RequestBody Member member) {
		memberService.addMember(member);
	}
}
