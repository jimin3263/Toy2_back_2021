package team2.study_project.dto.follow;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team2.study_project.domain.User;
import team2.study_project.dto.study.StudyResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class FollowListResponseDto {

    private List<User> followList;

    @Builder
    public FollowListResponseDto(List<User> followList) {
        this.followList = followList;
    }
}
