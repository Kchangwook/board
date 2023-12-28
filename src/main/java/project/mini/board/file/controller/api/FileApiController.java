package project.mini.board.file.controller.api;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.mini.board.constant.AesKey;
import project.mini.board.file.service.FileService;
import project.mini.board.member.annotation.LoginMember;
import project.mini.board.member.model.Member;
import project.mini.board.util.Aes256Util;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileApiController {
    private final FileService fileService;

    @PostMapping
    public String saveAttachFile(MultipartFile attachFile, @LoginMember Member loginMember) throws IOException {
        return fileService.saveAttachFile(attachFile, loginMember);
    }

    @GetMapping(value = "/image/{encryptImageFileId:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String encryptImageFileId) throws IOException {
        String replacedEncryptFileId = StringUtils.replace(encryptImageFileId, "_", "/");
        return fileService.getImageFileData(replacedEncryptFileId);
    }
}
