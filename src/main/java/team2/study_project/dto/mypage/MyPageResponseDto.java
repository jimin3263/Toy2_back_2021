package team2.study_project.dto.mypage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageResponseDto {

    private String username;
    private String email;
    private int follower;
    private int following;

    @Builder
    public MyPageResponseDto(String username, String email, int follower, int following) {
        this.username = username;
        this.email = email;
        this.follower = follower;
        this.following = following;
    }
}
