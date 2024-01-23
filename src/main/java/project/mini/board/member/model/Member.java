package project.mini.board.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.type.Alias;
import project.mini.board.cipher.enumeration.AesKey;
import project.mini.board.member.constant.MemberConstant;
import project.mini.board.cipher.util.Aes256Cipher;

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

    public void setEncryptProfileImageId(String encryptProfileImageId) {
        String replacedProfileImageId = StringUtils.replace(encryptProfileImageId, "_", "/");
        this.profileImageId = NumberUtils.toInt(Aes256Cipher.decrypt(AesKey.FILE, replacedProfileImageId), 0);
    }

    public String getEncryptProfileImage() {
        String profileImageId = this.profileImageId <= 0 ? MemberConstant.EMPTY_IMAGE_FILE_ID : String.valueOf(this.profileImageId);
        String encryptFileId = Aes256Cipher.encrypt(AesKey.FILE, profileImageId);
        return StringUtils.replace(encryptFileId, "/", "_");
    }
}
