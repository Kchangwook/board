package project.mini.board.member.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.mini.board.member.mapper.MemberMapper;
import project.mini.board.member.model.Member;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	private final MemberMapper memberMapper;
	private final MemberHistoryService memberHistoryService;

	@Override
	public void addMember(Member member) {
		String encodedPassword = DigestUtils.sha3_256Hex(member.getPassword());
		member.setPassword(encodedPassword);

		memberMapper.insertMember(member);
		memberHistoryService.addMemberHistory(member);
	}

	@Override
	public Member getMemberById(String memberId) {
		return memberMapper.selectMemberById(memberId);
	}

	@Override
	public void modifyMember(Member member) {
		memberMapper.updateMember(member);
		memberHistoryService.addMemberHistory(member);
	}

	@Override
	public boolean modifyMemberPassword(Member member) {
		Member targetMember = getMemberById(member.getId());
		String encodedInputCurrentPassword = DigestUtils.sha3_256Hex(member.getPassword());

		if (StringUtils.equals(targetMember.getPassword(), encodedInputCurrentPassword)) {
			return false;
		}

		String encodedNewPassword = DigestUtils.sha3_256Hex(member.getPassword());
		member.setPassword(encodedNewPassword);

		modifyMember(member);
		return true;
	}
}
