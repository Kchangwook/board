package project.mini.board.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.ibatis.type.Alias;

@Alias("member")
@Getter
@Setter
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

    public void setPassword(String password) {
        this.password = password;
    }
}
