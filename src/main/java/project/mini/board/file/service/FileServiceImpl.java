package project.mini.board.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.mini.board.file.mapper.FileMapper;
import project.mini.board.file.model.AttachFile;
import project.mini.board.member.model.Member;
import project.mini.board.util.Aes256Util;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private static final DateTimeFormatter SAVE_PATH_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm/ss");

    private final FileMapper fileMapper;

    @Value("${aes256.encrypt-key.attach-file}")
    private String attachFileEncryptKey;

    @Value("${file.save-base-dir}")
    private String saveBaseDirectory;

    @Override
    @Transactional(rollbackFor = IOException.class)
    public String saveAttachFile(MultipartFile multipartFile, Member member) throws IOException {
        String fileSavePath = createFileSavePath(multipartFile);
        multipartFile.transferTo(Path.of(fileSavePath));

        AttachFile attachFile = AttachFile.builder()
                .fileExtension(multipartFile.getContentType())
                .fileName(multipartFile.getOriginalFilename())
                .fileSavePath(fileSavePath)
                .build();

        fileMapper.insertAttachFile(attachFile, member);
        return Aes256Util.encrypt(attachFileEncryptKey, String.valueOf(attachFile.getFileId()));
    }

    private String createFileSavePath(MultipartFile multipartFile) {
        LocalDateTime now = LocalDateTime.now();
        return saveBaseDirectory + "/" + SAVE_PATH_FORMATTER.format(now) + multipartFile.getOriginalFilename();
    }
}
