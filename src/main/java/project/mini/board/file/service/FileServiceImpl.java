package project.mini.board.file.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.mini.board.cipher.enumeration.AesKey;
import project.mini.board.member.constant.MemberConstant;
import project.mini.board.file.mapper.FileMapper;
import project.mini.board.file.model.AttachFile;
import project.mini.board.member.model.Member;
import project.mini.board.cipher.util.Aes256Cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private static final DateTimeFormatter SAVE_PATH_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm/ss");

    private final FileMapper fileMapper;

    @Value("${file.save-base-dir}")
    private String saveBaseDirectory;

    @Value("${file.basic-profile-image-path}")
    private String basicProfileImagePath;

    @Override
    @Transactional(rollbackFor = IOException.class)
    public String saveAttachFile(MultipartFile multipartFile, Member member) throws IOException {
        String fileSavePath = createFileSavePath(multipartFile);

        File file = new File(fileSavePath);
        file.mkdirs();

        multipartFile.transferTo(file);

        AttachFile attachFile = AttachFile.builder()
                .fileExtension(multipartFile.getContentType())
                .fileName(multipartFile.getOriginalFilename())
                .fileSavePath(Aes256Cipher.encrypt(AesKey.FILE, fileSavePath))
                .build();

        fileMapper.insertAttachFile(attachFile, member);
        return Aes256Cipher.encrypt(AesKey.FILE, String.valueOf(attachFile.getFileId()));
    }

    private String createFileSavePath(MultipartFile multipartFile) {
        LocalDateTime now = LocalDateTime.now();
        return saveBaseDirectory + SAVE_PATH_FORMATTER.format(now) + "/" + multipartFile.getOriginalFilename();
    }

    @Override
    public byte[] getImageFileData(String encryptFileId) throws IOException {
        String fileId = Aes256Cipher.decrypt(AesKey.FILE, encryptFileId);
        String fileSavePath = basicProfileImagePath;

        if (StringUtils.equals(fileId, MemberConstant.EMPTY_IMAGE_FILE_ID) == false) {
            AttachFile attachFile = fileMapper.selectAttachFile(Integer.parseInt(fileId));
            fileSavePath = attachFile.getDecryptFileSavePath();
        }

        InputStream inputStream = new FileInputStream(fileSavePath);
        return IOUtils.toByteArray(inputStream);
    }
}
