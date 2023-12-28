package project.mini.board.file.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import project.mini.board.constant.AesKey;
import project.mini.board.file.enumeration.FileUsage;
import project.mini.board.util.Aes256Util;

@Alias("file")
@Setter
@Getter
@NoArgsConstructor
public class AttachFile {
    private int fileId;
    private String fileName;
    private String fileExtension;
    private FileUsage fileUsage;
    private int fileUsageId;
    private String fileSavePath;

    @Builder
    public AttachFile(int fileId, String fileName, String fileExtension, FileUsage fileUsage, int fileUsageId, String fileSavePath) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileUsage = fileUsage;
        this.fileUsageId = fileUsageId;
        this.fileSavePath = fileSavePath;
    }

    public String getDecryptFileSavePath() {
        return Aes256Util.decrypt(AesKey.FILE, fileSavePath);
    }
}
