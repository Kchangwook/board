package project.mini.board.file.controller.api;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    public String saveAttachFile(MultipartFile attachFile, @LoginMember Member loginMember) throws IOException {
        String encryptFileId = fileService.saveAttachFile(attachFile, loginMember);
        return StringUtils.replace(encryptFileId, "/", "_");
    }

    @GetMapping(value = "/image/{encryptImageFileId:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String encryptImageFileId) throws IOException {
        String replacedEncryptFileId = StringUtils.replace(encryptImageFileId, "_", "/");
        return fileService.getImageFileData(replacedEncryptFileId);
    }
}
