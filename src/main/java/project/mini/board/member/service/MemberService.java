package project.mini.board.member.service;

import project.mini.board.member.model.Member;

public interface MemberService {
	void addMember(Member member);

	Member getMemberById(String memberId);
}
