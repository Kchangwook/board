package project.mini.board.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.mini.board.member.mapper.MemberHistoryMapper;
import project.mini.board.member.model.Member;

@SpringBootTest
public class MapperTest {
    @Autowired
    private MemberHistoryMapper memberHistoryMapper;

    @Test
    public void test() {
        Member member = Member.builder()
                .id("id")
                .password("password")
                .email("email")
                .nick("nick")
                .build();

        memberHistoryMapper.insertMemberHistory(member);
    }
}
