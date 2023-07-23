package project.mini.board.file.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.mini.board.file.service.FileService;
import project.mini.board.member.annotation.LoginMember;
import project.mini.board.member.model.Member;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileApiController {
    private final FileService fileService;

    @PostMapping
    public String saveAttachFile(MultipartFile multipartFile, @LoginMember Member loginMember) throws IOException {
        return fileService.saveAttachFile(multipartFile, loginMember);
    }
}
