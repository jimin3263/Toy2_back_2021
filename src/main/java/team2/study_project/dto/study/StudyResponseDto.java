package team2.study_project.dto.study;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyResponseDto {

    private Long studyId;
    private String content;
    private boolean status;

    @Builder
    public StudyResponseDto(String content, boolean status, Long studyId ) {
        this.content = content;
        this.status = status;
        this.studyId = studyId;
    }
}
