package project.mini.board.file.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import project.mini.board.file.mapper.FileMapper;
import project.mini.board.file.model.AttachFile;
import project.mini.board.member.model.Member;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {
    @Mock
    private FileMapper fileMapper;

    @InjectMocks
    private FileServiceImpl fileService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(fileService, "attachFileEncryptKey", "projectminiboardattachfilekey!@#");
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
}
