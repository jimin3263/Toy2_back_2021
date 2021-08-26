package team2.study_project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StudyException extends RuntimeException {

    protected HttpStatus status;
    protected ErrorEnum errorEnum;

    public StudyException(ErrorEnum errorEnum) {
        super(errorEnum.toString());
        this.status = errorEnum.getErrorResponse().getHttpStatus();
        this.errorEnum = errorEnum;
    }
}
