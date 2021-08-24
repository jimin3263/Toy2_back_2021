package team2.study_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    //study 관련 오류 메시지
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "STUDY_001", "존재하지 않는 목록입니다.");

    private ErrorResponse errorResponse;

    ErrorEnum(HttpStatus httpStatus, String errCode, String message) {
        this.errorResponse = new ErrorResponse(httpStatus, errCode, message);
    }

    @Getter
    public class ErrorResponse {
        private HttpStatus httpStatus;
        private String errCode;
        private String message;

        public ErrorResponse(HttpStatus httpStatus, String errCode, String message) {
            this.httpStatus = httpStatus;
            this.errCode = errCode;
            this.message = message;
        }
    }

}
