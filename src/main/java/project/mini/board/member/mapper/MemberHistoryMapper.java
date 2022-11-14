package project.mini.board.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.mini.board.member.model.Member;

@Mapper
public interface MemberHistoryMapper {
    void insertMemberHistory(Member member);
}
