package project.mini.board.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import project.mini.board.member.model.Member;

@Mapper
public interface MemberMapper {
	void insertMember(Member member);

	Member selectMemberById(String memberId);

	void updateMember(Member member);
}
