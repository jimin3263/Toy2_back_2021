package team2.study_project.exception;

import lombok.Getter;

@Getter
public class StudyException extends RuntimeException {

    protected int status;
    protected ErrorEnum errorEnum;

    public StudyException(ErrorEnum errorEnum) {
        super(errorEnum.toString());
        this.status = errorEnum.getErrorResponse().getStatus();
        this.errorEnum = errorEnum;
    }
}
