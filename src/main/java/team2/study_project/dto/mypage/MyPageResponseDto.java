package team2.study_project.dto.mypage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageResponseDto {

    private String nickname;
    private String email;
    private int Follower;
    private int Following;

    @Builder
    public MyPageResponseDto(String nickname, String email, int follower, int following) {
        this.nickname = nickname;
        this.email = email;
        Follower = follower;
        Following = following;
    }
}
