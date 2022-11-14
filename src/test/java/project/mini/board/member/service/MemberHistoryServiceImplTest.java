//package project.mini.board.member.service;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import project.mini.board.member.mapper.MemberHistoryMapper;
//import project.mini.board.member.model.Member;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class MemberHistoryServiceImplTest {
//    @Mock
//    private MemberHistoryMapper memberHistoryMapper;
//
//    @InjectMocks
//    private MemberHistoryServiceImpl memberHistoryService;
//
//    @Test
//    @DisplayName("회원 히스토리 정보 저장 테스트")
//    public void addMemberHistoryTest() {
//        //given
//        Member member = new Member();
//
//        //when
//        memberHistoryService.addMemberHistory(member);
//
//        //then
//        verify(memberHistoryMapper, times(1)).insertMemberHistory(member);
//    }
//}
