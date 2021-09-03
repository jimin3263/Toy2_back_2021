package team2.study_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {
    private int code;
    private String message;

    public ResponseMessage(HttpStatus httpStatus, String message){
        this.code = httpStatus.value();
        this.message = message;
    }

}
