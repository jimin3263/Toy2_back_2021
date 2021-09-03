package team2.study_project.dto.follow;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class FollowResponseDto {

    private Long userId;
    private String username;
    private double achievementRate;
    private String accumulatedTime;
    private Boolean studyStatus;

    @Builder
    public FollowResponseDto(Long userId, String username, double achievementRate, String accumulatedTime, Boolean status) {
        this.userId = userId;
        this.username = username;
        this.achievementRate = achievementRate;
        this.accumulatedTime = accumulatedTime;
        this.studyStatus = status;
    }
}
