package team2.study_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    //study
    STUDY_NOT_FOUND(404, "STUDY_001", "존재하지 않는 목록입니다."),
    //follow
    FOLLOW_NOT_FOUND(400, "FOLLOW_001", "팔로우되어 있지 않습니다."),
    ALREADY_FOLLOW(400,"FOLLOW_002","이미 팔로우되어 있습니다."),
    REJECTED_FOLLOW(400,"FOLLOW_003","팔로우할 수 없습니다."),
    //user
    USER_NOT_FOUND(404, "USER_001", "존재하지 않는 회원입니다.");


    private ErrorResponse errorResponse;

    ErrorEnum(int httpStatus, String errCode, String message) {
        this.errorResponse = new ErrorResponse(httpStatus, message);
    }

    @Getter
    public class ErrorResponse {
        private int status;
        private String message;

        public ErrorResponse(int httpStatus, String message) {
            this.status = httpStatus;
            this.message = message;
        }

    }

}
