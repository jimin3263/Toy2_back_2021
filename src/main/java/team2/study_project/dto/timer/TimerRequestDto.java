package team2.study_project.dto.timer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TimerRequestDto {
    private String time;

    @Builder

    public TimerRequestDto(String time) {
        this.time = time;
    }
}
