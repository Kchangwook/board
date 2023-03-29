package project.mini.board.member.service;

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
		Member loginMember = new Member();

        //when
        memberService.modifyMember(member);

        //then
        verify(memberMapper, times(1)).updateMember(any(Member.class));
		verify(memberHistoryService, times(1)).addMemberHistory(any(Member.class));
    }
}
