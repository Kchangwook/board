package project.mini.board.member.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.mini.board.member.mapper.MemberMapper;
import project.mini.board.member.model.Member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {
	@Mock
	private MemberMapper memberMapper;

	@Mock
	private MemberHistoryService memberHistoryService;

	@InjectMocks
	private MemberServiceImpl memberService;

	@Test
	@DisplayName("회원 정보 추가 테스트")
	public void addMemberTest() {
		//given
		Member member = Member.builder()
			.id("id")
			.password("password")
			.email("email")
			.nick("nick")
			.build();

		//when
		memberService.addMember(member);

		//then
		verify(memberMapper, times(1)).insertMember(any(Member.class));
		verify(memberHistoryService, times(1)).addMemberHistory(any(Member.class));
	}

	@Test
	@DisplayName("회원 정보 조회 테스트")
	public void getMemberTest() {
		//given
		String memberId = "memberId";
		Member member = new Member();

		when(memberMapper.selectMemberById(anyString())).thenReturn(member);

		//when
		Member result = memberService.getMemberById(memberId);

		//then
		assertSame(result, member);

		verify(memberMapper, times(1)).selectMemberById(anyString());
	}

	@Test
	@DisplayName("회원 정보 수정 테스트")
    public void modifyMemberTest() {
        //given
        Member member = new Member();

        //when
        memberService.modifyMember(member);

        //then
        verify(memberMapper, times(1)).updateMember(any(Member.class));
		verify(memberHistoryService, times(1)).addMemberHistory(any(Member.class));
    }

	@Test
	@DisplayName("회원 비밀번호 수정 테스트 - 입력 비밀번호와 실제 비밀번호가 같은 경우")
	public void modifyMemberPasswordTest() {
		// given
		Member member = Member.builder()
			.id("id")
			.password("password")
			.newPassword("oldPassword")
			.build();

		Member targetMember = Member.builder()
			.id("id")
			.password(DigestUtils.sha3_256Hex("password"))
			.email("email")
			.nick("nick")
			.build();

		when(memberMapper.selectMemberById(anyString())).thenReturn(targetMember);

		// when
		boolean result = memberService.modifyMemberPassword(member);

		// then
		assertTrue(result);

		verify(memberMapper, times(1)).selectMemberById(anyString());
		verify(memberMapper, times(1)).updateMember(any(Member.class));
		verify(memberHistoryService, times(1)).addMemberHistory(any(Member.class));
	}

	@Test
	@DisplayName("회원 비밀번호 수정 테스트 - 입력 비밀번호와 실제 비밀번호가 다른 경우")
	public void modifyMemberPasswordDifferentPasswordTest() {
		// given
		Member member = Member.builder()
			.id("id")
			.password("password")
			.newPassword("oldPassword")
			.build();

		Member targetMember = Member.builder()
			.id("id")
			.password(DigestUtils.sha3_256Hex("text"))
			.email("email")
			.nick("nick")
			.build();

		when(memberMapper.selectMemberById(anyString())).thenReturn(targetMember);

		// when
		boolean result = memberService.modifyMemberPassword(member);

		// then
		assertFalse(result);

		verify(memberMapper, times(1)).selectMemberById(anyString());
		verify(memberMapper, times(0)).updateMember(any(Member.class));
		verify(memberHistoryService, times(0)).addMemberHistory(any(Member.class));
	}
}
