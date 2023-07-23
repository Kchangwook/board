package project.mini.board.file.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.mini.board.file.model.AttachFile;
import project.mini.board.member.model.Member;

@Mapper
public interface FileMapper {
    void insertAttachFile(AttachFile attachFile, Member loginMember);
}
