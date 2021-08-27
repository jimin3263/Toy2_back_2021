package team2.study_project.dto.study;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StudyListResponseDto {

    private List<StudyResponseDto> studyList;

    @Builder
    public StudyListResponseDto(List<StudyResponseDto> studyList) {
        this.studyList = studyList;
    }
}
