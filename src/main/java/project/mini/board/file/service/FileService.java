package project.mini.board.file.service;

import org.springframework.web.multipart.MultipartFile;
import project.mini.board.member.model.Member;

import java.io.IOException;

public interface FileService {
    String saveAttachFile(MultipartFile attachFile, Member member) throws IOException;

    byte[] getImageFileData(String encryptFileId) throws IOException;
}
