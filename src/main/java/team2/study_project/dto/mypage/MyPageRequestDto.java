package team2.study_project.dto.mypage;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageRequestDto {
    private String username;

    @Builder
    public MyPageRequestDto(String username) {
        this.username = username;
    }
}
