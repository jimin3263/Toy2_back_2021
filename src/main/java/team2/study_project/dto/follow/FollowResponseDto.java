package team2.study_project.dto.follow;

import lombok.Builder;
import lombok.Getter;


@Getter
public class FollowResponseDto {

    private Long userId;
    private String nickname;
    private Integer achievementRate;

    @Builder
    public FollowResponseDto(Long userId, String nickname, Integer achievementRate) {
        this.userId = userId;
        this.nickname = nickname;
        this.achievementRate = achievementRate;
    }

}
