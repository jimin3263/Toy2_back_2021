package team2.study_project.dto.study;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyRequestDto {
    private String content;

    @Builder
    public StudyRequestDto(String content) {
        this.content = content;
    }
}