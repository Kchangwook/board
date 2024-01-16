package project.mini.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AesKey {
    MEMBER_LOGIN("projectminiboardmemberloginkey!@"),
    FILE("projectminiboardattachfilekey!@#");

    @Getter
    private final String keyString;
}
