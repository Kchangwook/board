package project.mini.board.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.mini.board.member.mapper.MemberMapper;
import project.mini.board.member.model.Member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}
