package project.mini.board.member.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("member")
@NoArgsConstructor
public class Member {
    private String id;
    private String password;
    private String email;
    private String nick;

    @Builder
    public Member(String id, String password, String email, String nick) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nick = nick;
    }
}
