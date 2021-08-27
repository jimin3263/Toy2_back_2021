package team2.study_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudyException.class)
    public ResponseEntity<?> studyException(StudyException ex) {
        ErrorEnum errorEnum = ex.getErrorEnum();
        ErrorEnum.ErrorResponse errorResponse = errorEnum.getErrorResponse();

        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(ex.getStatus()));
    }

}
