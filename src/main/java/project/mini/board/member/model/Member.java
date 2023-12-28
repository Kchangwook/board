package project.mini.board.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;
import project.mini.board.constant.AesKey;
import project.mini.board.constant.MemberConstant;
import project.mini.board.util.Aes256Util;

import java.net.URLEncoder;

@Alias("member")
@NoArgsConstructor
public class Member {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String newPassword;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String nick;

    @Getter
    @Setter
    private int profileImageId;

    @Builder
    public Member(String id, String password, String newPassword, String email, String nick) {
        this.id = id;
        this.password = password;
        this.newPassword = newPassword;
        this.email = email;
        this.nick = nick;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptProfileImage() {
        String profileImageId = this.profileImageId <= 0 ? MemberConstant.EMPTY_IMAGE_FILE_ID : String.valueOf(this.profileImageId);
        String encryptFileId = Aes256Util.encrypt(AesKey.FILE, profileImageId);
        return StringUtils.replace(encryptFileId, "/", "_");
    }
}
