package team2.study_project.dto.follow;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FollowListResponseDto {

    private List<FollowResponseDto> followList;

    @Builder
    public FollowListResponseDto(List<FollowResponseDto> followList) {
        this.followList = followList;
    }
}
