package project.mini.board.member.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.mini.board.member.mapper.MemberHistoryMapper;
import project.mini.board.member.model.Member;

@RequiredArgsConstructor
@Service
public class MemberHistoryServiceImpl implements MemberHistoryService {
	private final MemberHistoryMapper memberHistoryMapper;
	@Override
	public void addMemberHistory(Member member) {
		memberHistoryMapper.insertMemberHistory(member);
	}
}
