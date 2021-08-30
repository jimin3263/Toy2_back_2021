package team2.study_project.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindUserListDto {

    private List<FindUserDto> userList;

    @Builder
    public FindUserListDto(List<FindUserDto> userList) {
        this.userList = userList;
    }
}