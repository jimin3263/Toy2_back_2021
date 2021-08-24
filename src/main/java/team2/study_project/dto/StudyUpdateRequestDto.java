package team2.study_project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class StudyUpdateRequestDto {
    private String content;

    @Builder
    public StudyUpdateRequestDto(String content) {
        this.content = content;
    }
}
