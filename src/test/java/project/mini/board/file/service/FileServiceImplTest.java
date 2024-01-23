package project.mini.board.file.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import project.mini.board.cipher.enumeration.AesKey;
import project.mini.board.member.constant.MemberConstant;
import project.mini.board.file.mapper.FileMapper;
import project.mini.board.file.model.AttachFile;
import project.mini.board.member.model.Member;
import project.mini.board.cipher.util.Aes256Cipher;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {
    @Mock
    private FileMapper fileMapper;

    @InjectMocks
    private FileServiceImpl fileService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(fileService, "saveBaseDirectory", "path");
    }
    @Test
    @DisplayName("파일 저장 테스트")
    public void saveFileTest() throws IOException {
        // given
        Member loginMember = Member.builder().build();
        MultipartFile multipartFile = mock(MultipartFile.class);

        when(multipartFile.getOriginalFilename()).thenReturn("file");

        // when
        fileService.saveAttachFile(multipartFile, loginMember);

        // then
        verify(multipartFile, times(2)).getOriginalFilename();

        verify(fileMapper, times(1)).insertAttachFile(any(AttachFile.class), any(Member.class));
    }

    @Test
    @DisplayName("이미지 파일 데이터 가져오기 테스트")
    public void getImageFileDataTest() throws IOException {
        //given
        String fileId = "1";
        String encryptFileId = Aes256Cipher.encrypt(AesKey.FILE, fileId);

        String encryptFilePath = Aes256Cipher.encrypt(AesKey.FILE, "/Users/kchangwook/IdeaProjects/board/src/main/resources/static/assets/img/avatars/1.png");

        AttachFile attachFile = AttachFile.builder()
                .fileId(Integer.parseInt(fileId))
                .fileSavePath(encryptFilePath)
                .build();

        when(fileMapper.selectAttachFile(anyInt())).thenReturn(attachFile);

        // when
        fileService.getImageFileData(encryptFileId);

        // then
        verify(fileMapper, times(1)).selectAttachFile(anyInt());
    }

    @Test
    @DisplayName("이미지 파일 데이터 가져오기 테스트 - 저장된 이미지가 없어 기본 이미지로 설정")
    public void getFileDataTest() throws IOException {
        //given
        String fileId = "0";

        // when
        fileService.getImageFileData(fileId);

        // then
        verify(fileMapper, times(0)).selectAttachFile(anyInt());
    }
}
