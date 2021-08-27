package team2.study_project.dto.mypage;


import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageRequestDto {
    private String nickname;

    @Builder
    public MyPageRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
